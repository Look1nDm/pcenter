package com.example.pcenter.web.controller;

import com.example.pcenter.domain.appointment.Appointment;
import com.example.pcenter.service.AppointmentService;
import com.example.pcenter.web.dto.appointment.AppointmentDto;
import com.example.pcenter.web.dto.validation.OnCreate;
import com.example.pcenter.web.dto.validation.OnUpdate;
import com.example.pcenter.web.mappers.AppointmentMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;


@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
@Validated
@Tag(name = "Appointment Controller", description = "Appointment API")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;

    private final Logger logger = Logger.getLogger(AppointmentController.class.getName());

    @PostMapping("/create/{doctorId}")
    @Operation(summary = "Creating a doctor's appointment")
    @PreAuthorize("@customSecurityExpression.canAccessAdmin()")
    public AppointmentDto createAppointment(@Validated(OnCreate.class)
                                            @RequestBody final AppointmentDto appointmentDto,
                                            @PathVariable final Long doctorId) {
        Appointment appointment = appointmentMapper.toEntity(appointmentDto);
        Appointment createdAppointment = appointmentService.create(appointment, doctorId);
        logger.info("Создана новая запись у доктора " + doctorId + " (контроллер)");
        return appointmentMapper.toDto(createdAppointment);
    }

    @PostMapping()
    @Operation(summary = "Updating appointment")
    @PreAuthorize("@customSecurityExpression.canAccessAdmin()")
    public AppointmentDto update(@Validated(OnUpdate.class)
                                 @RequestBody final AppointmentDto dto) {
        Appointment appointment = appointmentMapper.toEntity(dto);
        Appointment updatedAppointment = appointmentService.update(appointment);
        logger.info("Запись обновлена(контроллер)");
        return appointmentMapper.toDto(updatedAppointment);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get appointment")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public AppointmentDto getById(@PathVariable final Long id) {
        Appointment appointment = appointmentService.getById(id);
        return appointmentMapper.toDto(appointment);
    }

    @GetMapping("/getAllApp/{id}")
    @Operation(summary = "Find all appointments to user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public List<AppointmentDto> getAllAppointmentsByUser(@PathVariable final Long id) {
        return appointmentMapper.toDto(appointmentService.getAllAppointmentsByUser(id));
    }

    @PutMapping("/{userId},{appId}")
    @Operation(summary = "Add appointment to user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public AppointmentDto addUserToAppointment(@PathVariable final Long userId,
                                               @PathVariable final Long appId) {
        logger.info("Запись " + appId + " добавлена пациенту " + userId + " (контроллер)");
        return appointmentMapper.toDto(appointmentService.addUserAppointment(userId, appId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete appointment")
    @PreAuthorize("@customSecurityExpression.canAccessAdmin()")
    public void deleteAppointment(@PathVariable final Long id) {
        logger.info("Запись " + id + " удалена(контроллер)");
        appointmentService.deleteAppointment(id);
    }


}
