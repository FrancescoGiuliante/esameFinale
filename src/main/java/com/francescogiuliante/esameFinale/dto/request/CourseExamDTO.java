package com.francescogiuliante.esameFinale.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseExamDTO {

    private Long courseId;
    private Long examId;
    private java.time.LocalDate examDate;
}
