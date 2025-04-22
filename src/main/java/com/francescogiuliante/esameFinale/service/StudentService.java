package com.francescogiuliante.esameFinale.service;

import com.francescogiuliante.esameFinale.dto.request.StudentDTO;
import com.francescogiuliante.esameFinale.dto.response.StudentResponseDTO;
import com.francescogiuliante.esameFinale.mapper.StudentMapper;
import com.francescogiuliante.esameFinale.model.Account;
import com.francescogiuliante.esameFinale.model.Student;
import com.francescogiuliante.esameFinale.repository.AccountRepository;
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
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private AccountRepository accountRepository;

    public List<StudentResponseDTO> getAllStudents() {
        log.info("Fetching all students.");
        List<Student> students = studentRepository.findAll();
        List<StudentResponseDTO> responseDTOs = new ArrayList<>();
        for (Student student : students) {
            responseDTOs.add(studentMapper.toResponseDTO(student));
        }
        return responseDTOs;
    }

    public Optional<StudentResponseDTO> getStudentById(Long id) {
        log.info("Fetching student with ID: {}", id);
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            return Optional.of(studentMapper.toResponseDTO(studentOptional.get()));
        } else {
            return Optional.empty();
        }
    }

    public StudentResponseDTO createStudent(StudentDTO studentDTO) {
        log.info("Creating a new student: {}", studentDTO);
        Student student = studentMapper.toEntity(studentDTO);
        Optional<Account> accountOptional = accountRepository.findById(studentDTO.getAccountId());
        if (accountOptional.isPresent()) {
            student.setAccount(accountOptional.get());
            Student savedStudent = studentRepository.save(student);
            return studentMapper.toResponseDTO(savedStudent);
        } else {
            log.warn("Could not create student. Account with ID {} not found.", studentDTO.getAccountId());
            return null;
        }
    }

    public Optional<StudentResponseDTO> updateStudent(Long id, StudentDTO studentDTO) {
        log.info("Updating student with ID {}: {}", id, studentDTO);
        Optional<Student> existingStudentOptional = studentRepository.findById(id);
        if (existingStudentOptional.isPresent()) {
            Student existingStudent = existingStudentOptional.get();
            Student updatedStudent = studentMapper.toEntity(studentDTO);
            Optional<Account> accountOptional = accountRepository.findById(studentDTO.getAccountId());
            if (accountOptional.isPresent()) {
                updatedStudent.setId(id);
                updatedStudent.setAccount(accountOptional.get());
                existingStudent.setBirthDate(updatedStudent.getBirthDate());
                existingStudent.setBirthPlace(updatedStudent.getBirthPlace());
                existingStudent.setEnrollmentYear(updatedStudent.getEnrollmentYear());
                Student savedStudent = studentRepository.save(existingStudent);
                return Optional.of(studentMapper.toResponseDTO(savedStudent));
            } else {
                log.warn("Could not update student with ID {}. Account with ID {} not found.", id, studentDTO.getAccountId());
                return Optional.empty();
            }
        } else {
            log.warn("Student with ID {} not found for update.", id);
            return Optional.empty();
        }
    }

    public boolean deleteStudent(Long id) {
        log.info("Deleting student with ID: {}", id);
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        } else {
            log.warn("Student with ID {} not found for deletion.", id);
            return false;
        }
    }

    public List<StudentResponseDTO> getStudentsByBirthDate(LocalDate birthDate) {
        log.info("Fetching students by birth date: {}", birthDate);
        List<Student> students = studentRepository.findByBirthDate(birthDate);
        List<StudentResponseDTO> responseDTOs = new ArrayList<>();
        for (Student student : students) {
            responseDTOs.add(studentMapper.toResponseDTO(student));
        }
        return responseDTOs;
    }

    public List<StudentResponseDTO> getStudentsBornBefore(LocalDate birthDate) {
        log.info("Fetching students born before: {}", birthDate);
        List<Student> students = studentRepository.findByBirthDateBefore(birthDate);
        List<StudentResponseDTO> responseDTOs = new ArrayList<>();
        for (Student student : students) {
            responseDTOs.add(studentMapper.toResponseDTO(student));
        }
        return responseDTOs;
    }

    public List<StudentResponseDTO> getStudentsBornAfter(LocalDate birthDate) {
        log.info("Fetching students born after: {}", birthDate);
        List<Student> students = studentRepository.findByBirthDateAfter(birthDate);
        List<StudentResponseDTO> responseDTOs = new ArrayList<>();
        for (Student student : students) {
            responseDTOs.add(studentMapper.toResponseDTO(student));
        }
        return responseDTOs;
    }

    public List<StudentResponseDTO> getStudentsByBirthPlace(String birthPlace) {
        log.info("Fetching students by birth place: {}", birthPlace);
        List<Student> students = studentRepository.findByBirthPlace(birthPlace);
        List<StudentResponseDTO> responseDTOs = new ArrayList<>();
        for (Student student : students) {
            responseDTOs.add(studentMapper.toResponseDTO(student));
        }
        return responseDTOs;
    }

    public List<StudentResponseDTO> getStudentsByEnrollmentYear(int enrollmentYear) {
        log.info("Fetching students by enrollment year: {}", enrollmentYear);
        List<Student> students = studentRepository.findByEnrollmentYear(enrollmentYear);
        List<StudentResponseDTO> responseDTOs = new ArrayList<>();
        for (Student student : students) {
            responseDTOs.add(studentMapper.toResponseDTO(student));
        }
        return responseDTOs;
    }

    public List<StudentResponseDTO> getStudentsEnrolledAfterOrIn(int enrollmentYear) {
        log.info("Fetching students enrolled after or in: {}", enrollmentYear);
        List<Student> students = studentRepository.findByEnrollmentYearGreaterThanEqual(enrollmentYear);
        List<StudentResponseDTO> responseDTOs = new ArrayList<>();
        for (Student student : students) {
            responseDTOs.add(studentMapper.toResponseDTO(student));
        }
        return responseDTOs;
    }

    public List<StudentResponseDTO> getStudentsEnrolledBeforeOrIn(int enrollmentYear) {
        log.info("Fetching students enrolled before or in: {}", enrollmentYear);
        List<Student> students = studentRepository.findByEnrollmentYearLessThanEqual(enrollmentYear);
        List<StudentResponseDTO> responseDTOs = new ArrayList<>();
        for (Student student : students) {
            responseDTOs.add(studentMapper.toResponseDTO(student));
        }
        return responseDTOs;
    }
}