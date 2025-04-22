package com.francescogiuliante.esameFinale.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentProfileDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String birthPlace;
    private Integer enrollmentYear;

    private List<EnrollmentResponseDTO> enrollments;
    private List<FinalEvaluationResponseDTO> finalEvaluations;
    private List<StudentExamResponseDTO> studentExams;
}