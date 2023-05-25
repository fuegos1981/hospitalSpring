package com.fuegos1981.hospitalSpring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "diagnosis")
public class Diagnosis implements SimpleModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Pattern(regexp = "[A-ZА-Я][a-zа-я]+",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
