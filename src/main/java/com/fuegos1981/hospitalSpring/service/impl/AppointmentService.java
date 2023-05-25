package com.fuegos1981.hospitalSpring.service.impl;


import com.fuegos1981.hospitalSpring.dto.AppointmentDto;
import com.fuegos1981.hospitalSpring.model.Appointment;
import com.fuegos1981.hospitalSpring.repository.QueryRedactor;
import com.fuegos1981.hospitalSpring.repository.elements.AppointmentRepository;
import com.fuegos1981.hospitalSpring.service.GlobalService;
import com.fuegos1981.hospitalSpring.service.MappingUtils;
import org.springframework.stereotype.Service;

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
    public AppointmentDto readById(Integer id){
        return mappingUtils.mapToAppointmentDto(appointmentRepository.findById(id).get());
    }

    @Override
    public AppointmentDto create(AppointmentDto appointmentDto) {
        return mappingUtils.mapToAppointmentDto(appointmentRepository.save(mappingUtils.mapToAppointment(appointmentDto)));
    }

    @Override
    public AppointmentDto update(AppointmentDto appointmentDto) {
        return mappingUtils.mapToAppointmentDto(appointmentRepository.save(mappingUtils.mapToAppointment(appointmentDto)));
    }

    @Override
    public void delete(AppointmentDto appointmentDto){
        appointmentRepository.delete(mappingUtils.mapToAppointment(appointmentDto));
    }

    @Override
    public List<AppointmentDto> getAll(QueryRedactor qr){
        return appointmentRepository.findAll(qr, Appointment.class).stream()
                .map(mappingUtils::mapToAppointmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public int getSize(QueryRedactor qr) {
        return appointmentRepository.count(qr, Appointment.class);
    }

    @Override
    public List<AppointmentDto> getAll(){
        return appointmentRepository.findAll().stream()
                .map(mappingUtils::mapToAppointmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public int getSize(){
        return (int)appointmentRepository.count();
    }

}
