package com.francescogiuliante.esameFinale.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinalEvaluationDTO {

    private Long studentId;
    private Long courseId;
    private int finalGrade;
    private String finalFeedback;
}