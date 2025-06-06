package com.francescogiuliante.esameFinale.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CourseTeacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Course course;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Teacher teacher;

    private java.time.LocalDate startDate;
}
