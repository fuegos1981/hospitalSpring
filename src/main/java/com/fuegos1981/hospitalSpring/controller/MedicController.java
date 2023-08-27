package com.fuegos1981.hospitalSpring.controller;

import com.fuegos1981.hospitalSpring.service.impl.AppointmentService;
import com.fuegos1981.hospitalSpring.service.impl.DoctorService;
import com.fuegos1981.hospitalSpring.service.impl.PatientService;
import com.fuegos1981.hospitalSpring.service.impl.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MedicController {
    private ScheduleService scheduleService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;

    public MedicController(PatientService patientService, DoctorService doctorService,
                           ScheduleService scheduleService,AppointmentService appointmentService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.scheduleService = scheduleService;
        this.appointmentService = appointmentService;

    }

    @RequestMapping(value="/hospitalSpring/medic/{doctor_id}")
    public String med(@PathVariable("doctor_id") Integer doctorId,
                      @RequestParam Map<String,String> allParams,
                      Model model){
        model.addAttribute("patients",patientService.getAll());
        model.addAttribute("doctors",doctorService.getAll());
        model.addAttribute("doctor",doctorService.readById(doctorId));
        Map<String,Object> selection = new HashMap<>();
        selection.put("doctor.id",doctorId);
        ControllerUtils.fillSchedules(model,allParams,selection, scheduleService);
        ControllerUtils.fillAppointments(model,allParams,selection, appointmentService);
        //allParams.keySet().forEach(x-> logger.info("x="+x+"v="+allParams.get(x)));
        //logger.info(pag_d);
        model.addAttribute("maxCountOnPage",10);

        return "medic";
    }
}
