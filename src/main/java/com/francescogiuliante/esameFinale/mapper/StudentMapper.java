package com.francescogiuliante.esameFinale.mapper;

import com.francescogiuliante.esameFinale.dto.request.StudentDTO;
import com.francescogiuliante.esameFinale.dto.response.StudentResponseDTO;
import com.francescogiuliante.esameFinale.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "accountName", expression = "java(student.getAccount().getFirstName() + ' ' + student.getAccount().getLastName())")
    StudentResponseDTO toResponseDTO(Student student);

    @Mapping(source = "accountId", target = "account.id")
    Student toEntity(StudentDTO studentDTO);
}