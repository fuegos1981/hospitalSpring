package com.fuegos1981.hospitalSpring.service;

import com.fuegos1981.hospitalSpring.dto.AppointmentDto;
import com.fuegos1981.hospitalSpring.dto.ScheduleDto;
import com.fuegos1981.hospitalSpring.model.*;
import com.fuegos1981.hospitalSpring.repository.elements.DiagnosisRepository;
import com.fuegos1981.hospitalSpring.repository.elements.DoctorRepository;
import com.fuegos1981.hospitalSpring.repository.elements.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {
    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;
    private DiagnosisRepository diagnosisRepository;


    public MappingUtils(DoctorRepository doctorRepository, PatientRepository patientRepository, DiagnosisRepository diagnosisRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.diagnosisRepository = diagnosisRepository;
    }

    //из entity в dto
    public ScheduleDto mapToScheduleDto(Schedule entity) {
        ScheduleDto dto = new ScheduleDto();
        dto.setId(entity.getId());
        dto.setDoctorId(entity.getDoctor().getId());
        dto.setPatientId(entity.getPatient().getId());
        dto.setDoctorName(entity.getDoctor().getLastName() + " " + entity.getDoctor().getFirstName());
        dto.setPatientName(entity.getPatient().toString());
        dto.setDateVisit(entity.getDateVisit());

        return dto;
    }

    //из dto в entity
    public Schedule mapToSchedule(ScheduleDto dto) {
        Schedule entity = new Schedule();
        entity.setId(dto.getId());
        entity.setDoctor(doctorRepository.findById(dto.getDoctorId()).get());
        entity.setPatient(patientRepository.findById(dto.getPatientId()).get());
        entity.setDateVisit(dto.getDateVisit());
        return entity;
    }


    //из entity в dto
    public AppointmentDto mapToAppointmentDto(Appointment entity) {
        AppointmentDto dto = new AppointmentDto();
        dto.setId(entity.getId());
        dto.setDoctorId(entity.getDoctor().getId());
        dto.setPatientId(entity.getPatient().getId());
        dto.setDiagnosisId(entity.getDiagnosis().getId());
        dto.setDiagnosisName(entity.getDiagnosis().getName());
        dto.setDoctorName(entity.getDoctor().getLastName() + " " + entity.getDoctor().getFirstName());
        dto.setPatientName(entity.getPatient().toString());
        dto.setCategoryName(entity.getDoctor().getCategory().getName());
        dto.setDateCreate(entity.getDateCreate());
        dto.setMedication(entity.getMedication());
        dto.setProcedure(entity.getProcedure());
        dto.setOperation(entity.getOperation());
        return dto;
    }

    //из dto в entity
    public Appointment mapToAppointment(AppointmentDto dto){
        Appointment entity = new Appointment();
        entity.setId(dto.getId());
        entity.setDoctor(doctorRepository.findById(dto.getDoctorId()).get());
        entity.setPatient(patientRepository.findById(dto.getPatientId()).get());
        entity.setDiagnosis(diagnosisRepository.findById(dto.getDiagnosisId()).get());
        entity.setDateCreate(dto.getDateCreate());
        entity.setMedication(dto.getMedication());
        entity.setProcedure(dto.getProcedure());
        entity.setOperation(dto.getOperation());

        return entity;
    }


}
