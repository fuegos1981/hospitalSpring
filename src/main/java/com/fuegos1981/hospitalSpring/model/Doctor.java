package com.fuegos1981.hospitalSpring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Pattern(regexp = "[A-ZА-Я][a-zа-я]+",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Pattern(regexp = "[A-ZА-Я][a-zа-я]+",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @Pattern(regexp = "[A-ZА-Я][a-zа-я]+",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    @Column(name = "login", nullable = false)
    private String login;
    @Pattern(regexp = "[A-ZА-Я][a-zа-я]+",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public static Doctor createDoctor(String lastName, String firstName, Category category) {
        Doctor doctor = new Doctor();
        doctor.setLastName(lastName);
        doctor.setFirstName(firstName);
        doctor.setCategory(category);
        return  doctor;
    }

    @Override
    public String toString() {
        return lastName +" "+firstName+", " + category;
    }

}
