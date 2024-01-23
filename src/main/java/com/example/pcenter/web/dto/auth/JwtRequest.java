package com.example.pcenter.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request for login")
public class JwtRequest {

    @Schema(description = "Phone number", example = "+79992097902")
    @NotNull(message = "Username most be not null.")
    private String username;

    @Schema(description = "Password", example = "12345")
    @NotNull(message = "Password most be not null.")
    private String password;
}
