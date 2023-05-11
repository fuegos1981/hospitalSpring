package com.fuegos1981.hospitalSpring.controller;

import com.fuegos1981.hospitalSpring.dto.ScheduleDto;
import com.fuegos1981.hospitalSpring.exception.DBException;
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
@RequestMapping("/hospitalSpring/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    private static Logger logger = LoggerFactory.getLogger(PatientController.class);
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        ScheduleDto scheduleDto =new ScheduleDto();
        //logger.info("gggggg");
        model.addAttribute("schedule", scheduleDto);
        return "edit-schedule";
    }

    @PostMapping("/create")
    public String create(Model model,
                         @Validated @ModelAttribute("schedule") ScheduleDto schedule, BindingResult result) throws DBException, SQLException {
        if (result.hasErrors()) {
            return "edit-schedule";
        }
        scheduleService.create(schedule);
        return "redirect:/hospitalSpring/admin";
    }

    @GetMapping("/update/{schedule_id}")
    public String update(@PathVariable("schedule_id") int scheduleId, Model model) throws DBException, SQLException {
        ScheduleDto schedule = scheduleService.readById(scheduleId);
        model.addAttribute("schedule", schedule);
        return "edit-schedule";
    }

    @PostMapping("/update/{schedule_id}")
    public String update(@PathVariable("schedule_id") int patientId, Model model,
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
