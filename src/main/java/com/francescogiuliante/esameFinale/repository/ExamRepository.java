package com.francescogiuliante.esameFinale.repository;

import com.francescogiuliante.esameFinale.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findByType(String type);

    List<Exam> findByCredits(int credits);

    List<Exam> findByDurationGreaterThan(int duration);
}