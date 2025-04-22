package com.francescogiuliante.esameFinale.repository;

import com.francescogiuliante.esameFinale.model.StudentExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentExamRepository extends JpaRepository<StudentExam, Long> {

    List<StudentExam> findByStudent_Id(Long studentId);
    List<StudentExam> findByExam_Id(Long examId);
    Optional<StudentExam> findByStudent_IdAndExam_Id(Long studentId, Long examId);
    List<StudentExam> findByGrade(int grade);
    List<StudentExam> findByGradeGreaterThanEqual(int grade);
    List<StudentExam> findByGradeLessThanEqual(int grade);
}