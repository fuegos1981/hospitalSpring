package com.fuegos1981.hospitalSpring.controller;

import com.fuegos1981.hospitalSpring.dto.ScheduleDto;
import com.fuegos1981.hospitalSpring.exception.DBException;
import com.fuegos1981.hospitalSpring.service.impl.DoctorService;
import com.fuegos1981.hospitalSpring.service.impl.PatientService;
import com.fuegos1981.hospitalSpring.service.impl.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

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
    public String create(Model model) throws SQLException, DBException {
        ScheduleDto scheduleDto =new ScheduleDto();
        model.addAttribute("schedule", scheduleDto);
        model.addAttribute("doctors", doctorService.getAll());
        model.addAttribute("patients", patientService.getAll());
        return "edit-schedule";
    }

    @GetMapping("/create/patient/{patient_id}")
    public String create(@PathVariable("patient_id") int patientId,Model model) throws SQLException, DBException {
        ScheduleDto scheduleDto =new ScheduleDto();
        scheduleDto.setPatientId(patientId);
        //logger.info("gggggg");
        model.addAttribute("schedule", scheduleDto);
        model.addAttribute("doctors", doctorService.getAll());
        model.addAttribute("patients", patientService.getAll());
        return "edit-schedule";
    }

    @PostMapping({"/create/patient/{patient_id}", "/create/doctor/{doctor_id}"})
    public String create(@Validated @ModelAttribute("schedule") ScheduleDto schedule, BindingResult result) throws DBException, SQLException {
        String res = "redirect:/hospitalSpring/admin";
        if (result.hasErrors()) {
            res = "edit-schedule";
        } else {
            scheduleService.create(schedule);
        }
        return res;
    }

    @GetMapping("/create/doctor/{doctor_id}")
    public String createDoc(@PathVariable("doctor_id") int doctorId,Model model) throws SQLException, DBException {
        ScheduleDto scheduleDto =new ScheduleDto();
        scheduleDto.setDoctorId(doctorId);
        //logger.info("gggggg");
        model.addAttribute("schedule", scheduleDto);
        model.addAttribute("doctors", doctorService.getAll());
        model.addAttribute("patients", patientService.getAll());
        return "edit-schedule";
    }

    @GetMapping("/update/{schedule_id}/patient/{patient_id}")
    public String update(@PathVariable("schedule_id") int scheduleId,@PathVariable("patient_id") int patientId, Model model) throws DBException, SQLException {
        ScheduleDto schedule = scheduleService.readById(scheduleId);
        model.addAttribute("schedule", schedule);
        return "edit-schedule";
    }

    @PostMapping("/update/{schedule_id}/patient/{patient_id}")
    public String update(@PathVariable("schedule_id") int scheduleId, @PathVariable("patient_id") int patientId, Model model,
                         @Validated @ModelAttribute("schedule")ScheduleDto schedule, BindingResult result) throws DBException, SQLException {
        if (result.hasErrors()) {
            return "edit-schedule";
        }
        scheduleService.update(schedule);
        return "redirect:/hospitalSpring/admin";
    }
    @GetMapping("/delete/{schedule_id}")
    public String delete(@PathVariable("schedule_id") int scheduleId) throws SQLException, DBException {
        scheduleService.delete(scheduleService.readById(scheduleId));
        return "redirect:/hospitalSpring/admin";
    }
}
