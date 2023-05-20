package com.fuegos1981.hospitalSpring.controller;

import com.fuegos1981.hospitalSpring.exception.DBException;
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
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@Controller
public class MainController {
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final CategoryService categoryService;
    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    private final static String SORT_DOCTOR = "sortDoctor";
    private final static String SORT_PATIENT = "sortPatient";

    public MainController(PatientService patientService, DoctorService doctorService, CategoryService categoryService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.categoryService = categoryService;
    }

    @RequestMapping(value="/hospitalSpring", method=RequestMethod.GET)
    public String home() {
        //model.addAttribute("users",userService.getAll());
        return "home";
    }
    @RequestMapping(value="/hospitalSpring/login", method=RequestMethod.POST)
    public String homePost() {
        //model.addAttribute("users",userService.getAll());
        return "redirect:/hospitalSpring/admin";
    }
    @RequestMapping(value="/hospitalSpring/admin", method=RequestMethod.GET)
    public String admin(@RequestParam Map<String,String> allParams, Model model) throws DBException, SQLException {
        fillPatients(allParams, model);
        fillDoctors(allParams, model);

        //logger.info(pag_d);
        model.addAttribute("maxCountOnPage", ControllerUtils.MAX_COUNT_ON_PAGE);

        return "admin";
    }

    private void fillDoctors(Map<String,String> allParams, Model model) throws SQLException {
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

    private void fillPatients(Map<String,String> allParams, Model model) throws DBException, SQLException {
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
/*
    protected void processRequest(Model model){

        String page;
        try {
            if (!isDownLoad(model)) {
                ActionFactory client = new ActionFactory();
                ActionCommand command = client.defineCommand(model);
                page = command.execute(req, currentMessageLocale);
                if (page != null) {
                    if (page.contains(".jsp")) {
                        RequestDispatcher dispatcher = req.getRequestDispatcher(page);
                        dispatcher.forward(req, resp);
                    } else {
                        if (!page.isEmpty())
                            resp.sendRedirect(page);
                    }
                }
            }
        } catch (Exception e) {
            //logger.error(req.getSession().getId()+"; "+e.getMessage());
            model.addAttribute("message", "something_goes_wrong");
            "error"
        }

    }


    protected boolean isDownLoad(Model model) {
        if (model.getAttribute("download") != null) {
           //ControllerUtils.downloadHistory(req, resp, currentMessageLocale);
            return true;
        }
        return false;
    }
*/
}
