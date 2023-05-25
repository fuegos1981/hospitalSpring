package com.fuegos1981.hospitalSpring.controller;

import com.fuegos1981.hospitalSpring.dto.AppointmentDto;
import com.fuegos1981.hospitalSpring.service.impl.AppointmentService;
import com.fuegos1981.hospitalSpring.service.impl.DiagnosisService;
import com.fuegos1981.hospitalSpring.service.impl.DoctorService;
import com.fuegos1981.hospitalSpring.service.impl.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/hospitalSpring/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private PatientService patientService;
    private DoctorService doctorService;
    private DiagnosisService diagnosisService;

    private static Logger logger = LoggerFactory.getLogger(PatientController.class);
    public AppointmentController(AppointmentService appointmentService,DoctorService doctorService,
                                 PatientService patientService, DiagnosisService diagnosisService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.diagnosisService = diagnosisService;
    }

    @GetMapping("/create")
    public String create(Model model,
                         @RequestParam Map<String,String> allParams){
        AppointmentDto appointmentDto =new AppointmentDto();
        if (allParams.containsKey("doctor_id"))
            appointmentDto.setDoctorId(ControllerUtils.parseID(model, allParams, "doctor_id"));
        if (allParams.containsKey("patient_id"))
            appointmentDto.setPatientId(ControllerUtils.parseID(model, allParams, "patient_id"));
        model.addAttribute("appointment", appointmentDto);
        fillModel(model);

        return "edit-appointment";
    }

    @PostMapping("/create")
    public String create(@RequestParam Map<String,String> allParams,
                         Model model,
                         @Validated @ModelAttribute("appointment") AppointmentDto appointment,
                         BindingResult result){
        if (result.hasErrors()||!allParams.containsKey("submit")) {
            fillModel(model);
            return "edit-appointment";
        }
        appointmentService.create(appointment);
        return getReturnPath(allParams, appointment);
    }

    @GetMapping("/update/{appointment_id}")
    public String update(@PathVariable("appointment_id") int appointmentId, Model model){
        AppointmentDto appointment = appointmentService.readById(appointmentId);
        model.addAttribute("appointment", appointment);
        fillModel(model);
        return "edit-appointment";
    }

    @PostMapping("/update/{appointment_id}")
    public String update(@RequestParam Map<String,String> allParams,
                         Model model,
                         @Validated @ModelAttribute("appointment")AppointmentDto appointment,
                         BindingResult result){
        if (result.hasErrors()||!allParams.containsKey("submit")) {
            fillModel(model);
            return "edit-appointment";
        }
        appointmentService.update(appointment);
        return getReturnPath(allParams, appointment);
    }

    @GetMapping("/delete/{appointment_id}")
    public String delete(@PathVariable("appointment_id") int appointmentId,@RequestParam Map<String,String> allParams) {
        AppointmentDto appointmentDto = appointmentService.readById(appointmentId);
        String path = getReturnPath(allParams, appointmentDto);
        appointmentService.delete(appointmentDto);
        return path;
    }
    private void fillModel(Model model){
        model.addAttribute("diagnosises", diagnosisService.getAll());
        model.addAttribute("patients", patientService.getAll());
        model.addAttribute("doctors", doctorService.getAll());
    }

    private String getReturnPath(Map<String,String> allParams, AppointmentDto appointment){
        String command = allParams.get("command");
        if (command!=null&&command.equals("patient_info"))
            return "redirect:/hospitalSpring/patients/read/"+appointment.getPatientId();
        else if (command!=null&&command.equals("medic")) {
            String el =allParams.containsKey("current_doctor_id") ? allParams.get("current_doctor_id") : appointment.getDoctorId().toString();
            return "redirect:/hospitalSpring/medic/" +el;
        }
        else
            return "redirect:/hospitalSpring/admin";
    }
}
