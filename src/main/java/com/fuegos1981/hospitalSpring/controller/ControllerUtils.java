package com.fuegos1981.hospitalSpring.controller;

import java.util.Map;
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
}
