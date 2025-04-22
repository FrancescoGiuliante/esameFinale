package com.francescogiuliante.esameFinale.service;

import com.francescogiuliante.esameFinale.dto.request.TeacherDTO;
import com.francescogiuliante.esameFinale.dto.response.TeacherResponseDTO;
import com.francescogiuliante.esameFinale.mapper.TeacherMapper;
import com.francescogiuliante.esameFinale.model.Account;
import com.francescogiuliante.esameFinale.model.Teacher;
import com.francescogiuliante.esameFinale.repository.AccountRepository;
import com.francescogiuliante.esameFinale.repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TeacherMapper teacherMapper;

    public List<TeacherResponseDTO> getAllTeachers() {
        log.info("Fetching all teachers.");
        List<Teacher> teachers = teacherRepository.findAll();
        List<TeacherResponseDTO> responseDTOs = new ArrayList<>();
        for (Teacher teacher : teachers) {
            responseDTOs.add(teacherMapper.toResponseDTO(teacher));
        }
        return responseDTOs;
    }

    public Optional<TeacherResponseDTO> getTeacherById(Long id) {
        log.info("Fetching teacher with ID: {}", id);
        Optional<Teacher> teacherOptional = teacherRepository.findById(id);
        return teacherOptional.map(teacherMapper::toResponseDTO);
    }

    public TeacherResponseDTO createTeacher(TeacherDTO teacherDTO) {
        log.info("Creating a new teacher: {}", teacherDTO);
        Teacher teacher = teacherMapper.toEntity(teacherDTO);
        Optional<Account> accountOptional = accountRepository.findById(teacherDTO.getAccountId());
        if (accountOptional.isEmpty()) {
            // Potrebbe essere meglio lanciare un'eccezione qui, a seconda della gestione degli errori
            log.warn("Account with ID {} not found.", teacherDTO.getAccountId());
            return null;
        }
        teacher.setAccount(accountOptional.get());
        Teacher savedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toResponseDTO(savedTeacher);
    }

    public Optional<TeacherResponseDTO> updateTeacher(Long id, TeacherDTO teacherDTO) {
        log.info("Updating teacher with ID {}: {}", id, teacherDTO);
        Optional<Teacher> existingTeacherOptional = teacherRepository.findById(id);
        if (existingTeacherOptional.isPresent()) {
            Teacher existingTeacher = existingTeacherOptional.get();
            Teacher updatedTeacher = teacherMapper.toEntity(teacherDTO);
            updatedTeacher.setId(id);
            updatedTeacher.setHiringDate(existingTeacher.getHiringDate()); // Mantieni la data di assunzione originale

            Optional<Account> accountOptional = accountRepository.findById(teacherDTO.getAccountId());
            if (accountOptional.isEmpty()) {
                log.warn("Account with ID {} not found.", teacherDTO.getAccountId());
                return Optional.empty();
            }
            updatedTeacher.setAccount(accountOptional.get());

            Teacher savedTeacher = teacherRepository.save(updatedTeacher);
            return Optional.of(teacherMapper.toResponseDTO(savedTeacher));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteTeacher(Long id) {
        log.info("Deleting teacher with ID: {}", id);
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<TeacherResponseDTO> getTeachersBySpecialization(String specialization) {
        log.info("Fetching teachers with specialization: {}", specialization);
        List<Teacher> teachers = teacherRepository.findBySpecialization(specialization);
        List<TeacherResponseDTO> responseDTOs = new ArrayList<>();
        for (Teacher teacher : teachers) {
            responseDTOs.add(teacherMapper.toResponseDTO(teacher));
        }
        return responseDTOs;
    }

    public List<TeacherResponseDTO> getTeachersByYearsOfExperienceGreaterThanEqual(int years) {
        log.info("Fetching teachers with years of experience greater than or equal to: {}", years);
        List<Teacher> teachers = teacherRepository.findByYearsOfExperienceGreaterThanEqual(years);
        List<TeacherResponseDTO> responseDTOs = new ArrayList<>();
        for (Teacher teacher : teachers) {
            responseDTOs.add(teacherMapper.toResponseDTO(teacher));
        }
        return responseDTOs;
    }
}