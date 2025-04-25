package com.francescogiuliante.esameFinale.service;

import com.francescogiuliante.esameFinale.dto.request.registration.StudentRegistrationDTO;
import com.francescogiuliante.esameFinale.dto.request.registration.TeacherRegistrationDTO;
import com.francescogiuliante.esameFinale.exception.EmailAlreadyExistsException;
import com.francescogiuliante.esameFinale.mapper.StudentRegistrationMapper;
import com.francescogiuliante.esameFinale.mapper.TeacherRegistrationMapper;
import com.francescogiuliante.esameFinale.model.Account;
import com.francescogiuliante.esameFinale.model.Credential;
import com.francescogiuliante.esameFinale.model.Student;
import com.francescogiuliante.esameFinale.model.Teacher;
import com.francescogiuliante.esameFinale.repository.AccountRepository;
import com.francescogiuliante.esameFinale.repository.CredentialRepository;
import com.francescogiuliante.esameFinale.repository.StudentRepository;
import com.francescogiuliante.esameFinale.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RegistrationService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentRegistrationMapper studentRegistrationMapper;

    @Autowired
    private TeacherRegistrationMapper teacherRegistrationMapper;

    @Transactional
    public Student registerStudent(StudentRegistrationDTO registrationDTO) {
        log.info("Registering student: {}", registrationDTO.getEmail());
        if (credentialRepository.existsByEmail(registrationDTO.getEmail())) {
            log.warn("Registration failed: email {} already exists", registrationDTO.getEmail());
            throw new EmailAlreadyExistsException("Email already in use: " + registrationDTO.getEmail());
        }
        Account account = studentRegistrationMapper.toAccount(registrationDTO);
        Account savedAccount = accountRepository.save(account);
        Credential credential = new Credential(registrationDTO.getEmail(), passwordEncoder.encode(registrationDTO.getPassword()), savedAccount);
        credentialRepository.save(credential);
        Student student = studentRegistrationMapper.toStudent(registrationDTO);
        student.setAccount(savedAccount);
        return studentRepository.save(student);
    }

    @Transactional
    public Teacher registerTeacher(TeacherRegistrationDTO registrationDTO) {
        log.info("Registering teacher: {}", registrationDTO.getEmail());
        if (credentialRepository.existsByEmail(registrationDTO.getEmail())) {
            log.warn("Registration failed: email {} already exists", registrationDTO.getEmail());
            throw new EmailAlreadyExistsException("Email already in use: " + registrationDTO.getEmail());
        }
        Account account = teacherRegistrationMapper.toAccount(registrationDTO);
        Account savedAccount = accountRepository.save(account);
        Credential credential = new Credential(registrationDTO.getEmail(), passwordEncoder.encode(registrationDTO.getPassword()), savedAccount);
        credentialRepository.save(credential);
        Teacher teacher = teacherRegistrationMapper.toTeacher(registrationDTO);
        teacher.setAccount(savedAccount);
        return teacherRepository.save(teacher);
    }


}