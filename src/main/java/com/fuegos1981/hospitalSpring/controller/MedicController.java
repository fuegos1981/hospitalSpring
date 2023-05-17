package com.fuegos1981.hospitalSpring.controller;

import com.fuegos1981.hospitalSpring.exception.DBException;
import com.fuegos1981.hospitalSpring.service.impl.AppointmentService;
import com.fuegos1981.hospitalSpring.service.impl.DoctorService;
import com.fuegos1981.hospitalSpring.service.impl.PatientService;
import com.fuegos1981.hospitalSpring.service.impl.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
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

    @GetMapping(value="/hospitalSpring/medic/{doctor_id}")
    public String medic(@RequestParam Map<String,String> allParams, Model model) throws DBException, SQLException {
        model.addAttribute("patients",patientService.getAll());
        model.addAttribute("doctors",doctorService.getAll());

        model.addAttribute("schedules",scheduleService.getAll());
        model.addAttribute("pg_schedule",new PaginationTag("schedule",2,5).doTag());
        model.addAttribute("current_page_schedule",1);

        model.addAttribute("appointments",appointmentService.getAll());
        model.addAttribute("pg_appointment",new PaginationTag("appointment",3,6).doTag());
        model.addAttribute("current_page_appointment",1);
        //allParams.keySet().forEach(x-> logger.info("x="+x+"v="+allParams.get(x)));
        //logger.info(pag_d);
        model.addAttribute("maxCountOnPage",10);

        return "medic";
    }

    @PostMapping(value="/hospitalSpring/medic/{doctor_id}")
    public String med(@RequestParam Map<String,String> allParams, Model model) throws DBException, SQLException {
        model.addAttribute("patients",patientService.getAll());
        model.addAttribute("doctors",doctorService.getAll());

        model.addAttribute("schedules",scheduleService.getAll());
        model.addAttribute("pg_schedule",new PaginationTag("schedule",2,5).doTag());
        model.addAttribute("current_page_schedule",1);

        model.addAttribute("appointments",appointmentService.getAll());
        model.addAttribute("pg_appointment",new PaginationTag("appointment",3,6).doTag());
        model.addAttribute("current_page_appointment",1);
        //allParams.keySet().forEach(x-> logger.info("x="+x+"v="+allParams.get(x)));
        //logger.info(pag_d);
        model.addAttribute("maxCountOnPage",10);

        return "medic";
    }
}
