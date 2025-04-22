package com.francescogiuliante.esameFinale.mapper;

import com.francescogiuliante.esameFinale.dto.request.ExamDTO;
import com.francescogiuliante.esameFinale.dto.response.ExamResponseDTO;
import com.francescogiuliante.esameFinale.model.Exam;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExamMapper {

    ExamResponseDTO toResponseDTO(Exam exam);

    Exam toEntity(ExamDTO examDTO);
}