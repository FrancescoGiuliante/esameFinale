package com.francescogiuliante.esameFinale.service;

import com.francescogiuliante.esameFinale.dto.request.StudentExamDTO;
import com.francescogiuliante.esameFinale.dto.response.StudentExamResponseDTO;
import com.francescogiuliante.esameFinale.mapper.StudentExamMapper;
import com.francescogiuliante.esameFinale.model.Exam;
import com.francescogiuliante.esameFinale.model.Student;
import com.francescogiuliante.esameFinale.model.StudentExam;
import com.francescogiuliante.esameFinale.repository.ExamRepository;
import com.francescogiuliante.esameFinale.repository.StudentExamRepository;
import com.francescogiuliante.esameFinale.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StudentExamService {

    @Autowired
    private StudentExamRepository studentExamRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private StudentExamMapper studentExamMapper;

    public List<StudentExamResponseDTO> getAllStudentExams() {
        log.info("Fetching all student exams.");
        List<StudentExam> studentExams = studentExamRepository.findAll();
        List<StudentExamResponseDTO> responseDTOs = new ArrayList<>();
        for (StudentExam studentExam : studentExams) {
            responseDTOs.add(studentExamMapper.toResponseDTO(studentExam));
        }
        return responseDTOs;
    }

    public Optional<StudentExamResponseDTO> getStudentExamById(Long id) {
        log.info("Fetching student exam with ID: {}", id);
        Optional<StudentExam> studentExamOptional = studentExamRepository.findById(id);
        return studentExamOptional.map(studentExamMapper::toResponseDTO);
    }

    public StudentExamResponseDTO createStudentExam(StudentExamDTO studentExamDTO) {
        log.info("Creating a new student exam: {}", studentExamDTO);
        StudentExam studentExam = studentExamMapper.toEntity(studentExamDTO);

        Optional<Student> studentOptional = studentRepository.findById(studentExamDTO.getStudentId());
        if (studentOptional.isEmpty()) {
            log.warn("Student with ID {} not found.", studentExamDTO.getStudentId());
            return null;
        }
        studentExam.setStudent(studentOptional.get());

        Optional<Exam> examOptional = examRepository.findById(studentExamDTO.getExamId());
        if (examOptional.isEmpty()) {
            log.warn("Exam with ID {} not found.", studentExamDTO.getExamId());
            return null;
        }
        studentExam.setExam(examOptional.get());

        StudentExam savedStudentExam = studentExamRepository.save(studentExam);
        return studentExamMapper.toResponseDTO(savedStudentExam);
    }

    public Optional<StudentExamResponseDTO> updateStudentExam(Long id, StudentExamDTO studentExamDTO) {
        log.info("Updating student exam with ID {}: {}", id, studentExamDTO);
        Optional<StudentExam> existingStudentExamOptional = studentExamRepository.findById(id);
        if (existingStudentExamOptional.isPresent()) {
            StudentExam updatedStudentExam = studentExamMapper.toEntity(studentExamDTO);
            updatedStudentExam.setId(id);

            Optional<Student> studentOptional = studentRepository.findById(studentExamDTO.getStudentId());
            if (studentOptional.isEmpty()) {
                log.warn("Student with ID {} not found.", studentExamDTO.getStudentId());
                return Optional.empty();
            }
            updatedStudentExam.setStudent(studentOptional.get());

            Optional<Exam> examOptional = examRepository.findById(studentExamDTO.getExamId());
            if (examOptional.isEmpty()) {
                log.warn("Exam with ID {} not found.", studentExamDTO.getExamId());
                return Optional.empty();
            }
            updatedStudentExam.setExam(examOptional.get());

            StudentExam savedStudentExam = studentExamRepository.save(updatedStudentExam);
            return Optional.of(studentExamMapper.toResponseDTO(savedStudentExam));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteStudentExam(Long id) {
        log.info("Deleting student exam with ID: {}", id);
        if (studentExamRepository.existsById(id)) {
            studentExamRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<StudentExamResponseDTO> getStudentExamsByStudentId(Long studentId) {
        log.info("Fetching student exams for Student ID: {}", studentId);
        List<StudentExam> studentExams = studentExamRepository.findByStudent_Id(studentId);
        List<StudentExamResponseDTO> responseDTOs = new ArrayList<>();
        for (StudentExam studentExam : studentExams) {
            responseDTOs.add(studentExamMapper.toResponseDTO(studentExam));
        }
        return responseDTOs;
    }

    public List<StudentExamResponseDTO> getStudentExamsByExamId(Long examId) {
        log.info("Fetching student exams for Exam ID: {}", examId);
        List<StudentExam> studentExams = studentExamRepository.findByExam_Id(examId);
        List<StudentExamResponseDTO> responseDTOs = new ArrayList<>();
        for (StudentExam studentExam : studentExams) {
            responseDTOs.add(studentExamMapper.toResponseDTO(studentExam));
        }
        return responseDTOs;
    }

    public Optional<StudentExamResponseDTO> getStudentExamByStudentIdAndExamId(Long studentId, Long examId) {
        log.info("Fetching student exam for Student ID {} and Exam ID {}", studentId, examId);
        Optional<StudentExam> studentExamOptional = studentExamRepository.findByStudent_IdAndExam_Id(studentId, examId);
        return studentExamOptional.map(studentExamMapper::toResponseDTO);
    }

    public List<StudentExamResponseDTO> getStudentExamsByGrade(int grade) {
        log.info("Fetching student exams with grade: {}", grade);
        List<StudentExam> studentExams = studentExamRepository.findByGrade(grade);
        List<StudentExamResponseDTO> responseDTOs = new ArrayList<>();
        for (StudentExam studentExam : studentExams) {
            responseDTOs.add(studentExamMapper.toResponseDTO(studentExam));
        }
        return responseDTOs;
    }

    public List<StudentExamResponseDTO> getStudentExamsByGradeGreaterThanEqual(int grade) {
        log.info("Fetching student exams with grade greater than or equal to: {}", grade);
        List<StudentExam> studentExams = studentExamRepository.findByGradeGreaterThanEqual(grade);
        List<StudentExamResponseDTO> responseDTOs = new ArrayList<>();
        for (StudentExam studentExam : studentExams) {
            responseDTOs.add(studentExamMapper.toResponseDTO(studentExam));
        }
        return responseDTOs;
    }

    public List<StudentExamResponseDTO> getStudentExamsByGradeLessThanEqual(int grade) {
        log.info("Fetching student exams with grade less than or equal to: {}", grade);
        List<StudentExam> studentExams = studentExamRepository.findByGradeLessThanEqual(grade);
        List<StudentExamResponseDTO> responseDTOs = new ArrayList<>();
        for (StudentExam studentExam : studentExams) {
            responseDTOs.add(studentExamMapper.toResponseDTO(studentExam));
        }
        return responseDTOs;
    }
}