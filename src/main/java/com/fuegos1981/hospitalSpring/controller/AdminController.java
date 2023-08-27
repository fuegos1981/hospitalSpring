package com.fuegos1981.hospitalSpring.controller;

import com.fuegos1981.hospitalSpring.repository.MainQuery;
import com.fuegos1981.hospitalSpring.repository.QueryRedactor;
import com.fuegos1981.hospitalSpring.repository.SortRule;
import com.fuegos1981.hospitalSpring.service.impl.CategoryService;
import com.fuegos1981.hospitalSpring.service.impl.DoctorService;
import com.fuegos1981.hospitalSpring.service.impl.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AdminController {
    private PatientService patientService;
    private DoctorService doctorService;
    private CategoryService categoryService;
    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    private final static String SORT_DOCTOR = "sortDoctor";
    private final static String SORT_PATIENT = "sortPatient";

    public AdminController(PatientService patientService, DoctorService doctorService, CategoryService categoryService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.categoryService = categoryService;
    }

    @RequestMapping(value="/hospitalSpring/admin", method= RequestMethod.GET)
    public String admin(@RequestParam Map<String,String> allParams, Model model){
        fillPatients(allParams, model);
        fillDoctors(allParams, model);

        //logger.info(pag_d);
        model.addAttribute("maxCountOnPage", ControllerUtils.MAX_COUNT_ON_PAGE);

        return "admin";
    }

    private void fillDoctors(Map<String,String> allParams, Model model){
        model.addAttribute("categories",categoryService.getAll());
        Integer categoryId = ControllerUtils.parseID(model,allParams,"category_id");
        Map<String,Object> selection = new HashMap<>();
        if (categoryId!=null) {
            selection.put("category.id", categoryId);
            model.addAttribute("category_id",categoryId);
        }
        String sortDoctor = allParams.get(SORT_DOCTOR);
        sortDoctor=(sortDoctor==null)? SortRule.NAME_ASC.toString():sortDoctor;
        model.addAttribute(SORT_DOCTOR,sortDoctor);
        int[]  limit =ControllerUtils.setMasForPagination(model,allParams, doctorService.getSize((QueryRedactor.getRedactor(MainQuery.GET_SIZE_DOCTOR,selection))), "doctor");
        model.addAttribute("doctors",doctorService.getAll(QueryRedactor.getRedactor(MainQuery.GET_ALL_DOCTORS, selection, SortRule.valueOf(sortDoctor),limit)));
        model.addAttribute("pg_doctor",new PaginationTag("doctor",
                (Integer) model.getAttribute("current_page_doctor"),
                (Integer) model.getAttribute("count_page_doctor")
        ).doTag());
    }

    private void fillPatients(Map<String,String> allParams, Model model){
        String sortPatient = allParams.get(SORT_PATIENT);
        sortPatient=(sortPatient==null)?SortRule.NAME_ASC.toString():sortPatient;
        model.addAttribute(SORT_PATIENT,sortPatient);
        int[] limit =ControllerUtils.setMasForPagination(model,allParams, patientService.getSize(), "patient");
        model.addAttribute("patients",patientService.getAll(QueryRedactor.getRedactor(MainQuery.GET_ALL_PATIENTS,SortRule.valueOf(sortPatient), limit)));
        model.addAttribute("pg_patient",new PaginationTag("patient",
                (Integer) model.getAttribute("current_page_patient"),
                (Integer) model.getAttribute("count_page_patient")
        ).doTag());
    }
}
