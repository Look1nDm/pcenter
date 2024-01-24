package com.example.pcenter.service.impl;

import com.example.pcenter.domain.doctor.Doctor;
import com.example.pcenter.domain.exception.ResourceNotFoundException;
import com.example.pcenter.repository.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class DoctorServiceImplTest {

    @MockBean
    private DoctorRepository doctorRepository;

    @SpyBean
    private DoctorServiceImpl doctorService;

    @Test
    void getById() {
        Long id = 1L;
        Doctor doctor = new Doctor();
        doctor.setId(id);
        when(doctorRepository.findById(id))
                .thenReturn(Optional.of(doctor));
        Doctor testDoctor = doctorService.getById(id);
        verify(doctorRepository).findById(id);
        assertEquals(doctor, testDoctor);
    }

    @Test
    void getByIdWithNotExistingId() {
        Long id = 1L;
        Doctor doctor = new Doctor();
        doctor.setId(id);
        when(doctorRepository.findById(id))
                .thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> doctorService.getById(id));
        verify(doctorRepository).findById(id);
    }

    @Test
    void getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            doctors.add(new Doctor());
        }
        when(doctorRepository.findAll()).thenReturn(doctors);
        List<Doctor> testDoctors = doctorService.getAllDoctors();
        verify(doctorRepository).findAll();
        assertEquals(doctors, testDoctors);
    }

    @Test
    void createDoctor() {
        Long id = 1L;
        String name = "Anna";
        String surname = "Petrovna";
        String lastname = "Dumova";
        Integer room = 10;
        Doctor extendedDoctor = new Doctor();
        extendedDoctor.setId(id);
        extendedDoctor.setName(name);
        extendedDoctor.setSurname(surname);
        extendedDoctor.setLastname(lastname);
        extendedDoctor.setNumberRoom(room);
        when(doctorRepository.save(any())).thenReturn(extendedDoctor);
        Doctor actualDoctor = doctorService.createDoctor(extendedDoctor);
        assertEquals(extendedDoctor, actualDoctor);
    }

    @Test
    void updateDoctor() {
        String name = "name";
        String lastname = "lastname";
        Doctor extendedDoctor = new Doctor();
        extendedDoctor.setName(name);
        extendedDoctor.setLastname(lastname);
        when(doctorRepository.save(any())).thenReturn(extendedDoctor);
        Doctor actualDoctor = doctorService.updateDoctor(extendedDoctor);
        verify(doctorRepository).save(extendedDoctor);
        assertEquals(extendedDoctor.getName(), actualDoctor.getName());
        assertEquals(extendedDoctor.getLastname(), actualDoctor.getLastname());
    }

    @Test
    void deleteDoctor() {
        Long id = 1L;
        doctorService.deleteDoctor(id);
        verify(doctorRepository).deleteById(id);
    }
}
