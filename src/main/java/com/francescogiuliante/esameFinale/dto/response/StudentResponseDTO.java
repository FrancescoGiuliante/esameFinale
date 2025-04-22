package com.francescogiuliante.esameFinale.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDTO {

    private String accountName;
    private java.time.LocalDate birthDate;
    private String birthPlace;
    private int enrollmentYear;
}
