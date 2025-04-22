package com.francescogiuliante.esameFinale.service;

import com.francescogiuliante.esameFinale.dto.request.FinalEvaluationDTO;
import com.francescogiuliante.esameFinale.dto.response.FinalEvaluationResponseDTO;
import com.francescogiuliante.esameFinale.mapper.FinalEvaluationMapper;
import com.francescogiuliante.esameFinale.model.Course;
import com.francescogiuliante.esameFinale.model.FinalEvaluation;
import com.francescogiuliante.esameFinale.model.Student;
import com.francescogiuliante.esameFinale.repository.CourseRepository;
import com.francescogiuliante.esameFinale.repository.FinalEvaluationRepository;
import com.francescogiuliante.esameFinale.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FinalEvaluationService {

    @Autowired
    private FinalEvaluationRepository finalEvaluationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private FinalEvaluationMapper finalEvaluationMapper;

    public List<FinalEvaluationResponseDTO> getAllFinalEvaluations() {
        log.info("Fetching all final evaluations.");
        List<FinalEvaluation> finalEvaluations = finalEvaluationRepository.findAll();
        List<FinalEvaluationResponseDTO> responseDTOs = new ArrayList<>();
        for (FinalEvaluation evaluation : finalEvaluations) {
            responseDTOs.add(finalEvaluationMapper.toResponseDTO(evaluation));
        }
        return responseDTOs;
    }

    public Optional<FinalEvaluationResponseDTO> getFinalEvaluationById(Long id) {
        log.info("Fetching final evaluation with ID: {}", id);
        Optional<FinalEvaluation> evaluationOptional = finalEvaluationRepository.findById(id);
        return evaluationOptional.map(finalEvaluationMapper::toResponseDTO);
    }

    public FinalEvaluationResponseDTO createFinalEvaluation(FinalEvaluationDTO finalEvaluationDTO) {
        log.info("Creating a new final evaluation: {}", finalEvaluationDTO);
        FinalEvaluation evaluation = finalEvaluationMapper.toEntity(finalEvaluationDTO);

        Optional<Student> studentOptional = studentRepository.findById(finalEvaluationDTO.getStudentId());
        if (studentOptional.isEmpty()) {
            log.warn("Student with ID {} not found.", finalEvaluationDTO.getStudentId());
            return null;
        }
        evaluation.setStudent(studentOptional.get());

        Optional<Course> courseOptional = courseRepository.findById(finalEvaluationDTO.getCourseId());
        if (courseOptional.isEmpty()) {
            log.warn("Course with ID {} not found.", finalEvaluationDTO.getCourseId());
            return null;
        }
        evaluation.setCourse(courseOptional.get());

        FinalEvaluation savedEvaluation = finalEvaluationRepository.save(evaluation);
        return finalEvaluationMapper.toResponseDTO(savedEvaluation);
    }

    public Optional<FinalEvaluationResponseDTO> updateFinalEvaluation(Long id, FinalEvaluationDTO finalEvaluationDTO) {
        log.info("Updating final evaluation with ID {}: {}", id, finalEvaluationDTO);
        Optional<FinalEvaluation> existingEvaluationOptional = finalEvaluationRepository.findById(id);
        if (existingEvaluationOptional.isPresent()) {
            FinalEvaluation updatedEvaluation = finalEvaluationMapper.toEntity(finalEvaluationDTO);
            updatedEvaluation.setId(id);

            Optional<Student> studentOptional = studentRepository.findById(finalEvaluationDTO.getStudentId());
            if (studentOptional.isEmpty()) {
                log.warn("Student with ID {} not found.", finalEvaluationDTO.getStudentId());
                return Optional.empty();
            }
            updatedEvaluation.setStudent(studentOptional.get());

            Optional<Course> courseOptional = courseRepository.findById(finalEvaluationDTO.getCourseId());
            if (courseOptional.isEmpty()) {
                log.warn("Course with ID {} not found.", finalEvaluationDTO.getCourseId());
                return Optional.empty();
            }
            updatedEvaluation.setCourse(courseOptional.get());

            FinalEvaluation savedEvaluation = finalEvaluationRepository.save(updatedEvaluation);
            return Optional.of(finalEvaluationMapper.toResponseDTO(savedEvaluation));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteFinalEvaluation(Long id) {
        log.info("Deleting final evaluation with ID: {}", id);
        if (finalEvaluationRepository.existsById(id)) {
            finalEvaluationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<FinalEvaluationResponseDTO> getFinalEvaluationsByStudentId(Long studentId) {
        log.info("Fetching final evaluations for Student ID: {}", studentId);
        List<FinalEvaluation> evaluations = finalEvaluationRepository.findByStudent_Id(studentId);
        List<FinalEvaluationResponseDTO> responseDTOs = new ArrayList<>();
        for (FinalEvaluation evaluation : evaluations) {
            responseDTOs.add(finalEvaluationMapper.toResponseDTO(evaluation));
        }
        return responseDTOs;
    }

    public List<FinalEvaluationResponseDTO> getFinalEvaluationsByCourseId(Long courseId) {
        log.info("Fetching final evaluations for Course ID: {}", courseId);
        List<FinalEvaluation> evaluations = finalEvaluationRepository.findByCourse_Id(courseId);
        List<FinalEvaluationResponseDTO> responseDTOs = new ArrayList<>();
        for (FinalEvaluation evaluation : evaluations) {
            responseDTOs.add(finalEvaluationMapper.toResponseDTO(evaluation));
        }
        return responseDTOs;
    }

    public Optional<FinalEvaluationResponseDTO> getFinalEvaluationByStudentIdAndCourseId(Long studentId, Long courseId) {
        log.info("Fetching final evaluation for Student ID {} and Course ID {}", studentId, courseId);
        Optional<FinalEvaluation> evaluationOptional = finalEvaluationRepository.findByStudent_IdAndCourse_Id(studentId, courseId);
        return evaluationOptional.map(finalEvaluationMapper::toResponseDTO);
    }

    public List<FinalEvaluationResponseDTO> getFinalEvaluationsByFinalGradeGreaterThanEqual(int grade) {
        log.info("Fetching final evaluations with grade greater than or equal to: {}", grade);
        List<FinalEvaluation> evaluations = finalEvaluationRepository.findByFinalGradeGreaterThanEqual(grade);
        List<FinalEvaluationResponseDTO> responseDTOs = new ArrayList<>();
        for (FinalEvaluation evaluation : evaluations) {
            responseDTOs.add(finalEvaluationMapper.toResponseDTO(evaluation));
        }
        return responseDTOs;
    }

    public List<FinalEvaluationResponseDTO> getFinalEvaluationsByFinalGradeLessThanEqual(int grade) {
        log.info("Fetching final evaluations with grade less than or equal to: {}", grade);
        List<FinalEvaluation> evaluations = finalEvaluationRepository.findByFinalGradeLessThanEqual(grade);
        List<FinalEvaluationResponseDTO> responseDTOs = new ArrayList<>();
        for (FinalEvaluation evaluation : evaluations) {
            responseDTOs.add(finalEvaluationMapper.toResponseDTO(evaluation));
        }
        return responseDTOs;
    }
}