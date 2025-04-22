package com.francescogiuliante.esameFinale.controller;

import com.francescogiuliante.esameFinale.dto.request.TeacherDTO;
import com.francescogiuliante.esameFinale.dto.response.TeacherResponseDTO;
import com.francescogiuliante.esameFinale.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/teachers")
@Tag(name = "Teacher", description = "Operations related to teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    @Operation(summary = "Get all teachers")
    @ApiResponse(responseCode = "200", description = "List of all teachers")
    public ResponseEntity<List<TeacherResponseDTO>> getAllTeachers() {
        log.info("Received GET request for /api/teachers");
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get teacher by ID")
    @ApiResponse(responseCode = "200", description = "Teacher found")
    @ApiResponse(responseCode = "404", description = "Teacher not found")
    public ResponseEntity<TeacherResponseDTO> getTeacherById(@PathVariable Long id) {
        log.info("Received GET request for /api/teachers/{}", id);
        Optional<TeacherResponseDTO> teacher = teacherService.getTeacherById(id);
        return teacher.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new teacher")
    @ApiResponse(responseCode = "201", description = "Teacher created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<TeacherResponseDTO> createTeacher(@RequestBody TeacherDTO teacherDTO) {
        log.info("Received POST request for /api/teachers with body: {}", teacherDTO);
        TeacherResponseDTO createdTeacher = teacherService.createTeacher(teacherDTO);
        if (createdTeacher != null) {
            return new ResponseEntity<>(createdTeacher, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build(); // O un altro codice di errore appropriato
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing teacher")
    @ApiResponse(responseCode = "200", description = "Teacher updated")
    @ApiResponse(responseCode = "404", description = "Teacher not found")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<TeacherResponseDTO> updateTeacher(@PathVariable Long id, @RequestBody TeacherDTO teacherDTO) {
        log.info("Received PUT request for /api/teachers/{} with body: {}", id, teacherDTO);
        Optional<TeacherResponseDTO> updatedTeacher = teacherService.updateTeacher(id, teacherDTO);
        return updatedTeacher.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a teacher by ID")
    @ApiResponse(responseCode = "204", description = "Teacher deleted")
    @ApiResponse(responseCode = "404", description = "Teacher not found")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        log.warn("Received DELETE request for /api/teachers/{}", id);
        if (teacherService.deleteTeacher(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-specialization/{specialization}")
    @Operation(summary = "Get teachers by specialization")
    @ApiResponse(responseCode = "200", description = "List of teachers with the given specialization")
    public ResponseEntity<List<TeacherResponseDTO>> getTeachersBySpecialization(@PathVariable String specialization) {
        log.info("Received GET request for /api/teachers/by-specialization/{}", specialization);
        return ResponseEntity.ok(teacherService.getTeachersBySpecialization(specialization));
    }

    @GetMapping("/by-experience/greater-than-equal/{years}")
    @Operation(summary = "Get teachers with years of experience greater than or equal to a value")
    @ApiResponse(responseCode = "200", description = "List of teachers with the specified minimum years of experience")
    public ResponseEntity<List<TeacherResponseDTO>> getTeachersByYearsOfExperienceGreaterThanEqual(@PathVariable int years) {
        log.info("Received GET request for /api/teachers/by-experience/greater-than-equal/{}", years);
        return ResponseEntity.ok(teacherService.getTeachersByYearsOfExperienceGreaterThanEqual(years));
    }
}