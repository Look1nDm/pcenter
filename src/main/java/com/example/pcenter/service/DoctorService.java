package com.example.pcenter.service;

import com.example.pcenter.domain.doctor.Doctor;

import java.util.List;

public interface DoctorService {

    Doctor getById(Long id);

    List<Doctor> getAllDoctors();

    Doctor createDoctor(Doctor doctor);

    Doctor updateDoctor(Doctor doctor);

    void deleteDoctor(Long id);
}
