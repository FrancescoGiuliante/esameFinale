package com.francescogiuliante.esameFinale.service;

import com.francescogiuliante.esameFinale.dto.request.CourseExamDTO;
import com.francescogiuliante.esameFinale.dto.response.CourseExamResponseDTO;
import com.francescogiuliante.esameFinale.mapper.CourseExamMapper;
import com.francescogiuliante.esameFinale.model.Course;
import com.francescogiuliante.esameFinale.model.CourseExam;
import com.francescogiuliante.esameFinale.model.Exam;
import com.francescogiuliante.esameFinale.repository.CourseExamRepository;
import com.francescogiuliante.esameFinale.repository.CourseRepository;
import com.francescogiuliante.esameFinale.repository.ExamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CourseExamService {

    @Autowired
    private CourseExamRepository courseExamRepository;

    @Autowired
    private CourseExamMapper courseExamMapper;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ExamRepository examRepository;

    public List<CourseExamResponseDTO> getAllCourseExams() {
        log.info("Fetching all course exams.");
        List<CourseExam> courseExams = courseExamRepository.findAll();
        List<CourseExamResponseDTO> responseDTOs = new ArrayList<>();
        for (CourseExam courseExam : courseExams) {
            responseDTOs.add(courseExamMapper.toResponseDTO(courseExam));
        }
        return responseDTOs;
    }

    public Optional<CourseExamResponseDTO> getCourseExamById(Long id) {
        log.info("Fetching course exam with ID: {}", id);
        Optional<CourseExam> courseExamOptional = courseExamRepository.findById(id);
        if (courseExamOptional.isPresent()) {
            return Optional.of(courseExamMapper.toResponseDTO(courseExamOptional.get()));
        } else {
            return Optional.empty();
        }
    }

    public CourseExamResponseDTO createCourseExam(CourseExamDTO courseExamDTO) {
        log.info("Creating a new course exam: {}", courseExamDTO);
        CourseExam courseExam = courseExamMapper.toEntity(courseExamDTO);
        Optional<Course> courseOptional = courseRepository.findById(courseExamDTO.getCourseId());
        Optional<Exam> examOptional = examRepository.findById(courseExamDTO.getExamId());
        if (courseOptional.isPresent() && examOptional.isPresent()) {
            courseExam.setCourse(courseOptional.get());
            courseExam.setExam(examOptional.get());
            CourseExam savedCourseExam = courseExamRepository.save(courseExam);
            return courseExamMapper.toResponseDTO(savedCourseExam);
        } else {
            log.warn("Could not create course exam. Course or Exam not found.");
            return null;
        }
    }

    public Optional<CourseExamResponseDTO> updateCourseExam(Long id, CourseExamDTO courseExamDTO) {
        log.info("Updating course exam with ID {}: {}", id, courseExamDTO);
        Optional<CourseExam> existingCourseExamOptional = courseExamRepository.findById(id);
        if (existingCourseExamOptional.isPresent()) {
            CourseExam existingCourseExam = existingCourseExamOptional.get();
            CourseExam updatedCourseExam = courseExamMapper.toEntity(courseExamDTO);
            Optional<Course> courseOptional = courseRepository.findById(courseExamDTO.getCourseId());
            Optional<Exam> examOptional = examRepository.findById(courseExamDTO.getExamId());
            if (courseOptional.isPresent() && examOptional.isPresent()) {
                updatedCourseExam.setId(id);
                updatedCourseExam.setCourse(courseOptional.get());
                updatedCourseExam.setExam(examOptional.get());
                CourseExam savedCourseExam = courseExamRepository.save(updatedCourseExam);
                return Optional.of(courseExamMapper.toResponseDTO(savedCourseExam));
            } else {
                log.warn("Could not update course exam with ID {}. Course or Exam not found.", id);
                return Optional.empty();
            }
        } else {
            log.warn("Course exam with ID {} not found for update.", id);
            return Optional.empty();
        }
    }

    public boolean deleteCourseExam(Long id) {
        log.info("Deleting course exam with ID: {}", id);
        if (courseExamRepository.existsById(id)) {
            courseExamRepository.deleteById(id);
            return true;
        } else {
            log.warn("Course exam with ID {} not found for deletion.", id);
            return false;
        }
    }

    public List<CourseExamResponseDTO> getCourseExamsByCourseId(Long courseId) {
        log.info("Fetching course exams by course ID: {}", courseId);
        List<CourseExam> courseExams = courseExamRepository.findByCourse_Id(courseId);
        List<CourseExamResponseDTO> responseDTOs = new ArrayList<>();
        for (CourseExam courseExam : courseExams) {
            responseDTOs.add(courseExamMapper.toResponseDTO(courseExam));
        }
        return responseDTOs;
    }

    public List<CourseExamResponseDTO> getCourseExamsByExamId(Long examId) {
        log.info("Fetching course exams by exam ID: {}", examId);
        List<CourseExam> courseExams = courseExamRepository.findByExam_Id(examId);
        List<CourseExamResponseDTO> responseDTOs = new ArrayList<>();
        for (CourseExam courseExam : courseExams) {
            responseDTOs.add(courseExamMapper.toResponseDTO(courseExam));
        }
        return responseDTOs;
    }

    public List<CourseExamResponseDTO> getCourseExamsByExamDate(LocalDate examDate) {
        log.info("Fetching course exams by exam date: {}", examDate);
        List<CourseExam> courseExams = courseExamRepository.findByExamDate(examDate);
        List<CourseExamResponseDTO> responseDTOs = new ArrayList<>();
        for (CourseExam courseExam : courseExams) {
            responseDTOs.add(courseExamMapper.toResponseDTO(courseExam));
        }
        return responseDTOs;
    }

    public List<CourseExamResponseDTO> getCourseExamsByExamDateGreaterThan(LocalDate examDate) {
        log.info("Fetching course exams after exam date: {}", examDate);
        List<CourseExam> courseExams = courseExamRepository.findByExamDateGreaterThan(examDate);
        List<CourseExamResponseDTO> responseDTOs = new ArrayList<>();
        for (CourseExam courseExam : courseExams) {
            responseDTOs.add(courseExamMapper.toResponseDTO(courseExam));
        }
        return responseDTOs;
    }

    public List<CourseExamResponseDTO> getCourseExamsByAcademicYear(String academicYear) {
        log.info("Fetching course exams by academic year: {}", academicYear);
        List<CourseExam> courseExams = courseExamRepository.findByCourse_AcademicYear(academicYear);
        List<CourseExamResponseDTO> responseDTOs = new ArrayList<>();
        for (CourseExam courseExam : courseExams) {
            responseDTOs.add(courseExamMapper.toResponseDTO(courseExam));
        }
        return responseDTOs;
    }
}