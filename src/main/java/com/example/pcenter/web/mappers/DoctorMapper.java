package com.example.pcenter.web.mappers;

import com.example.pcenter.domain.doctor.Doctor;
import com.example.pcenter.web.dto.doctor.DoctorDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorDto toDto(Doctor doctor);

    Doctor toEntity(DoctorDto doctorDto);

    List<DoctorDto> toDto (List<Doctor> doctors);
}
