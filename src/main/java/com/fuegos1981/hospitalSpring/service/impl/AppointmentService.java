package com.fuegos1981.hospitalSpring.service.impl;


import com.fuegos1981.hospitalSpring.model.Appointment;
import com.fuegos1981.hospitalSpring.repository.QueryRedactor;
import com.fuegos1981.hospitalSpring.repository.elements.AppointmentRepository;
import com.fuegos1981.hospitalSpring.service.GlobalService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class AppointmentService implements GlobalService<Appointment> {
    private AppointmentRepository appointmentRepository;
    //private final MappingUtils mappingUtils;

    private AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }


    @Override
    public Appointment readById(Integer id) throws SQLException{
       return appointmentRepository.findById(id).get();
    }

    @Override
    public Appointment create(Appointment appointment) throws SQLException {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment update(Appointment appointment) throws SQLException {
        return appointmentRepository.save(appointment);
    }

    @Override
    public void delete(Appointment appointment) throws SQLException {
        appointmentRepository.delete(appointment);
    }

    @Override
    public List<Appointment> getAll(QueryRedactor qr) throws  SQLException {
        return null;
    }

    @Override
    public int getSize(QueryRedactor qr) {
        return 0;// appointmentRepository.getSize(qr);
    }

    @Override
    public List<Appointment> getAll() throws SQLException {
        return appointmentRepository.findAll();
    }

    @Override
    public int getSize(){
        return (int)appointmentRepository.count();
    }

}
