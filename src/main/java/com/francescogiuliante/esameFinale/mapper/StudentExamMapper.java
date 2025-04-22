package com.francescogiuliante.esameFinale.mapper;

import com.francescogiuliante.esameFinale.dto.request.StudentExamDTO;
import com.francescogiuliante.esameFinale.dto.response.StudentExamResponseDTO;
import com.francescogiuliante.esameFinale.model.StudentExam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentExamMapper {

    @Mapping(source = "student.account.firstName", target = "studentName")
    @Mapping(source = "exam.name", target = "examName")
    StudentExamResponseDTO toResponseDTO(StudentExam studentExam);

    @Mapping(source = "studentId", target = "student.id")
    @Mapping(source = "examId", target = "exam.id")
    StudentExam toEntity(StudentExamDTO studentExamDTO);
}