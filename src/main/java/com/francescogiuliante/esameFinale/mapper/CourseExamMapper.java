package com.francescogiuliante.esameFinale.mapper;

import com.francescogiuliante.esameFinale.dto.request.CourseExamDTO;
import com.francescogiuliante.esameFinale.dto.response.CourseExamResponseDTO;
import com.francescogiuliante.esameFinale.model.CourseExam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseExamMapper {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "exam.id", target = "examId")
    CourseExamResponseDTO toResponseDTO(CourseExam courseExam);

    @Mapping(source = "courseId", target = "course.id")
    @Mapping(source = "examId", target = "exam.id")
    CourseExam toEntity(CourseExamDTO courseExamDTO);
}