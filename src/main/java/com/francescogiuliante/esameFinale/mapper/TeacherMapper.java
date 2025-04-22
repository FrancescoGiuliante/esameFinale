package com.francescogiuliante.esameFinale.mapper;

import com.francescogiuliante.esameFinale.dto.request.TeacherDTO;
import com.francescogiuliante.esameFinale.dto.response.TeacherResponseDTO;
import com.francescogiuliante.esameFinale.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    @Mapping(target = "accountName", expression = "java(teacher.getAccount().getFirstName() + ' ' + teacher.getAccount().getLastName())")
    TeacherResponseDTO toResponseDTO(Teacher teacher);

    @Mapping(source = "accountId", target = "account.id")
    Teacher toEntity(TeacherDTO teacherDTO);
}