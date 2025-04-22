package com.francescogiuliante.esameFinale.mapper;

import com.francescogiuliante.esameFinale.dto.request.registration.StudentRegistrationDTO;
import com.francescogiuliante.esameFinale.model.Account;
import com.francescogiuliante.esameFinale.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentRegistrationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", expression = "java(java.time.LocalDate.now())")
    Account toAccount(StudentRegistrationDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true) // Verrà settato nel service
    Student toStudent(StudentRegistrationDTO dto);
}