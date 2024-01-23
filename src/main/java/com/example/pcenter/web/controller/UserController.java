package com.example.pcenter.web.controller;

import com.example.pcenter.domain.user.User;
import com.example.pcenter.service.UserService;
import com.example.pcenter.web.dto.user.UserDto;
import com.example.pcenter.web.dto.validation.OnUpdate;
import com.example.pcenter.web.mappers.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User Controller", description = "User API")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    private Logger logger = Logger.getLogger(UserController.class.getName());

    @PutMapping("/create")
    @PreAuthorize("@customSecurityExpression.canAccessAdmin()")
    @Operation(summary = "Creating a user")
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        userService.create(user);
        logger.info("Новый пациент создан(контроллер)");
        return userMapper.toDto(user);
    }

    @PutMapping
    @Operation(summary = "Update user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#dto.id)")
    public UserDto updateUser(@Validated(OnUpdate.class)
                              @RequestBody final UserDto dto) {
        User user = userMapper.toEntity(dto);
        User updatedUser = userService.update(user);
        logger.info("Пациент"+user.getId()+" обновлен(контроллер)");
        return userMapper.toDto(updatedUser);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public UserDto getById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return userMapper.toDto(user);
    }

    @GetMapping()
    @Operation(summary = "Find user by username (number phone)")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public UserDto getByUsername(@RequestParam(value = "username") String username) {
        return userMapper.toDto(userService.getByUsername(username));
    }

    @GetMapping("/allUsers")
    @Operation(summary = "Get all users")
    @PreAuthorize("@customSecurityExpression.canAccessAdmin()")
    public List<UserDto> getAllUsers() {
        return userMapper.toDto(userService.getAllUsers());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    @PreAuthorize("@customSecurityExpression.canAccessAdmin()")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @DeleteMapping("/deletedAppointment/{userId}")
    @Operation(summary = "Delete appointment to user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public void deleteAppointmentToUser(@PathVariable Long userId) {
        logger.info("Запись у пациента "+userId+" удалена(контроллер)");
        userService.deleteAppointmentToUser(userId);
    }
}
