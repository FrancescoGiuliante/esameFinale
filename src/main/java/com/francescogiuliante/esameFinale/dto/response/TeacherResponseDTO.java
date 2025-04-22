package com.francescogiuliante.esameFinale.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResponseDTO {

    private String accountName;
    private String specialization;
    private int yearsOfExperience;
    private java.time.LocalDate hiringDate;
}
