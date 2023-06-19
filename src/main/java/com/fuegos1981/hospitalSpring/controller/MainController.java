package com.fuegos1981.hospitalSpring.controller;

import com.fuegos1981.hospitalSpring.model.Doctor;
import com.fuegos1981.hospitalSpring.model.Role;
import com.fuegos1981.hospitalSpring.repository.MainQuery;
import com.fuegos1981.hospitalSpring.repository.QueryRedactor;
import com.fuegos1981.hospitalSpring.service.impl.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private DoctorService doctorService;
    private static Logger logger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

   // public MainController(DoctorService doctorService) {
   //     this.doctorService = doctorService;
    //}

    @RequestMapping({"/hospitalSpring"})
    public String home() {
        return "home";
    }

    @RequestMapping({"/hospitalSpring/default"})
    public String homePost(Principal principal) {
        Map<String,Object> selection = new HashMap<>();
        selection.put("el.login",principal.getName());
        Doctor doctor = doctorService.getAll(QueryRedactor.getRedactor(MainQuery.GET_ALL_DOCTORS,selection)).stream().findFirst().orElse(null);
        if ( doctor ==null){
            logger.info("no find doctor!!!!!!!!!!");
            return "redirect:/hospitalSpring/error";
        }
        if(doctor.getRole()== Role.ADMIN){
            return "redirect:/hospitalSpring/admin";
        }
        return "redirect:/hospitalSpring/medic/" + doctor.getId();
    }

}
