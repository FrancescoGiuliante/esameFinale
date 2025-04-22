package com.francescogiuliante.esameFinale.repository;

import com.francescogiuliante.esameFinale.model.CourseTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CourseTeacherRepository extends JpaRepository<CourseTeacher, Long> {

    List<CourseTeacher> findByCourse_Id(Long courseId);
    List<CourseTeacher> findByTeacher_Id(Long teacherId);
    List<CourseTeacher> findByStartDate(LocalDate startDate);
}