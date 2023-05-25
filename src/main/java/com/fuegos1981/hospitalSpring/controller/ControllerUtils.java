package com.fuegos1981.hospitalSpring.controller;

import java.util.Map;

import com.fuegos1981.hospitalSpring.repository.MainQuery;
import com.fuegos1981.hospitalSpring.repository.QueryRedactor;
import com.fuegos1981.hospitalSpring.repository.SortRule;
import com.fuegos1981.hospitalSpring.service.impl.AppointmentService;
import com.fuegos1981.hospitalSpring.service.impl.ScheduleService;
import org.springframework.ui.Model;
public class ControllerUtils {
    public static final int MAX_COUNT_ON_PAGE = 10;

    public static int[] setMasForPagination(Model model,Map<String,String> allParams, int countList, String  name){
        model.addAttribute("maxCountOnPage", MAX_COUNT_ON_PAGE);
        int currentPage = (allParams.containsKey("current_page_"+name))?Integer.valueOf(allParams.get("current_page_"+name)):1;
        model.addAttribute("current_page_"+name, currentPage);
        int countPage =(int)Math.ceil(1.00*countList/MAX_COUNT_ON_PAGE);
        model.addAttribute("count_page_"+name, countPage);
        if (countList<=MAX_COUNT_ON_PAGE){
            return null;
        }
        else{
            return new int[]{(currentPage-1)*MAX_COUNT_ON_PAGE,MAX_COUNT_ON_PAGE};
        }
    }

    public static Integer parseID(Model model,Map<String,String> allParams, String idName) {

        String idStr= allParams.get(idName);
        model.addAttribute(idName,idStr);
        if (idStr==null|| idStr.isEmpty())
            return null;
        else {
            return Integer.valueOf(idStr);
        }
    }

    public static void fillSchedules(Model model, Map<String,String> allParams,
                               Map<String,Object> selection, ScheduleService scheduleService){
        int[] limit =ControllerUtils.setMasForPagination(model,allParams, scheduleService.getSize((QueryRedactor.getRedactor(MainQuery.GET_ALL_SCHEDULES,selection))), "schedule");
        model.addAttribute("schedules",scheduleService.getAll(QueryRedactor.getRedactor(MainQuery.GET_ALL_SCHEDULES, selection, SortRule.VISIT_TIME_DESC,limit)));
        String pagText = new PaginationTag("schedule",
                (Integer) model.getAttribute("current_page_schedule"),
                (Integer) model.getAttribute("count_page_schedule")
        ).doTag();
        model.addAttribute("pg_schedule",pagText);
    }

    public static void fillAppointments(Model model, Map<String,String> allParams,
                                  Map<String,Object> selection, AppointmentService appointmentService){
        int[] limit =ControllerUtils.setMasForPagination(model,allParams, appointmentService.getSize((QueryRedactor.getRedactor(MainQuery.GET_ALL_APPOINTMENTS,selection))), "appointment");
        model.addAttribute("appointments",appointmentService.getAll(QueryRedactor.getRedactor(MainQuery.GET_ALL_APPOINTMENTS, selection, SortRule.DATE_CREATE_DESC,limit)));
        model.addAttribute("pg_appointment",new PaginationTag("appointment",
                (Integer) model.getAttribute("current_page_appointment"),
                (Integer) model.getAttribute("count_page_appointment")
        ).doTag());
    }
}
