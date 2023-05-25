package com.fuegos1981.hospitalSpring.service.impl;

import com.fuegos1981.hospitalSpring.model.Doctor;
import com.fuegos1981.hospitalSpring.repository.QueryRedactor;
import com.fuegos1981.hospitalSpring.repository.elements.DoctorRepository;
import com.fuegos1981.hospitalSpring.service.GlobalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService implements GlobalService<Doctor> {
    private DoctorRepository doctorRepository;

    private DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor readById(Integer id){
        if (id == null) {
            return null;
        } else {
            return doctorRepository.findById(id).get();
        }

    }

    public Doctor readByLoginPassword(String login, String pass){
        return doctorRepository.findByLoginAndPassword(login, pass);
    }

    public Doctor readByLogin(String login) {
        return doctorRepository.findByLogin(login);
    }

    @Override
    public Doctor create(Doctor doctor){
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor update(Doctor doctor){
        return doctorRepository.save(doctor);
    }

    @Override
    public void delete(Doctor doctor) {
        doctorRepository.delete(doctor);
    }

    @Override
    public List<Doctor> getAll(QueryRedactor qr){
        return doctorRepository.findAll(qr, Doctor.class);
    }

    public int getSize(QueryRedactor qr) {
        return doctorRepository.count(qr, Doctor.class);
    }

    @Override
    public List<Doctor> getAll(){
        return doctorRepository.findAll();
    }

    public int getSize(){
        return (int)doctorRepository.count();
    }
}
