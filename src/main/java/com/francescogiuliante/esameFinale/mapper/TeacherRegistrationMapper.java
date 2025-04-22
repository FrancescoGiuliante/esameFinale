package com.francescogiuliante.esameFinale.mapper;

import com.francescogiuliante.esameFinale.dto.request.registration.TeacherRegistrationDTO;
import com.francescogiuliante.esameFinale.model.Account;
import com.francescogiuliante.esameFinale.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeacherRegistrationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", expression = "java(java.time.LocalDate.now())")
    Account toAccount(TeacherRegistrationDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true) // Verrà settato nel service
    Teacher toTeacher(TeacherRegistrationDTO dto);
}