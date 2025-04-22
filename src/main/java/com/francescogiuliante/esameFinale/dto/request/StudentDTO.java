package com.francescogiuliante.esameFinale.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private java.time.LocalDate birthDate;
    private String birthPlace;
    private int enrollmentYear;
    private Long accountId;
}
