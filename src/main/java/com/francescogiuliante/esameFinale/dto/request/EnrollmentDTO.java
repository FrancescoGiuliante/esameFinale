package com.francescogiuliante.esameFinale.dto.request;

import com.francescogiuliante.esameFinale.enums.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDTO {

    private Long studentId;
    private Long courseId;
    private java.time.LocalDate enrollmentDate;
    private EnrollmentStatus enrollmentStatus;
}
