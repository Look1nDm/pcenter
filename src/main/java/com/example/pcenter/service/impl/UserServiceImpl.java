package com.example.pcenter.service.impl;

import com.example.pcenter.domain.appointment.Appointment;
import com.example.pcenter.domain.appointment.Status;
import com.example.pcenter.domain.exception.ResourceNotFoundException;
import com.example.pcenter.domain.user.Role;
import com.example.pcenter.domain.user.User;
import com.example.pcenter.repository.UserRepository;
import com.example.pcenter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::getUserById", key = "#id")
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::getByUsername", key = "#username")
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
    }

    @Override
    @Transactional
    @Caching(cacheable = {
            @Cacheable(value = "UserService::getUserById", key = "#user.id"),
            @Cacheable(value = "UserService::getByUsername", key = "#user.username"),
    })
    public User create(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("Пользователь с таким номером уже существует.");
        }
        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new IllegalStateException("Введенные пароли не совпадают.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = Set.of(Role.ROLE_PATIENT);
        user.setRoles(roles);
        userRepository.save(user);
        logger.info("Новый пациент создан(сервис)");
        return user;
    }

    @Override
    @Transactional
    @Caching(put = {
            @CachePut(value = "UserService::getUserById", key = "#user.id"),
            @CachePut(value = "UserService::getByUsername", key = "#user.username"),
    })
    public User update(User user) {
        User existing = userRepository.findById(user.getId()).orElseThrow();
        existing.setName(user.getName());
        existing.setSurname(user.getSurname());
        existing.setLastname(user.getLastname());
        existing.setEmail(user.getEmail());
        existing.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(existing);
        logger.info("Пациент " + user.getId() + " обновлен(сервис)");
        return existing;
    }

    @Override
    @Transactional
    @CacheEvict(value = "UserService::getUserById", key = "#id")
    public void deleteUser(Long id) {
        logger.info("Пациент " + id + " удален (контроллер)");
        userRepository.deleteById(id);
    }


    @Override
    @Transactional
    public void deleteAppointmentToUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        logger.info("Найден пользователь для удаления записи (сервис)");
        List<Appointment> appointmentList = user.getAppointmentList();
        for (Appointment app : appointmentList) {
            if (app.getStatus().equals(Status.RECORDED)) {
                app.setUserId(null);
                app.setStatus(Status.FREE);
                user.getAppointmentList().remove(app);
                logger.info("Статус записи изменен на FREE и удален у пациента");
                userRepository.save(user);
            } else {
                throw new ResourceNotFoundException("Записей не найдено");
            }
        }

    }

}
