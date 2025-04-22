package com.francescogiuliante.esameFinale.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {

    private String specialization;
    private int yearsOfExperience;
    private java.time.LocalDate hiringDate;
    private Long accountId;
}
