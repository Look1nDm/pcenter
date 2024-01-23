package com.example.pcenter.service.impl;

import com.example.pcenter.domain.exception.ResourceNotFoundException;
import com.example.pcenter.domain.user.Role;
import com.example.pcenter.domain.user.User;
import com.example.pcenter.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @SpyBean
    private UserServiceImpl userService;

    @Test
    void getUserById() {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        when(userRepository.findById(id))
                .thenReturn(Optional.of(user));
        User testUser = userService.getUserById(id);
        verify(userRepository).findById(id);
        assertEquals(user, testUser);
    }

    @Test
    void getByIdWithNotExistingId() {
        Long id = 1L;
        when(userRepository.findById(id))
                .thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> userService.getUserById(id));
        verify(userRepository).findById(id);
    }

    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            users.add(new User());
        }
        when(userRepository.findAll()).thenReturn(users);
        List<User> testUsers = userService.getAllUsers();
        verify(userRepository).findAll();
        assertEquals(users, testUsers);
    }

    @Test
    void getByUsername() {
        String username = "Phone";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));
        User testUser = userService.getByUsername(username);
        verify(userRepository).findByUsername(username);
        assertEquals(user, testUser);
    }

    @Test
    void getByUsernameWithNotExistingUsername() {
        String username = "Vova";
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> userService.getByUsername(username));
        verify(userRepository).findByUsername(username);
    }

    @Test
    void create() {
        String username = "username";
        String password = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPasswordConfirmation(password);
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());
        User testUser = userService.create(user);
        verify(userRepository).save(user);
        verify(passwordEncoder).encode(password);
        assertEquals(Set.of(Role.ROLE_PATIENT), testUser.getRoles());
    }

    @Test
    void createWithExistingUsername() {
        String username = "username";
        String password = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPasswordConfirmation(password);
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(new User()));
        assertThrows(IllegalStateException.class,
                () -> userService.create(user));
        verify(userRepository, never()).save(user);
    }

    @Test
    void createWithDifferentPasswords() {
        String username = "username";
        String password = "password";
        String passwordConfirmation = "passwordConfirmation";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPasswordConfirmation(passwordConfirmation);
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class,
                () -> userService.create(user));
        verify(userRepository, never()).save(user);
    }

    @Test
    void update() {
        Long id = 1L;
        String password = "password";
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        when(passwordEncoder.encode(password))
                .thenReturn("encodedPassword");
        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        User updatedUser = userService.update(user);
        verify(passwordEncoder).encode(password);
        verify(userRepository).save(user);
        assertEquals(user.getUsername(), updatedUser.getUsername());
        assertEquals(user.getName(), updatedUser.getName());
    }

    @Test
    void deleteUser() {
        Long id = 1L;
        userService.deleteUser(id);
        verify(userRepository).deleteById(id);
    }
}
