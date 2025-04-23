package com.francescogiuliante.esameFinale.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CourseExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Course course;

    @ManyToOne
    private Exam exam;

    private java.time.LocalDate examDate;
}
