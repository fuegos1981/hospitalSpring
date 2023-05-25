package com.fuegos1981.hospitalSpring.service.impl;

import com.fuegos1981.hospitalSpring.exception.DBException;
import com.fuegos1981.hospitalSpring.model.Patient;
import com.fuegos1981.hospitalSpring.repository.QueryRedactor;
import com.fuegos1981.hospitalSpring.repository.elements.PatientRepository;
import com.fuegos1981.hospitalSpring.service.GlobalService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PatientService implements GlobalService<Patient> {

    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient readById(Integer id){
        if (id == null)
            return null;
        else
            return patientRepository.getReferenceById(id);
    }

    @Override
    public Patient create(Patient patient){

        return patientRepository.save(patient);

    }

    @Override
    public Patient update(Patient patient){

        return patientRepository.save(patient);
    }

    @Override
    public void delete(Patient patient) throws DBException {
        patientRepository.delete(patient);
    }

    @Override
    public List<Patient> getAll(QueryRedactor qr){
       return patientRepository.findAll(qr, Patient.class);
    }

    public int getSize(QueryRedactor qr){
        return patientRepository.count(qr,Patient.class);
    }

    @Override
    public List<Patient> getAll(){
        return patientRepository.findAll();
    }

    public int getSize(){
        return (int) patientRepository.count();
    }

}
