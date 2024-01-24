package com.example.pcenter.domain.doctor;

import com.example.pcenter.domain.appointment.Appointment;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "doctors", schema = "rcenter")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "surname")
    private String surname; // фамилия
    @Column(name = "name")
    private String name; // имя
    @Column(name = "lastname")
    private String lastname; // отчество
    @Column(name = "room")
    private Integer numberRoom; // номер кабинета
    @JoinColumn(name = "doctor_id")
    @OneToMany
    private List<Appointment> appointments; // список приемов на день

}
