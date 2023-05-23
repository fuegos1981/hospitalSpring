package com.fuegos1981.hospitalSpring.controller;

import com.fuegos1981.hospitalSpring.service.impl.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    private final DoctorService doctorService;
    private static Logger logger = LoggerFactory.getLogger(MainController.class);


    public MainController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @RequestMapping(value="/hospitalSpring", method=RequestMethod.GET)
    public String home() {
        //model.addAttribute("users",userService.getAll());
        return "home";
    }
    @RequestMapping(value="/hospitalSpring/login", method=RequestMethod.POST)
    public String homePost(@RequestParam(required = false, value="submit") String  submit) {
        //model.addAttribute("users",userService.getAll());

        return submit==null?"home":"redirect:/hospitalSpring/admin";
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
