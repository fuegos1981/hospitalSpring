package com.fuegos1981.hospitalSpring.controller;

import com.fuegos1981.hospitalSpring.exception.DBException;
import com.fuegos1981.hospitalSpring.model.Category;
import com.fuegos1981.hospitalSpring.model.Diagnosis;
import com.fuegos1981.hospitalSpring.model.SimpleModel;
import com.fuegos1981.hospitalSpring.service.GlobalService;
import com.fuegos1981.hospitalSpring.service.impl.CategoryService;
import com.fuegos1981.hospitalSpring.service.impl.DiagnosisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/hospitalSpring/simples")
public class SimpleController {
    private CategoryService categoryService;
    private DiagnosisService diagnosisService;
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    public SimpleController(CategoryService categoryService,DiagnosisService diagnosisService) {
        this.categoryService = categoryService;
        this.diagnosisService = diagnosisService;
    }

    @GetMapping("/create/{simple}")
    public String create(@PathVariable("simple") String simple,Model model) throws ClassNotFoundException {
        Category simpleModel =new Category();
        logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!"+simpleModel+" "+simple);
        model.addAttribute("simpleModel", simpleModel);
        return "edit-simple";
    }

    @GetMapping("/all")
    public String readSimple(Model model) throws DBException, SQLException {

        model.addAttribute("diagnosises", diagnosisService.getAll());
        model.addAttribute("pg_diagnosis",new PaginationTag("diagnosis",2,5).doTag());
        model.addAttribute("current_page_diagnosis",1);

        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("pg_category",new PaginationTag("category",2,5).doTag());
        model.addAttribute("current_page_category",1);

        model.addAttribute("maxCountOnPage",10);
        return "simples";
    }

    @PostMapping("/create/{simple}")
    public String create(@PathVariable("simple") String simple,Model model,
                         @Validated @ModelAttribute("simpleModel") Category simpleModel, BindingResult result) throws Exception {
        logger.info("eeeee!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        if (result.hasErrors()) {
            logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!"+result.getAllErrors().stream().map(e->e.getDefaultMessage()).collect(Collectors.joining()));
            return "edit-simple";
        }
        //getS(simpleModel, "create");
        return "redirect:/hospitalSpring/admin";
    }


    @GetMapping("/update/{simple}/{simple_id}")
    public <T> String update(@PathVariable("simple") String simple, @PathVariable("simple_id") int simpleId, Model model) throws Exception {
        T simpleModel = (T) getService(simple).readById(simpleId);
        model.addAttribute("simpleModel", simpleModel);
        return "edit-simple";
    }

    @PostMapping("/update/{simple}/{simple_id}")
    public <T> String update(@PathVariable("simple") String simple, @PathVariable("simple_id") int simpleId, Model model,
                         @Validated @ModelAttribute("simpleModel")T simpleModel, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return "edit-simple";
        }
        getS(simpleModel, "update");
        return "redirect:/hospitalSpring/admin";
    }

    @GetMapping("/delete/{simple}/{schedule_id}")
    public <T> String delete(@PathVariable("simple") String simple,@PathVariable("simple_id") int simpleId) throws Exception {
        T simpleModel = (T) getService(simple).readById(simpleId);
        getS(simpleModel, "update");
        return "redirect:/hospitalSpring/admin";
    }

    GlobalService<?> getService(String simple) throws Exception {
        if(simple.equalsIgnoreCase("category"))
            return categoryService;
        else if (simple.equalsIgnoreCase("diagnosis"))
            return diagnosisService;
        else
            throw new Exception("not_correct_path");
    }

    <T>void getS(T t, String operation) throws DBException, SQLException {
        if(t instanceof Category){
            switch (operation) {
                case "create" -> categoryService.create((Category) t);
                case "update" -> categoryService.update((Category) t);
                case "delete" -> categoryService.delete((Category) t);
            }
        }
        else if(t instanceof Diagnosis) {
            switch (operation) {
                case "create" -> diagnosisService.create((Diagnosis) t);
                case "update" -> diagnosisService.update((Diagnosis) t);
                case "delete" -> diagnosisService.delete((Diagnosis) t);
            }
        }
    }

}
