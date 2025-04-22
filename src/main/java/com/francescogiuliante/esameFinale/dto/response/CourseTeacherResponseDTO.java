package com.francescogiuliante.esameFinale.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseTeacherResponseDTO {

    private Long courseId;
    private Long teacherId;
    private LocalDate startDate;
}