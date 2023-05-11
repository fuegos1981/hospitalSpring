package com.fuegos1981.hospitalSpring.repository.elements;


import com.fuegos1981.hospitalSpring.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository  extends JpaRepository<Appointment, Integer> {

}
