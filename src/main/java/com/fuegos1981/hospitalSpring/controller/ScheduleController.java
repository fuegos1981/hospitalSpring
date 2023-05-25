package com.fuegos1981.hospitalSpring.controller;

import com.fuegos1981.hospitalSpring.dto.ScheduleDto;
import com.fuegos1981.hospitalSpring.exception.DBException;
import com.fuegos1981.hospitalSpring.service.impl.DoctorService;
import com.fuegos1981.hospitalSpring.service.impl.PatientService;
import com.fuegos1981.hospitalSpring.service.impl.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/hospitalSpring/schedules")
public class ScheduleController {
    private ScheduleService scheduleService;
    private PatientService patientService;
    private DoctorService doctorService;

    private static Logger logger = LoggerFactory.getLogger(PatientController.class);

    public ScheduleController(ScheduleService scheduleService,PatientService patientService,DoctorService doctorService) {
        this.scheduleService= scheduleService;
        this.patientService= patientService;
        this.doctorService= doctorService;
    }
    @GetMapping("/create")
    public String create(@RequestParam(required = false, value="patient_id") Integer  patientId,
                         @RequestParam(required = false, value="doctor_id") Integer  doctorId,
                         Model model){
        ScheduleDto schedule =new ScheduleDto();
        if (patientId!=null){
            schedule.setPatientId(patientId);
        }
        if (doctorId!=null){
            schedule.setDoctorId(doctorId);
        }
        model.addAttribute("schedule", schedule);
        fillModel(model);
        return "edit-schedule";
    }


    @PostMapping({"/create"})
    public String create(@RequestParam Map<String,String> allParams,
                         Model model,
                         @Validated @ModelAttribute("schedule") ScheduleDto schedule,
                         BindingResult result){

        if (result.hasErrors()||!allParams.containsKey("submit")) {
            fillModel(model);
            logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!create post schedule");
            logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!"+result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining()));
            return "edit-schedule";
        } else {
            scheduleService.create(schedule);
            return getReturnPath(allParams, schedule);
        }
    }


    @GetMapping("/update/{schedule_id}")
    public String update(@PathVariable("schedule_id") Integer scheduleId,
                         Model model){
        ScheduleDto schedule = scheduleService.readById(scheduleId);
        model.addAttribute("schedule", schedule);
        fillModel(model);
        return "edit-schedule";
    }

    @PostMapping("/update/{schedule_id}")
    public String update(@RequestParam Map<String,String> allParams,
                         Model model,
                         @Validated @ModelAttribute("schedule")ScheduleDto schedule,
                         BindingResult result){
        if (result.hasErrors()||!allParams.containsKey("submit")) {
            fillModel(model);
            return "edit-schedule";
        }
        scheduleService.update(schedule);
        return getReturnPath(allParams, schedule);
    }
    @GetMapping("/delete/{schedule_id}")
    public String delete(@PathVariable("schedule_id") Integer scheduleId,
                         @RequestParam Map<String,String> allParams) throws SQLException, DBException {
        ScheduleDto schedule =scheduleService.readById(scheduleId);
        String path = getReturnPath(allParams, schedule);
        scheduleService.delete(schedule);
        return path;
    }

    private void fillModel(Model model){
        model.addAttribute("doctors", doctorService.getAll());
        model.addAttribute("patients", patientService.getAll());
    }

    private String getReturnPath(Map<String,String> allParams, ScheduleDto schedule){
        String command = allParams.get("command");
        if (command!=null&&command.equals("patient_info"))
            return"redirect:/hospitalSpring/patients/read/"+schedule.getPatientId();
        else if (command!=null&&command.equals("medic")) {
            String el = allParams.containsKey("current_doctor_id") ? allParams.get("current_doctor_id") : schedule.getDoctorId().toString();
            return "redirect:/hospitalSpring/medic/" + el;
        }
        else
            return "redirect:/hospitalSpring/admin";
    }
}
