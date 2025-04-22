package com.francescogiuliante.esameFinale.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinalEvaluationResponseDTO {

    private String studentName;
    private String courseName;
    private int finalGrade;
    private String finalFeedback;
}
