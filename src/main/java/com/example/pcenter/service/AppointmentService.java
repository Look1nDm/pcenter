package com.example.pcenter.service;

import com.example.pcenter.domain.appointment.Appointment;

import java.util.List;

public interface AppointmentService {

    Appointment getById(Long id);

    List<Appointment> getAllAppointmentsByDoctorId(Long doctorId);

    Appointment update(Appointment appointment);

    List<Appointment> getAllAppointmentsByUser(Long id);

    Appointment create(Appointment appointment, Long doctorId);

    Appointment addUserAppointment(Long appId, Long userId);

    void deleteAppointment(Long id);
}
