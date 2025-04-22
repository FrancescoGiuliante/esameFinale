package com.francescogiuliante.esameFinale.repository;

import com.francescogiuliante.esameFinale.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    List<Teacher> findBySpecialization(String specialization);
    List<Teacher> findByYearsOfExperienceGreaterThanEqual(int years);
}