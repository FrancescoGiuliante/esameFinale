package com.francescogiuliante.esameFinale.repository;

import com.francescogiuliante.esameFinale.model.Course;
import com.francescogiuliante.esameFinale.enums.CourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByCategory(CourseCategory category);

    List<Course> findByAcademicYear(String academicYear);
}