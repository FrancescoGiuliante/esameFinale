package com.francescogiuliante.esameFinale.service;

import com.francescogiuliante.esameFinale.dto.request.EnrollmentDTO;
import com.francescogiuliante.esameFinale.dto.response.EnrollmentResponseDTO;
import com.francescogiuliante.esameFinale.enums.EnrollmentStatus;
import com.francescogiuliante.esameFinale.mapper.EnrollmentMapper;
import com.francescogiuliante.esameFinale.model.Course;
import com.francescogiuliante.esameFinale.model.Enrollment;
import com.francescogiuliante.esameFinale.model.Student;
import com.francescogiuliante.esameFinale.repository.CourseRepository;
import com.francescogiuliante.esameFinale.repository.EnrollmentRepository;
import com.francescogiuliante.esameFinale.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentMapper enrollmentMapper;

    public List<EnrollmentResponseDTO> getAllEnrollments() {
        log.info("Fetching all enrollments.");
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        List<EnrollmentResponseDTO> responseDTOs = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            responseDTOs.add(enrollmentMapper.toResponseDTO(enrollment));
        }
        return responseDTOs;
    }

    public Optional<EnrollmentResponseDTO> getEnrollmentById(Long id) {
        log.info("Fetching enrollment with ID: {}", id);
        Optional<Enrollment> enrollmentOptional = enrollmentRepository.findById(id);
        return enrollmentOptional.map(enrollmentMapper::toResponseDTO);
    }

    public EnrollmentResponseDTO createEnrollment(EnrollmentDTO enrollmentDTO) {
        log.info("Creating a new enrollment: {}", enrollmentDTO);
        Enrollment enrollment = enrollmentMapper.toEntity(enrollmentDTO);

        Optional<Student> studentOptional = studentRepository.findById(enrollmentDTO.getStudentId());
        if (studentOptional.isEmpty()) {
            log.warn("Student with ID {} not found.", enrollmentDTO.getStudentId());
            return null;
        }
        enrollment.setStudent(studentOptional.get());

        Optional<Course> courseOptional = courseRepository.findById(enrollmentDTO.getCourseId());
        if (courseOptional.isEmpty()) {
            log.warn("Course with ID {} not found.", enrollmentDTO.getCourseId());
            return null;
        }
        enrollment.setCourse(courseOptional.get());

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toResponseDTO(savedEnrollment);
    }

    public Optional<EnrollmentResponseDTO> updateEnrollment(Long id, EnrollmentDTO enrollmentDTO) {
        log.info("Updating enrollment with ID {}: {}", id, enrollmentDTO);
        Optional<Enrollment> existingEnrollmentOptional = enrollmentRepository.findById(id);
        if (existingEnrollmentOptional.isPresent()) {
            Enrollment updatedEnrollment = enrollmentMapper.toEntity(enrollmentDTO);
            updatedEnrollment.setId(id);

            Optional<Student> studentOptional = studentRepository.findById(enrollmentDTO.getStudentId());
            if (studentOptional.isEmpty()) {
                log.warn("Student with ID {} not found.", enrollmentDTO.getStudentId());
                return Optional.empty();
            }
            updatedEnrollment.setStudent(studentOptional.get());

            Optional<Course> courseOptional = courseRepository.findById(enrollmentDTO.getCourseId());
            if (courseOptional.isEmpty()) {
                log.warn("Course with ID {} not found.", enrollmentDTO.getCourseId());
                return Optional.empty();
            }
            updatedEnrollment.setCourse(courseOptional.get());

            Enrollment savedEnrollment = enrollmentRepository.save(updatedEnrollment);
            return Optional.of(enrollmentMapper.toResponseDTO(savedEnrollment));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteEnrollment(Long id) {
        log.info("Deleting enrollment with ID: {}", id);
        if (enrollmentRepository.existsById(id)) {
            enrollmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<EnrollmentResponseDTO> getEnrollmentsByStudentId(Long studentId) {
        log.info("Fetching enrollments for Student ID: {}", studentId);
        List<Enrollment> enrollments = enrollmentRepository.findByStudent_Id(studentId);
        List<EnrollmentResponseDTO> responseDTOs = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            responseDTOs.add(enrollmentMapper.toResponseDTO(enrollment));
        }
        return responseDTOs;
    }

    public List<EnrollmentResponseDTO> getEnrollmentsByCourseId(Long courseId) {
        log.info("Fetching enrollments for Course ID: {}", courseId);
        List<Enrollment> enrollments = enrollmentRepository.findByCourse_Id(courseId);
        List<EnrollmentResponseDTO> responseDTOs = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            responseDTOs.add(enrollmentMapper.toResponseDTO(enrollment));
        }
        return responseDTOs;
    }

    public List<EnrollmentResponseDTO> getEnrollmentsByEnrollmentDate(LocalDate enrollmentDate) {
        log.info("Fetching enrollments by enrollment date: {}", enrollmentDate);
        List<Enrollment> enrollments = enrollmentRepository.findByEnrollmentDate(enrollmentDate);
        List<EnrollmentResponseDTO> responseDTOs = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            responseDTOs.add(enrollmentMapper.toResponseDTO(enrollment));
        }
        return responseDTOs;
    }

    public List<EnrollmentResponseDTO> getEnrollmentsByEnrollmentStatus(EnrollmentStatus enrollmentStatus) {
        log.info("Fetching enrollments by status: {}", enrollmentStatus);
        List<Enrollment> enrollments = enrollmentRepository.findByEnrollmentStatus(enrollmentStatus);
        List<EnrollmentResponseDTO> responseDTOs = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            responseDTOs.add(enrollmentMapper.toResponseDTO(enrollment));
        }
        return responseDTOs;
    }

    public Optional<EnrollmentResponseDTO> getEnrollmentByStudentIdAndCourseId(Long studentId, Long courseId) {
        log.info("Fetching enrollment for Student ID {} and Course ID {}", studentId, courseId);
        Optional<Enrollment> enrollmentOptional = enrollmentRepository.findByStudent_IdAndCourse_Id(studentId, courseId);
        return enrollmentOptional.map(enrollmentMapper::toResponseDTO);
    }
}