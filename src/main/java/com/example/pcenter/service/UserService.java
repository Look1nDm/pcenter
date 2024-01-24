package com.example.pcenter.service;

import com.example.pcenter.domain.user.User;

import java.util.List;

public interface UserService {
    User getUserById(Long id);

    List<User> getAllUsers();

    User getByUsername(String username);

    User update(User user);

    User create(User user);

    void deleteUser(Long id);

    void deleteAppointmentToUser(Long userId);
}
