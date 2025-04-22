package com.francescogiuliante.esameFinale.repository;

import com.francescogiuliante.esameFinale.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByBirthDate(LocalDate birthDate);

    List<Student> findByBirthDateBefore(LocalDate birthDate);

    List<Student> findByBirthDateAfter(LocalDate birthDate);

    List<Student> findByBirthPlace(String birthPlace);

    List<Student> findByEnrollmentYear(int enrollmentYear);

    List<Student> findByEnrollmentYearGreaterThanEqual(int enrollmentYear);

    List<Student> findByEnrollmentYearLessThanEqual(int enrollmentYear);
}