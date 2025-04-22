package com.francescogiuliante.esameFinale.dto.request.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRegistrationDTO {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private java.time.LocalDate birthDate;
    private String birthPlace;
    private int enrollmentYear;
}