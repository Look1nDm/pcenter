package com.example.pcenter.web.controller;

import com.example.pcenter.domain.doctor.Doctor;
import com.example.pcenter.service.AppointmentService;
import com.example.pcenter.service.DoctorService;
import com.example.pcenter.web.dto.appointment.AppointmentDto;
import com.example.pcenter.web.dto.doctor.DoctorDto;
import com.example.pcenter.web.dto.validation.OnCreate;
import com.example.pcenter.web.dto.validation.OnUpdate;
import com.example.pcenter.web.mappers.AppointmentMapper;
import com.example.pcenter.web.mappers.DoctorMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
@Validated
@Tag(name = "Doctor Controller", description = "Doctor API")
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;
    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;

    private final Logger logger = Logger.getLogger(DoctorController.class.getName());

    @PutMapping("/updateDoctor")
    @Operation(summary = "Update doctor")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#dto.id)")
    public DoctorDto updateDoctor(@Validated(OnUpdate.class)
                                  @RequestBody final DoctorDto dto) {
        Doctor doctor = doctorMapper.toEntity(dto);
        Doctor updatedDoctor = doctorService.updateDoctor(doctor);
        logger.info("Данные врача " + doctor.getId() + " обновлены(контроллер)");
        return doctorMapper.toDto(updatedDoctor);
    }

    @PostMapping("/createDoctor")
    @Operation(summary = "Creating doctor")
    @PreAuthorize("@customSecurityExpression.canAccessAdmin()")
    public DoctorDto creteDoctor(@Validated(OnCreate.class)
                                 @RequestBody final DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.toEntity(doctorDto);
        Doctor createdDoctor = doctorService.createDoctor(doctor);
        logger.info("Добавден врач " + doctor.getId() + " (контроллер)");
        return doctorMapper.toDto(createdDoctor);
    }

    @GetMapping("/{id}/appointments")
    @Operation(summary = "Get all appointments by doctor")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public List<AppointmentDto> findAllAppointmentsByDoctor(@PathVariable final Long id) {
        return appointmentMapper.toDto(appointmentService.getAllAppointmentsByDoctorId(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get doctor by id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public DoctorDto getById(@PathVariable final Long id) {
        Doctor doctor = doctorService.getById(id);
        return doctorMapper.toDto(doctor);
    }

    @GetMapping("/allDoctors")
    @Operation(summary = "Get all doctors")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public List<DoctorDto> getAllDoctors() {
        return doctorMapper.toDto(doctorService.getAllDoctors());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete doctor")
    @PreAuthorize("@customSecurityExpression.canAccessAdmin()")
    public void deleteDoctor(@PathVariable final Long id) {
        logger.info("Врач " + id + " удален(контроллер)");
        doctorService.deleteDoctor(id);
    }
}
