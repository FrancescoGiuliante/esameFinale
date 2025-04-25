package com.francescogiuliante.esameFinale.dto.request;

import com.francescogiuliante.esameFinale.enums.CourseCategory;
import jakarta.validation.constraints.Pattern;
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
    private CourseCategory category;

    @Pattern(regexp = "^\\d{4}-\\d{4}$", message = "Academic year must be in the format YYYY-YYYY")
    private String academicYear;
}
