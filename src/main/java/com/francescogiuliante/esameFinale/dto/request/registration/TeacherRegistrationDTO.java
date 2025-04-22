package com.francescogiuliante.esameFinale.dto.request.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRegistrationDTO {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private String specialization;
    private int yearsOfExperience;
    private java.time.LocalDate hiringDate;
}