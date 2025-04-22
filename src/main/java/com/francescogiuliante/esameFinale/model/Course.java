package com.francescogiuliante.esameFinale.model;

import com.francescogiuliante.esameFinale.enums.CourseCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private int duration;

    @Enumerated(EnumType.STRING)
    private CourseCategory category;

    private String academicYear;
}
