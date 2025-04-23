package com.francescogiuliante.esameFinale.model;

import com.francescogiuliante.esameFinale.enums.EducationalMaterialType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EducationalMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Course course;

    @ManyToOne
    private Teacher teacher;

    private String title;

    @Enumerated(EnumType.STRING)
    private EducationalMaterialType type;

    private String url;
    private java.time.LocalDate publicationDate;
}
