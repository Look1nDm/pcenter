package com.example.pcenter.service.impl;

import com.example.pcenter.domain.appointment.Appointment;
import com.example.pcenter.domain.appointment.Status;
import com.example.pcenter.domain.exception.ResourceNotFoundException;
import com.example.pcenter.repository.AppointmentRepository;
import com.example.pcenter.repository.DoctorRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class AppointmentServiceImplTest {

    @MockBean
    private AppointmentRepository appointmentRepository;

    @MockBean
    private DoctorRepository doctorRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @SpyBean
    private DoctorServiceImpl doctorService;

    @SpyBean
    private UserServiceImpl userService;

    @SpyBean
    private AppointmentServiceImpl appointmentService;

    @Test
    void getById() {
        Long id = 1L;
        Appointment appointment = new Appointment();
        appointment.setId(id);
        when(appointmentRepository.findById(id))
                .thenReturn(Optional.of(appointment));
        Appointment testAppointment = appointmentService.getById(id);
        verify(appointmentRepository).findById(id);
        assertEquals(appointment, testAppointment);
    }

    @Test
    void getByIdWithNotExistingId() {
        Long id = 1L;
        when(appointmentRepository.findById(id))
                .thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> appointmentService.getById(id));
        verify(appointmentRepository).findById(id);
    }

    @Test
    void getAllAppointmentsByDoctorId() {
        Long doctorId = 1L;
        List<Appointment> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add(new Appointment());
            list.get(i).setStatus(Status.FREE);
        }
        when(appointmentRepository.findAllByDoctorId(doctorId))
                .thenReturn(list);
        List<Appointment> testList = appointmentService.getAllAppointmentsByDoctorId(doctorId);
        verify(appointmentRepository).findAllByDoctorId(doctorId);
        assertEquals(list, testList);
    }

    @Test
    void update() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setStatus(Status.BUSY);
        Appointment testAppointment = appointmentService.update(appointment);
        verify(appointmentRepository).save(appointment);
        assertEquals(appointment, testAppointment);
    }

    @Test
    void updateWithNullStatus() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        Appointment testAppointment = appointmentService.update(appointment);
        verify(appointmentRepository).save(appointment);
        assertEquals(testAppointment.getStatus(), Status.FREE);
    }

    @Test
    void getAllAppointmentsByUser() {
        Long userId = 1L;
        List<Appointment> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add(new Appointment());
        }
        when(appointmentRepository.findAllByUserId(userId))
                .thenReturn(list);
        List<Appointment> testList = appointmentService.getAllAppointmentsByUser(userId);
        verify(appointmentRepository).findAllByUserId(userId);
        assertEquals(list, testList);
    }

//    @Test
//    void create(){
//        Long doctorId = 1L;
//        Long appointmentId = 1L;
//        Appointment appointment = new Appointment();
//
//    }

    @Test
    void delete() {
        Long id = 1L;
        appointmentService.deleteAppointment(id);
        verify(appointmentRepository).deleteById(id);
    }

}
