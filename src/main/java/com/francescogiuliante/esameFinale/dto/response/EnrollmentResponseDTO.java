package com.francescogiuliante.esameFinale.dto.response;

import com.francescogiuliante.esameFinale.enums.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponseDTO {

    private String studentName;
    private String courseName;
    private java.time.LocalDate enrollmentDate;
    private EnrollmentStatus enrollmentStatus;
}
