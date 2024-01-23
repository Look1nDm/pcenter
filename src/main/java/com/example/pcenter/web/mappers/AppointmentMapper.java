package com.example.pcenter.web.mappers;

import com.example.pcenter.domain.appointment.Appointment;
import com.example.pcenter.web.dto.appointment.AppointmentDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    AppointmentDto toDto(Appointment appointment);

    Appointment toEntity(AppointmentDto appointmentDto);

    List<AppointmentDto> toDto(List<Appointment> appointments);
}
