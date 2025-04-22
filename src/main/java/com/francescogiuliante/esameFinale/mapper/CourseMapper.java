package com.francescogiuliante.esameFinale.mapper;

import com.francescogiuliante.esameFinale.dto.request.CourseDTO;
import com.francescogiuliante.esameFinale.dto.response.CourseResponseDTO;
import com.francescogiuliante.esameFinale.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(source = "name", target = "courseName")
    @Mapping(source = "category", target = "courseCategory")
    CourseResponseDTO toResponseDTO(Course course);

    Course toEntity(CourseDTO courseDTO);
}