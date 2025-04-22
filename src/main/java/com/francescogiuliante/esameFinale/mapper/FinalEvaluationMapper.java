package com.francescogiuliante.esameFinale.mapper;

import com.francescogiuliante.esameFinale.dto.request.FinalEvaluationDTO;
import com.francescogiuliante.esameFinale.dto.response.FinalEvaluationResponseDTO;
import com.francescogiuliante.esameFinale.model.FinalEvaluation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FinalEvaluationMapper {

    @Mapping(source = "student.account.firstName", target = "studentName")
    @Mapping(source = "course.name", target = "courseName")
    FinalEvaluationResponseDTO toResponseDTO(FinalEvaluation finalEvaluation);

    @Mapping(source = "studentId", target = "student.id")
    @Mapping(source = "courseId", target = "course.id")
    FinalEvaluation toEntity(FinalEvaluationDTO finalEvaluationDTO);
}