package com.fuegos1981.hospitalSpring.service.impl;


import com.fuegos1981.hospitalSpring.dto.AppointmentDto;
import com.fuegos1981.hospitalSpring.exception.DBException;
import com.fuegos1981.hospitalSpring.model.SimpleModel;
import com.fuegos1981.hospitalSpring.repository.QueryRedactor;
import com.fuegos1981.hospitalSpring.repository.elements.AppointmentRepository;
import com.fuegos1981.hospitalSpring.service.GlobalService;
import com.fuegos1981.hospitalSpring.service.MappingUtils;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService implements GlobalService<AppointmentDto> {
    private AppointmentRepository appointmentRepository;
    private MappingUtils mappingUtils;

    private AppointmentService(AppointmentRepository appointmentRepository, MappingUtils mappingUtils) {
        this.appointmentRepository = appointmentRepository;
        this.mappingUtils = mappingUtils;
    }


    @Override
    public AppointmentDto readById(Integer id) throws SQLException{
        return mappingUtils.mapToAppointmentDto(appointmentRepository.findById(id).get());
    }

    @Override
    public AppointmentDto create(AppointmentDto appointmentDto) throws SQLException, DBException {
        return mappingUtils.mapToAppointmentDto(appointmentRepository.save(mappingUtils.mapToAppointment(appointmentDto)));
    }

    @Override
    public AppointmentDto update(AppointmentDto appointmentDto) throws SQLException, DBException {
        return mappingUtils.mapToAppointmentDto(appointmentRepository.save(mappingUtils.mapToAppointment(appointmentDto)));
    }

    @Override
    public void delete(AppointmentDto appointmentDto) throws SQLException, DBException {
        appointmentRepository.delete(mappingUtils.mapToAppointment(appointmentDto));
    }

    @Override
    public List<AppointmentDto> getAll(QueryRedactor qr) throws  SQLException {
        return null;
    }

    @Override
    public int getSize(QueryRedactor qr) {
        return 0;// appointmentRepository.getSize(qr);
    }

    @Override
    public List<AppointmentDto> getAll() throws SQLException {
        return appointmentRepository.findAll().stream()
                .map(mappingUtils::mapToAppointmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public int getSize(){
        return (int)appointmentRepository.count();
    }

}
