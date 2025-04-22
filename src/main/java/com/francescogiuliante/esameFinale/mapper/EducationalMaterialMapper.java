package com.francescogiuliante.esameFinale.mapper;

import com.francescogiuliante.esameFinale.dto.request.EducationalMaterialDTO;
import com.francescogiuliante.esameFinale.dto.response.EducationalMaterialResponseDTO;
import com.francescogiuliante.esameFinale.model.EducationalMaterial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EducationalMaterialMapper {

    @Mapping(source = "course.name", target = "courseName")
    @Mapping(source = "teacher.account.firstName", target = "teacherName")
    EducationalMaterialResponseDTO toResponseDTO(EducationalMaterial educationalMaterial);

    @Mapping(source = "courseId", target = "course.id")
    @Mapping(source = "teacherId", target = "teacher.id")
    EducationalMaterial toEntity(EducationalMaterialDTO educationalMaterialDTO);
}