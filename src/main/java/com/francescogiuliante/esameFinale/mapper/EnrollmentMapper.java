package com.francescogiuliante.esameFinale.mapper;

import com.francescogiuliante.esameFinale.dto.request.EnrollmentDTO;
import com.francescogiuliante.esameFinale.dto.response.EnrollmentResponseDTO;
import com.francescogiuliante.esameFinale.model.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    @Mapping(source = "student.account.firstName", target = "studentName")
    @Mapping(source = "course.name", target = "courseName")
    EnrollmentResponseDTO toResponseDTO(Enrollment enrollment);

    @Mapping(source = "studentId", target = "student.id")
    @Mapping(source = "courseId", target = "course.id")
    Enrollment toEntity(EnrollmentDTO enrollmentDTO);
}