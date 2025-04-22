package com.francescogiuliante.esameFinale.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    private String name;
    private String description;
    private int duration;
    private String category;
    private String academicYear;
}
