package com.francescogiuliante.esameFinale.service;

import com.francescogiuliante.esameFinale.dto.request.registration.StudentRegistrationDTO;
import com.francescogiuliante.esameFinale.dto.request.registration.TeacherRegistrationDTO;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final AccountRepository accountRepository;
    private final CredentialRepository credentialRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRegistrationMapper studentRegistrationMapper;
    private final TeacherRegistrationMapper teacherRegistrationMapper;

    public void registerStudent(StudentRegistrationDTO registrationDTO) {
        log.info("Registering student: {}", registrationDTO.getEmail());
        Account account = studentRegistrationMapper.toAccount(registrationDTO);
        Account savedAccount = accountRepository.save(account);
        Credential credential = new Credential(null, registrationDTO.getEmail(), passwordEncoder.encode(registrationDTO.getPassword()), savedAccount);
        credentialRepository.save(credential);
        Student student = studentRegistrationMapper.toStudent(registrationDTO);
        student.setAccount(savedAccount);
        studentRepository.save(student);
        log.info("Student {} registered with ID: {}", registrationDTO.getEmail(), savedAccount.getId());
    }

    public void registerTeacher(TeacherRegistrationDTO registrationDTO) {
        log.info("Registering teacher: {}", registrationDTO.getEmail());
        Account account = teacherRegistrationMapper.toAccount(registrationDTO);
        Account savedAccount = accountRepository.save(account);
        Credential credential = new Credential(null, registrationDTO.getEmail(), passwordEncoder.encode(registrationDTO.getPassword()), savedAccount);
        credentialRepository.save(credential);
        Teacher teacher = teacherRegistrationMapper.toTeacher(registrationDTO);
        teacher.setAccount(savedAccount);
        teacherRepository.save(teacher);
        log.info("Teacher {} registered with ID: {}", registrationDTO.getEmail(), savedAccount.getId());
    }
}