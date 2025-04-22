package com.francescogiuliante.esameFinale.model;

import com.francescogiuliante.esameFinale.enums.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    private Course course;

    private java.time.LocalDate enrollmentDate;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus enrollmentStatus;
}
