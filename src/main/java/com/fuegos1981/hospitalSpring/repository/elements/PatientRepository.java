package com.fuegos1981.hospitalSpring.repository.elements;

import com.fuegos1981.hospitalSpring.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PatientRepository extends JpaRepository<Patient, Integer>{

}
