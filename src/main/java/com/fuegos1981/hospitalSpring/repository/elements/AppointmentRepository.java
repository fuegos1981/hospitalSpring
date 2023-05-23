package com.fuegos1981.hospitalSpring.repository.elements;


import com.fuegos1981.hospitalSpring.model.Appointment;
import com.fuegos1981.hospitalSpring.repository.CustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository  extends JpaRepository<Appointment, Integer>, CustomRepository<Appointment> {

}
