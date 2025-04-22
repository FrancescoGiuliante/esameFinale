package com.francescogiuliante.esameFinale.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentExamDTO {

    private Long studentId;
    private Long examId;
    private int grade;
}
