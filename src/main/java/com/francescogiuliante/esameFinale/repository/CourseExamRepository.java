package com.francescogiuliante.esameFinale.repository;

import com.francescogiuliante.esameFinale.model.CourseExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CourseExamRepository extends JpaRepository<CourseExam, Long> {

    List<CourseExam> findByCourse_Id(Long courseId);

    List<CourseExam> findByExam_Id(Long examId);

    List<CourseExam> findByExamDate(LocalDate examDate);

    List<CourseExam> findByExamDateGreaterThan(LocalDate examDate);

    List<CourseExam> findByCourse_AcademicYear(String academicYear);
}