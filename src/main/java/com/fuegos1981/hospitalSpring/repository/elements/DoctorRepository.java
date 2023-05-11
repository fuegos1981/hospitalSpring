package com.fuegos1981.hospitalSpring.repository.elements;


import com.fuegos1981.hospitalSpring.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Doctor findByLoginAndPassword(String login, String password);
    Doctor findByLogin(String login);
}
