package com.fuegos1981.hospitalSpring.controller;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class PaginationTag{
    private MessageSource messageSource;
    public PaginationTag(String name, int current_page, int count_page) {
        this.name = name;
        this.current_page = current_page;
        this.count_page = count_page;
    }

    private String name;
    private int current_page;
    private int count_page;


    public String doTag(){

        String begin="Begin";
        String end= "End";


        //begin= messageSource.getMessage(begin, null, Locale.US);
       // end = messageSource.getMessage(end,null,Locale.US);


        StringBuilder sb = new StringBuilder();
        sb.append("<div class btn-group btn group-xs>")
            .append("<ul id=\"pagination_").append(name).append("\" class = \"pagination pagination-sm\">")
            .append("<li class=\"disabled\"><input type=\"button\" onClick=\"click_page").append("(1").append(",'").append(name).append("')\" name=\"").append(name).append("pag\" class=\"btn btn-info btn-md\" value=\"").append(begin).append("\"/></li>");

        for (int i=1; i<=count_page;i++) {
            String act =i==current_page?"active":"";
            sb.append("<li class=\"").append(act).append("\"><input type=\"button\" onClick=\"click_page").append("(").append(i).append(",'").append(name).append("')\" name=\"").append(name).append("pag\" class=\"btn btn-info btn-md\" value=\"").append(i).append("\"/></li>");
        }
        sb.append("<li><input type=\"button\" onClick=\"click_page").append("(").append(count_page).append(",'").append(name).append("')\" name=\"").append(name).append("pag\" class=\"btn btn-info btn-md\" value=\"").append(end).append("\"/></li>");
        sb.append("<input type=\"hidden\" id =\"pat_comment_").append(name).append("\" name=\"current_page_").append(name).append("\" value=\"").append(current_page).append("\" />");
        sb.append("</div>");

        return sb.toString();
    }

}
