package com.fuegos1981.hospitalSpring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "patient")
public class Patient implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Pattern(regexp = "[A-ZА-Я][a-zа-я]+",
            message = "{ValidErrorName}")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Pattern(regexp = "[A-ZА-Я][a-zа-я]+",
            message = "{ValidErrorName}")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @Pattern(regexp = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
            message = "ValidErrorEMAIL")
    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public static Patient createPatient(String lastName, String firstName, Date birthday, String email, Gender gender){
        Patient patient = new Patient();
        patient.setLastName(lastName);
        patient.setFirstName(firstName);
        patient.setBirthday(birthday);
        patient.setEmail(email);
        patient.setGender(gender);
        return patient;
    }

    public String getBirthdayString(){
        String birthdayString ="";
        if (birthday!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            birthdayString = sdf.format(birthday);

        }
        return birthdayString;
    }

    @Override
    public String toString() {
        return lastName +" "+ firstName;
    }
}
