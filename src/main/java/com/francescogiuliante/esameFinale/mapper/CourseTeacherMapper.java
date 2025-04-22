package com.francescogiuliante.esameFinale.mapper;

import com.francescogiuliante.esameFinale.dto.request.CourseTeacherDTO;
import com.francescogiuliante.esameFinale.dto.response.CourseTeacherResponseDTO;
import com.francescogiuliante.esameFinale.model.CourseTeacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseTeacherMapper {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "teacher.id", target = "teacherId")
    CourseTeacherResponseDTO toResponseDTO(CourseTeacher courseTeacher);

    @Mapping(source = "courseId", target = "course.id")
    @Mapping(source = "teacherId", target = "teacher.id")
    CourseTeacher toEntity(CourseTeacherDTO courseTeacherDTO);
}