package com.example.pcenter.web.controller;

import com.example.pcenter.domain.user.User;
import com.example.pcenter.service.AuthService;
import com.example.pcenter.service.UserService;
import com.example.pcenter.web.dto.auth.JwtRequest;
import com.example.pcenter.web.dto.auth.JwtResponse;
import com.example.pcenter.web.dto.user.UserDto;
import com.example.pcenter.web.dto.validation.OnCreate;
import com.example.pcenter.web.mappers.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Auth Controller", description = "Authentication API")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    private Logger logger = Logger.getLogger(AuthController.class.getName());

    @PostMapping("/login")
    @Operation(summary = "Authorization user")
    public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest){
        logger.info("Вход в ЛК (контроллер)");
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    @Operation(summary = "Registration user")
    public UserDto register(@Validated(OnCreate.class) @RequestBody final UserDto userDto){
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.create(user);
        logger.info("Регистрация нового пользователя (контроллер)");
        return userMapper.toDto(createdUser);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Update user")
    public JwtResponse refresh (@RequestBody final String refreshToken){
        logger.info("Смена пароля у пользователя(контроллер)");
        return authService.refresh(refreshToken);
    }
}
