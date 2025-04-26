package com.francescogiuliante.esameFinale.controller;

import com.francescogiuliante.esameFinale.dto.request.LoginDTO;
import com.francescogiuliante.esameFinale.dto.request.registration.StudentRegistrationDTO;
import com.francescogiuliante.esameFinale.dto.request.registration.TeacherRegistrationDTO;
import com.francescogiuliante.esameFinale.dto.response.AuthResponseDTO;
import com.francescogiuliante.esameFinale.service.JwtService;
import com.francescogiuliante.esameFinale.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Operations related to authentication and registration")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    @Operation(summary = "Authenticate user and log in")
    @ApiResponse(responseCode = "200", description = "Login successful")
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        log.info("Received POST request for /api/auth/login with body: {}", loginDTO);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = jwtService.generateToken(userDetails);
            AuthResponseDTO authResponseDTO = new AuthResponseDTO(jwtToken);
            return ResponseEntity.ok(authResponseDTO);
        } catch (AuthenticationException e) {
            log.error("Authentication failed for user: {}", loginDTO.getEmail(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: invalid credentials.");
        }
    }

    @PostMapping("/register/student")
    @Operation(summary = "Register a new student")
    @ApiResponse(responseCode = "201", description = "Student registered successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<Void> registerStudent(@RequestBody StudentRegistrationDTO registrationDTO) {
        log.info("Received POST request for /api/auth/register/student with body: {}", registrationDTO);
        registrationService.registerStudent(registrationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/register/teacher")
    @Operation(summary = "Register a new teacher")
    @ApiResponse(responseCode = "201", description = "Teacher registered successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<Void> registerTeacher(@RequestBody TeacherRegistrationDTO registrationDTO) {
        log.info("Received POST request for /api/auth/register/teacher with body: {}", registrationDTO);
        registrationService.registerTeacher(registrationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
