package com.fuegos1981.hospitalSpring.controller;

import com.fuegos1981.hospitalSpring.exception.DBException;
import com.fuegos1981.hospitalSpring.model.Gender;
import com.fuegos1981.hospitalSpring.model.Patient;
import com.fuegos1981.hospitalSpring.service.impl.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

@Controller
@RequestMapping("/hospitalSpring/patients")
public class PatientController {
    private final PatientService patientService;
    private static Logger logger = LoggerFactory.getLogger(PatientController.class);
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        Patient patient =new Patient();
        patient.setGender(Gender.MALE);
        logger.info("gggggg");
        model.addAttribute("patient", patient);
        //model.addAttribute("genders", Gender.values());
        return "edit-patient";
    }

    @PostMapping("/create")
    public String create(Model model,
                         @Validated @ModelAttribute("patient") Patient patient, BindingResult result) throws DBException {
        if (result.hasErrors()) {
            //model.addAttribute("genders", Gender.values());
            return "edit-patient";
        }
        patientService.create(patient);
        return "redirect:/hospitalSpring/admin";
    }

    @GetMapping("/update/{patient_id}")
    public String update(@PathVariable("patient_id") int patientId,Model model) throws DBException, SQLException {
        Patient patient = patientService.readById(patientId);
        model.addAttribute("patient", patient);
        model.addAttribute("genders", Gender.values());
        return "edit-patient";
    }

    @PostMapping("/update/{patient_id}")
    public String update(@PathVariable("patient_id") int patientId, Model model,
                         @Validated @ModelAttribute("patient")Patient patient, BindingResult result) throws DBException {
        if (result.hasErrors()) {
            model.addAttribute("genders", Gender.values());
            return "edit-patient";
        }
        patientService.update(patient);
        return "redirect:/hospitalSpring/admin";
    }

}
