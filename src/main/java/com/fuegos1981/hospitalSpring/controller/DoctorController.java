package com.fuegos1981.hospitalSpring.controller;

import com.fuegos1981.hospitalSpring.exception.DBException;
import com.fuegos1981.hospitalSpring.model.Doctor;
import com.fuegos1981.hospitalSpring.model.Role;
import com.fuegos1981.hospitalSpring.service.impl.CategoryService;
import com.fuegos1981.hospitalSpring.service.impl.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/hospitalSpring/doctors")
public class DoctorController {
    private final DoctorService doctorService;
    private final CategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    public DoctorController(DoctorService doctorService, CategoryService categoryService) {
        this.doctorService = doctorService;
        this.categoryService = categoryService;
    }
    @GetMapping("/create")
    public String create(Model model) throws SQLException {
        Doctor doctor =new Doctor();
        doctor.setRole(Role.NURSE);
        logger.info("gggggg");
        model.addAttribute("doctor", doctor);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("roles", Role.values());
        return "edit-doctor";
    }

    @PostMapping("/create")
    public String create(Model model,
                         @Validated @ModelAttribute("doctor") Doctor doctor, BindingResult result) throws DBException {
        if (result.hasErrors()) {
            //model.addAttribute("genders", Gender.values());
            return "edit-doctor";
        }
        doctorService.create(doctor);
        return "redirect:/hospitalSpring/admin";
    }

    @GetMapping("/update/{doctor_id}")
    public String update(@PathVariable("doctor_id") int patientId, Model model) throws DBException, SQLException {
        Doctor doctor = doctorService.readById(patientId);
        model.addAttribute("doctor", doctor);
        model.addAttribute("roles", Role.values());
        return "edit-doctor";
    }

    @PostMapping("/update/{doctor_id}")
    public String update(@PathVariable("doctor_id") int doctorId, Model model,
                         @Validated @ModelAttribute("doctor")Doctor doctor, BindingResult result) throws DBException {
        if (result.hasErrors()) {
            model.addAttribute("roles", Role.values());
            return "edit-doctor";
        }
        doctorService.update(doctor);
        return "redirect:/hospitalSpring/admin";
    }
}
