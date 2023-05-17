package com.fuegos1981.hospitalSpring.controller;

import com.fuegos1981.hospitalSpring.dto.AppointmentDto;
import com.fuegos1981.hospitalSpring.exception.DBException;
import com.fuegos1981.hospitalSpring.service.impl.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/hospitalSpring/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    private static Logger logger = LoggerFactory.getLogger(PatientController.class);
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        AppointmentDto appointmentDto =new AppointmentDto();
        //logger.info("gggggg");
        model.addAttribute("appointmentDto", appointmentDto);
        return "edit-appointment";
    }

    @PostMapping("/create")
    public String create(Model model,
                         @Validated @ModelAttribute("appointment") AppointmentDto appointment, BindingResult result) throws DBException, SQLException {
        if (result.hasErrors()) {
            return "edit-appointment";
        }
        appointmentService.create(appointment);
        return "redirect:/hospitalSpring/admin";
    }

    @GetMapping("/update/{appointment_id}")
    public String update(@PathVariable("appointment_id") int appointmentId, Model model) throws DBException, SQLException {
        AppointmentDto appointment = appointmentService.readById(appointmentId);
        model.addAttribute("appointment", appointment);
        return "edit-appointment";
    }

    @PostMapping("/update/{appointment_id}")
    public String update(@PathVariable("appointment_id") int appointmentId, Model model,
                         @Validated @ModelAttribute("appointment")AppointmentDto appointment, BindingResult result) throws DBException, SQLException {
        if (result.hasErrors()) {
            return "edit-appointment";
        }
        appointmentService.update(appointment);
        return "redirect:/hospitalSpring/admin";
    }
    @GetMapping("/delete/{appointment_id}")
    public String delete(@PathVariable("appointment_id") int appointmentId) throws SQLException, DBException {
        appointmentService.delete(appointmentService.readById(appointmentId));
        return "redirect:/hospitalSpring/admin";
    }
}
