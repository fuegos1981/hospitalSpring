package com.fuegos1981.hospitalSpring.controller;

import com.fuegos1981.hospitalSpring.exception.DBException;
import com.fuegos1981.hospitalSpring.service.impl.DoctorService;
import com.fuegos1981.hospitalSpring.service.impl.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;


@Controller
public class MainController {
    private final PatientService patientService;
    private final DoctorService doctorService;
    private static Logger logger = LoggerFactory.getLogger(MainController.class);
    public MainController(PatientService patientService, DoctorService doctorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
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
        model.addAttribute("patients",patientService.getAll());
        model.addAttribute("pg_patient",new PaginationTag("patient",2,5).doTag());
        model.addAttribute("current_page_patient",1);

        model.addAttribute("doctors",doctorService.getAll());
        model.addAttribute("pg_doctor",new PaginationTag("doctor",3,6).doTag());
        model.addAttribute("current_page_doctor",1);
        //allParams.keySet().forEach(x-> logger.info("x="+x+"v="+allParams.get(x)));
        //logger.info(pag_d);
        model.addAttribute("maxCountOnPage",10);

        return "admin";
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
