package com.francescogiuliante.esameFinale.repository;

import com.francescogiuliante.esameFinale.enums.EnrollmentStatus;
import com.francescogiuliante.esameFinale.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudent_Id(Long studentId);
    List<Enrollment> findByCourse_Id(Long courseId);
    List<Enrollment> findByEnrollmentDate(LocalDate enrollmentDate);
    List<Enrollment> findByEnrollmentStatus(EnrollmentStatus enrollmentStatus);
    Optional<Enrollment> findByStudent_IdAndCourse_Id(Long studentId, Long courseId);
    Optional<Enrollment> findById(Long id);
}