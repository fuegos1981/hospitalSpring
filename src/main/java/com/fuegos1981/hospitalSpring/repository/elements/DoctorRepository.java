package com.fuegos1981.hospitalSpring.repository.elements;


import com.fuegos1981.hospitalSpring.model.Doctor;
import com.fuegos1981.hospitalSpring.repository.CustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Integer>, CustomRepository<Doctor> {
    Doctor findByLoginAndPassword(String login, String password);
    Doctor findByLogin(String login);
}
