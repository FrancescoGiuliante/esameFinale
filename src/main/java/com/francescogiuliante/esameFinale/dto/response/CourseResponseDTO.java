package com.francescogiuliante.esameFinale.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDTO {

    private String courseName;
    private String courseCategory;
    private int duration;
    private String description;
    private String academicYear;
}
