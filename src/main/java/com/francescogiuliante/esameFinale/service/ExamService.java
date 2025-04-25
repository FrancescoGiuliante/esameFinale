package com.francescogiuliante.esameFinale.service;

import com.francescogiuliante.esameFinale.dto.request.ExamDTO;
import com.francescogiuliante.esameFinale.dto.response.ExamResponseDTO;
import com.francescogiuliante.esameFinale.mapper.ExamMapper;
import com.francescogiuliante.esameFinale.model.Exam;
import com.francescogiuliante.esameFinale.repository.ExamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ExamMapper examMapper;

    public List<ExamResponseDTO> getAllExams() {
        log.info("Fetching all exams.");
        List<Exam> exams = examRepository.findAll();
        List<ExamResponseDTO> responseDTOs = new ArrayList<>();
        for (Exam exam : exams) {
            responseDTOs.add(examMapper.toResponseDTO(exam));
        }
        return responseDTOs;
    }

    public Optional<ExamResponseDTO> getExamById(Long id) {
        log.info("Fetching exam with ID: {}", id);
        Optional<Exam> examOptional = examRepository.findById(id);
        if (examOptional.isPresent()) {
            return Optional.of(examMapper.toResponseDTO(examOptional.get()));
        } else {
            return Optional.empty();
        }
    }

    public Long createExam(ExamDTO examDTO) {
        log.info("Creating a new exam: {}", examDTO);
        Exam exam = examMapper.toEntity(examDTO);
        Exam savedExam = examRepository.save(exam);
        return savedExam.getId();
    }

    public Optional<ExamResponseDTO> updateExam(Long id, ExamDTO examDTO) {
        log.info("Updating exam with ID {}: {}", id, examDTO);
        Optional<Exam> existingExamOptional = examRepository.findById(id);
        if (existingExamOptional.isPresent()) {
            Exam existingExam = existingExamOptional.get();
            Exam updatedExam = examMapper.toEntity(examDTO);
            updatedExam.setId(id);
            existingExam.setName(updatedExam.getName());
            existingExam.setType(updatedExam.getType());
            existingExam.setDuration(updatedExam.getDuration());
            existingExam.setCredits(updatedExam.getCredits());
            Exam savedExam = examRepository.save(existingExam);
            return Optional.of(examMapper.toResponseDTO(savedExam));
        } else {
            log.warn("Exam with ID {} not found for update.", id);
            return Optional.empty();
        }
    }

    public boolean deleteExam(Long id) {
        log.info("Deleting exam with ID: {}", id);
        if (examRepository.existsById(id)) {
            examRepository.deleteById(id);
            return true;
        } else {
            log.warn("Exam with ID {} not found for deletion.", id);
            return false;
        }
    }

    public List<ExamResponseDTO> getExamsByType(String type) {
        log.info("Fetching exams by type: {}", type);
        List<Exam> exams = examRepository.findByType(type);
        List<ExamResponseDTO> responseDTOs = new ArrayList<>();
        for (Exam exam : exams) {
            responseDTOs.add(examMapper.toResponseDTO(exam));
        }
        return responseDTOs;
    }

    public List<ExamResponseDTO> getExamsByCredits(int credits) {
        log.info("Fetching exams by credits: {}", credits);
        List<Exam> exams = examRepository.findByCredits(credits);
        List<ExamResponseDTO> responseDTOs = new ArrayList<>();
        for (Exam exam : exams) {
            responseDTOs.add(examMapper.toResponseDTO(exam));
        }
        return responseDTOs;
    }

    public List<ExamResponseDTO> getExamsByDurationGreaterThan(int duration) {
        log.info("Fetching exams with duration greater than: {}", duration);
        List<Exam> exams = examRepository.findByDurationGreaterThan(duration);
        List<ExamResponseDTO> responseDTOs = new ArrayList<>();
        for (Exam exam : exams) {
            responseDTOs.add(examMapper.toResponseDTO(exam));
        }
        return responseDTOs;
    }
}