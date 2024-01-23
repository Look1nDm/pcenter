package com.example.pcenter.web.dto.doctor;

import com.example.pcenter.domain.appointment.Appointment;
import com.example.pcenter.web.dto.validation.OnCreate;
import com.example.pcenter.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@Schema(description = "Doctor dto")
public class DoctorDto {

    @Schema(description = "Doctor id", example = "1")
    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    @Schema(description = "Doctor surname", example = "Usmanova")
    @NotNull(message = "Surname must be not null.", groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 255, message = "Фамилия не может быть более 255 символов", groups = {OnUpdate.class, OnCreate.class})
    private String surname; // фамилия

    @Schema(description = "Doctor name", example = "Veronika")
    @NotNull(message = "Name must be not null.", groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 255, message = "Имя не может быть более 255 символов", groups = {OnUpdate.class, OnCreate.class})
    private String name; // имя

    @Schema(description = "Doctor lastname", example = "Viktorovna")
    @NotNull(message = "Lastname must be not null.", groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 255, message = "Отчество не может быть более 255 символов", groups = {OnUpdate.class, OnCreate.class})
    private String lastname; // отчество

    @Schema(description = "Doctor room", example = "23")
    private Integer numberRoom; // номер кабинета
    private List<Appointment> list;
}
