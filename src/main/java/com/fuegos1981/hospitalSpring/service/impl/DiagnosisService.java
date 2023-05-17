package com.fuegos1981.hospitalSpring.service.impl;

import com.fuegos1981.hospitalSpring.model.Diagnosis;
import com.fuegos1981.hospitalSpring.repository.MainQuery;
import com.fuegos1981.hospitalSpring.repository.QueryRedactor;
import com.fuegos1981.hospitalSpring.repository.elements.DiagnosisRepository;
import com.fuegos1981.hospitalSpring.service.GlobalService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiagnosisService implements GlobalService<Diagnosis>{

    private DiagnosisRepository diagnosisRepository;
    private AppointmentService appointmentService;

    public DiagnosisService(DiagnosisRepository diagnosisRepository, AppointmentService appointmentService) {
        this.diagnosisRepository = diagnosisRepository;
        this.appointmentService = appointmentService;
    }

    @Override
    public Diagnosis create(Diagnosis diagnosis){
        return diagnosisRepository.save(diagnosis);
    }

    @Override
    public Diagnosis readById(Integer id) throws SQLException{
        if (id == null)
            return null;
        return diagnosisRepository.findById(id).get();
    }

    @Override
    public Diagnosis update(Diagnosis diagnosis){
        return diagnosisRepository.save(diagnosis);
    }

    @Override
    public void delete(Diagnosis diagnosis) throws SQLException {
        Map<String,Object> selection = new HashMap<>();
        selection.put("diagnosis_id", diagnosis.getId());
        if (appointmentService.getAll(QueryRedactor.getRedactor(MainQuery.GET_ALL_APPOINTMENTS,selection)).size()>0){
            throw new SQLException("Appointment use this diagnosis!");
        }
        diagnosisRepository.delete(diagnosis);
    }

    @Override
    public List<Diagnosis> getAll(QueryRedactor qr) throws SQLException {
        return null;//simpleRepository.getAll(qr);
    }

    public int getSize(QueryRedactor qr){
        return 0;//simpleRepository.getSize(qr);
    }

    @Override
    public List<Diagnosis> getAll() throws SQLException {
        return diagnosisRepository.findAll();
    }

    public int getSize(){
        return (int)diagnosisRepository.count();
    }


}
