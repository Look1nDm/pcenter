package com.example.pcenter.web.dto.user;

import com.example.pcenter.web.dto.validation.OnCreate;
import com.example.pcenter.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Schema(description = "User dto")
public class UserDto {

    @Schema(description = "User id", example = "2")
    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    @Schema(description = "User surname", example = "Pupkin")
    @NotNull(message = "Surname must be not null.", groups = OnUpdate.class)
    @Length(max = 255, message = "Фамилия не может быть более 255 символов", groups = {OnUpdate.class, OnCreate.class})
    private String surname; // фамилия

    @Schema(description = "User name", example = "Vasiliy")
    @NotNull(message = "Name must be not null.", groups = OnUpdate.class)
    @Length(max = 255, message = "Имя не может быть более 255 символов", groups = {OnUpdate.class, OnCreate.class})
    private String name; // имя

    @Schema(description = "User lastname", example = "Petrovich")
    @NotNull(message = "Lastname must be not null.", groups = OnUpdate.class)
    @Length(max = 255, message = "Отчество не может быть более 255 символов", groups = {OnUpdate.class, OnCreate.class})
    private String lastname; // отчество

    @Schema(description = "User number phone", example = "+79992097902")
    @NotNull(message = "Username must be not null.", groups = {OnUpdate.class, OnCreate.class})
    private String username; // номер телефона для входа

    @Schema(description = "User email", example = "pupkin@gmail.com")
    private String email;

    @Schema(description = "User crypted password", example = "$2a$12$TsqmXxvOObz1dF4ugre6reFNw6XVxQ2O/kHe1Zv1vHbObai68TqL6")
    @NotNull(message = "Password must be not null.", groups = {OnUpdate.class, OnCreate.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Schema(description = "User crypted password confirmation", example = "$2a$12$TsqmXxvOObz1dF4ugre6reFNw6XVxQ2O/kHe1Zv1vHbObai68TqL6")
    @NotNull(message = "Password confirmation must be not null.", groups = OnCreate.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordConfirmation;
//    private List<Appointment> appointmentList;

}
