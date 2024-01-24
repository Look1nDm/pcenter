package com.example.pcenter.service.impl;

import com.example.pcenter.domain.doctor.Doctor;
import com.example.pcenter.domain.exception.ResourceNotFoundException;
import com.example.pcenter.repository.DoctorRepository;
import com.example.pcenter.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    private final Logger logger = Logger.getLogger(DoctorServiceImpl.class.getName());

    @Override
    @Transactional(readOnly = true)
    public Doctor getById(Long id) {
        return doctorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Врач не найден"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    @Transactional
    public Doctor createDoctor(Doctor doctor) {
        logger.info("Новый врач создан(сервис)");
        return doctorRepository.save(doctor);
    }

    @Override
    @Transactional
    public Doctor updateDoctor(Doctor doctor) {
        logger.info("Врач" + doctor.getId() + " обновлен(сервис)");
        return doctorRepository.save(doctor);
    }

    @Override
    @Transactional
    public void deleteDoctor(Long id) {
        logger.info("Врач " + id + " удален(сервис)");
        doctorRepository.deleteById(id);
    }
}
