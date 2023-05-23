package com.fuegos1981.hospitalSpring.controller;

import com.fuegos1981.hospitalSpring.HistoryPatient;
import com.fuegos1981.hospitalSpring.exception.DBException;
import com.fuegos1981.hospitalSpring.model.Gender;
import com.fuegos1981.hospitalSpring.model.Patient;
import com.fuegos1981.hospitalSpring.repository.MainQuery;
import com.fuegos1981.hospitalSpring.repository.QueryRedactor;
import com.fuegos1981.hospitalSpring.repository.SortRule;
import com.fuegos1981.hospitalSpring.service.impl.AppointmentService;
import com.fuegos1981.hospitalSpring.service.impl.PatientService;
import com.fuegos1981.hospitalSpring.service.impl.ScheduleService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/hospitalSpring/patients")
public class PatientController {
    private final PatientService patientService;
    private final ScheduleService scheduleService;
    private final AppointmentService appointmentService;
    private MessageSource messageSource;
    private static Logger logger = LoggerFactory.getLogger(PatientController.class);
    public PatientController(PatientService patientService, ScheduleService scheduleService, AppointmentService appointmentService, MessageSource messageSource) {
        this.patientService = patientService;
        this.scheduleService = scheduleService;
        this.appointmentService = appointmentService;
        this.messageSource = messageSource;
    }

    @GetMapping("/create")
    public String create(Model model) {
        Patient patient =new Patient();
        patient.setGender(Gender.MALE);
        logger.info("gggggg");
        model.addAttribute("patient", patient);
        model.addAttribute("genders", Gender.values());
        return "edit-patient";
    }

    @PostMapping("/create")
    public String create(Model model,
                         @Validated @ModelAttribute("patient") Patient patient, BindingResult result) throws DBException, SQLException {

        if (result.hasErrors()) {
            model.addAttribute("genders", Gender.values());
            logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!create post patient");
            logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!"+result.getAllErrors().stream().map(e->e.getDefaultMessage()).collect(Collectors.joining()));
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

    @RequestMapping("/read/{patient_id}")
    public String read(@PathVariable("patient_id") int patientId,
                       @RequestParam Map<String,String> allParams,
                       Model model) throws DBException, SQLException {
        Patient patient = patientService.readById(patientId);
        model.addAttribute("patient", patient);
        model.addAttribute("maxCountOnPage", ControllerUtils.MAX_COUNT_ON_PAGE);
        Map<String,Object> selection = new HashMap<>();
        selection.put("patient.id",patientId);
        int[]  limit =ControllerUtils.setMasForPagination(model,allParams, scheduleService.getSize((QueryRedactor.getRedactor(MainQuery.GET_ALL_SCHEDULES,selection))), "schedule");
        model.addAttribute("schedules",scheduleService.getAll(QueryRedactor.getRedactor(MainQuery.GET_ALL_SCHEDULES, selection, SortRule.VISIT_TIME_DESC,limit)));
        String pagText = new PaginationTag("schedule",
                (Integer) model.getAttribute("current_page_schedule"),
                (Integer) model.getAttribute("count_page_schedule")
        ).doTag();
        logger.info("pg_schedule:"+pagText);
        model.addAttribute("pg_schedule",pagText);

        limit =ControllerUtils.setMasForPagination(model,allParams, appointmentService.getSize((QueryRedactor.getRedactor(MainQuery.GET_ALL_APPOINTMENTS,selection))), "appointment");
        model.addAttribute("appointments",appointmentService.getAll(QueryRedactor.getRedactor(MainQuery.GET_ALL_APPOINTMENTS, selection, SortRule.DATE_CREATE_DESC,limit)));
        model.addAttribute("pg_appointment",new PaginationTag("appointment",
                (Integer) model.getAttribute("current_page_appointment"),
                (Integer) model.getAttribute("count_page_appointment")
        ).doTag());

        return "patient-info";
    }

    @GetMapping("/download/{patient_id}")
    @ResponseBody
    public FileSystemResource download(@PathVariable("patient_id") int patientId, HttpServletResponse resp, Locale locale) throws DBException, SQLException {
        Patient patient = patientService.readById(patientId);
        ClassLoader classLoader = HistoryPatient.class.getClassLoader();
        File file = new File(classLoader.getResource("pdf/info.pdf").getFile());
        new HistoryPatient(appointmentService, messageSource).getHistoryPatient(patient, file, locale);
        resp.setContentType("application/pdf");
        resp.setHeader("Content-disposition", "attachment; filename=info.pdf");
        return new FileSystemResource(file);
    }

}
