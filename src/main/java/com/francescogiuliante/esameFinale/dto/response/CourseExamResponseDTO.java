package com.francescogiuliante.esameFinale.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseExamResponseDTO {

    private Long courseId;
    private Long examId;
    private java.time.LocalDate examDate;
}