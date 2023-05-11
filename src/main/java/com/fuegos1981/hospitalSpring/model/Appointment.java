package com.fuegos1981.hospitalSpring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "date_create", nullable = false)
    private Date dateCreate;
    @ManyToOne
    @JoinColumn(name = "diagnosis_id", nullable = false)
    private Diagnosis diagnosis;
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    @Column(name = "medication")
    private String medication;
    @Column(name = "procedures")
    private String procedure;
    @Column(name = "operation")
    private String operation;

    public static Appointment createAppointment(Date dateCreate, Diagnosis diagnosis, Patient patient, Doctor doctor) {
        Appointment appointment = new Appointment();
        appointment.setDateCreate(dateCreate);
        appointment.setDiagnosis(diagnosis);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        return appointment;
    }

    public Appointment() {
        this.setMedication("");
        this.setProcedure("");
        this.setOperation("");
    }

}
