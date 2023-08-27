package com.fuegos1981.hospitalSpring.service.impl;

import com.fuegos1981.hospitalSpring.model.Doctor;
import com.fuegos1981.hospitalSpring.repository.QueryRedactor;
import com.fuegos1981.hospitalSpring.repository.elements.DoctorRepository;
import com.fuegos1981.hospitalSpring.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorService implements GlobalService<Doctor> {

    private DoctorRepository doctorRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Doctor readById(Integer id){
        if (id == null) {
            return null;
        } else {
            return doctorRepository.findById(id).get();
        }

    }
    @Transactional(readOnly = true)
    public Doctor readByLoginPassword(String login, String pass){
        return doctorRepository.findByLoginAndPassword(login, pass);
    }
    @Transactional(readOnly = true)
    public Doctor readByLogin(String login) {
        return doctorRepository.findByLogin(login);
    }

    @Override
    public Doctor create(Doctor doctor){
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor update(Doctor doctor){
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        return doctorRepository.save(doctor);
    }

    @Override
    public void delete(Doctor doctor) {
        doctorRepository.delete(doctor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Doctor> getAll(QueryRedactor qr){
        return doctorRepository.findAll(qr, Doctor.class);
    }
    @Transactional(readOnly = true)
    public int getSize(QueryRedactor qr) {
        return doctorRepository.count(qr, Doctor.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Doctor> getAll(){
        return doctorRepository.findAll();
    }
    @Transactional(readOnly = true)
    public int getSize(){
        return (int)doctorRepository.count();
    }
}
