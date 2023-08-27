package com.fuegos1981.hospitalSpring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    private Integer id;
    @Pattern(regexp = "[A-ZА-Я][a-zа-я]+",
            message = "{ValidErrorName}")
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Pattern(regexp = "[A-ZА-Я][a-zа-я]+",
            message = "{ValidErrorName}")
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull
    private Category category;
    @Pattern(regexp = "[A-ZА-Я][A-Za-zА-Яа-я]+",
            message = "{ValidErrorLogin}")
    @Column(name = "login", nullable = false)
    private String login;
    //@Min(value = 3, message = "Should not be less than 3")
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
