package com.fuegos1981.hospitalSpring.service;

import com.fuegos1981.hospitalSpring.dto.AppointmentDto;
import com.fuegos1981.hospitalSpring.dto.ScheduleDto;
import com.fuegos1981.hospitalSpring.exception.DBException;
import com.fuegos1981.hospitalSpring.model.Appointment;
import com.fuegos1981.hospitalSpring.model.Schedule;
import com.fuegos1981.hospitalSpring.service.impl.DiagnosisService;
import com.fuegos1981.hospitalSpring.service.impl.DoctorService;
import com.fuegos1981.hospitalSpring.service.impl.PatientService;

import java.sql.SQLException;

public class MappingUtils {
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final DiagnosisService diagnosisService;
    public MappingUtils(DoctorService doctorService, PatientService patientService, DiagnosisService diagnosisService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.diagnosisService = diagnosisService;
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
    public Schedule mapToSchedule(ScheduleDto dto) throws SQLException, DBException {
        Schedule entity = new Schedule();
        entity.setId(dto.getId());
        entity.setDoctor(doctorService.readById(dto.getDoctorId()));
        entity.setPatient(patientService.readById(dto.getPatientId()));
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
    public Appointment mapToAppointment(AppointmentDto dto) throws DBException, SQLException {
        Appointment entity = new Appointment();
        entity.setId(dto.getId());
        entity.setDoctor(doctorService.readById(dto.getDoctorId()));
        entity.setPatient(patientService.readById(dto.getPatientId()));
        entity.setDiagnosis(diagnosisService.readById(dto.getDiagnosisId()));
        entity.setDateCreate(dto.getDateCreate());
        entity.setMedication(dto.getMedication());
        entity.setProcedure(dto.getProcedure());
        entity.setOperation(dto.getOperation());

        return entity;
    }
}
