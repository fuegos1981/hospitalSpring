package com.fuegos1981.hospitalSpring.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class ScheduleDto {
    private Integer id;
    @NotNull
    private Integer doctorId;
    @NotNull
    private Integer patientId;
    private String doctorName;
    private String patientName;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date dateVisit;


    public static ScheduleDto createScheduleDto(Integer doctorId, Integer patientId, Date dateVisit){
        ScheduleDto schedule = new ScheduleDto();
        schedule.doctorId=doctorId;
        schedule.patientId=patientId;
        schedule.dateVisit = dateVisit;
        return schedule;
    }
    public String getDateVisitString(){
        String dateVisitString ="";
        if (dateVisit!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            dateVisitString = sdf.format(dateVisit);

        }
        return dateVisitString;
    }

}
