package com.example.pcenter.service.impl;

import com.example.pcenter.domain.appointment.Appointment;
import com.example.pcenter.domain.appointment.Status;
import com.example.pcenter.domain.doctor.Doctor;
import com.example.pcenter.domain.exception.ResourceNotFoundException;
import com.example.pcenter.domain.user.User;
import com.example.pcenter.repository.AppointmentRepository;
import com.example.pcenter.service.AppointmentService;
import com.example.pcenter.service.DoctorService;
import com.example.pcenter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorService doctorService;
    private final UserService userService;

    private final Logger logger = Logger.getLogger(AppointmentServiceImpl.class.getName());

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "AppointmentService::getById", key = "#id")
    public Appointment getById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Запись не найдена"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "AppointmentService::getAllAppointments", key = "#doctorId")
    public List<Appointment> getAllAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findAllByDoctorId(doctorId).stream()
                .filter(e -> e.getStatus().equals(Status.FREE)).collect(Collectors.toList());
    }


    @Override
    @Transactional
    @Caching(put = {
            @CachePut(value = "AppointmentService::getById", key = "#appointment.id"),
            @CachePut(value = "AppointmentService::getStatus", key = "#appointment.status"),
    })
    public Appointment update(Appointment appointment) {
        if (appointment.getStatus() == null) {
            appointment.setStatus(Status.FREE);
        }
        appointmentRepository.save(appointment);
        logger.info("Запись обновлена(сервис)");
        return appointment;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Appointment> getAllAppointmentsByUser(Long id) {
        return appointmentRepository.findAllByUserId(id);
    }

    @Override
    @Transactional
    public Appointment create(Appointment appointment, Long doctorId) {
        appointment.setStatus(Status.FREE);
        appointment.setDoctorId(doctorId);
        appointmentRepository.save(appointment);
        Doctor doctor = doctorService.getById(doctorId);
        doctor.getAppointments().add(appointment);
        doctorService.updateDoctor(doctor);
        logger.info("Создана новая запись у врача" + doctorId + " (сервис)");
        return appointment;
    }

    @Override
    @Transactional
    public Appointment addUserAppointment(Long userId, Long appId) {
        User user = userService.getUserById(userId);
        Appointment appointment = appointmentRepository.findById(appId).orElseThrow();
        appointment.setUserId(user.getId());
        appointment.setStatus(Status.RECORDED);
        user.getAppointmentList().add(appointment);
        appointmentRepository.save(appointment);
        userService.update(user);
        logger.info("Добавлена запись к пациенту " + userId + " (сервис)");
        return appointment;
    }

    @Override
    @Transactional
    @CacheEvict(value = "AppointmentService::getById", key = "#id")
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
