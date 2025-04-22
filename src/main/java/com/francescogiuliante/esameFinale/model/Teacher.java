package com.francescogiuliante.esameFinale.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String specialization;
    private int yearsOfExperience;
    private java.time.LocalDate hiringDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private Account account;
}
