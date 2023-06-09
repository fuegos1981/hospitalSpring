package com.fuegos1981.hospitalSpring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "visit_time", nullable = false)
    private Date dateVisit;

    public static Schedule createSchedule(Doctor doctor, Patient patient,Date dateVisit){
        Schedule schedule = new Schedule();
        schedule.doctor=doctor;
        schedule.patient=patient;
        schedule.dateVisit = dateVisit;
        return schedule;
    }

}
