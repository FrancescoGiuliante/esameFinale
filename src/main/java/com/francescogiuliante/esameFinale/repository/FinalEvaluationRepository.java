package com.francescogiuliante.esameFinale.repository;

import com.francescogiuliante.esameFinale.model.FinalEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinalEvaluationRepository extends JpaRepository<FinalEvaluation, Long> {

    List<FinalEvaluation> findByStudent_Id(Long studentId);
    List<FinalEvaluation> findByCourse_Id(Long courseId);
    Optional<FinalEvaluation> findByStudent_IdAndCourse_Id(Long studentId, Long courseId);
    List<FinalEvaluation> findByFinalGradeGreaterThanEqual(int grade);
    List<FinalEvaluation> findByFinalGradeLessThanEqual(int grade);
}