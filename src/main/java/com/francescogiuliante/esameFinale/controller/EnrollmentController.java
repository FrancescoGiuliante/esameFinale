package com.francescogiuliante.esameFinale.controller;

import com.francescogiuliante.esameFinale.dto.request.EnrollmentDTO;
import com.francescogiuliante.esameFinale.dto.response.EnrollmentResponseDTO;
import com.francescogiuliante.esameFinale.enums.EnrollmentStatus;
import com.francescogiuliante.esameFinale.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/enrollments")
@Tag(name = "Enrollment", description = "Operations related to student enrollments in courses")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping
    @Operation(summary = "Get all enrollments")
    @ApiResponse(responseCode = "200", description = "List of all enrollments")
    public ResponseEntity<List<EnrollmentResponseDTO>> getAllEnrollments() {
        log.info("Received GET request for /api/enrollments");
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get enrollment by ID")
    @ApiResponse(responseCode = "200", description = "Enrollment found")
    @ApiResponse(responseCode = "404", description = "Enrollment not found")
    public ResponseEntity<EnrollmentResponseDTO> getEnrollmentById(@PathVariable Long id) {
        log.info("Received GET request for /api/enrollments/{}", id);
        Optional<EnrollmentResponseDTO> enrollment = enrollmentService.getEnrollmentById(id);
        return enrollment.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new enrollment")
    @ApiResponse(responseCode = "201", description = "Enrollment created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<EnrollmentResponseDTO> createEnrollment(@RequestBody EnrollmentDTO enrollmentDTO) {
        log.info("Received POST request for /api/enrollments with body: {}", enrollmentDTO);
        EnrollmentResponseDTO createdEnrollment = enrollmentService.createEnrollment(enrollmentDTO);
        if (createdEnrollment != null) {
            return new ResponseEntity<>(createdEnrollment, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing enrollment")
    @ApiResponse(responseCode = "200", description = "Enrollment updated")
    @ApiResponse(responseCode = "404", description = "Enrollment not found")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<EnrollmentResponseDTO> updateEnrollment(@PathVariable Long id, @RequestBody EnrollmentDTO enrollmentDTO) {
        log.info("Received PUT request for /api/enrollments/{} with body: {}", id, enrollmentDTO);
        Optional<EnrollmentResponseDTO> updatedEnrollment = enrollmentService.updateEnrollment(id, enrollmentDTO);
        return updatedEnrollment.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an enrollment by ID")
    @ApiResponse(responseCode = "204", description = "Enrollment deleted")
    @ApiResponse(responseCode = "404", description = "Enrollment not found")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        log.warn("Received DELETE request for /api/enrollments/{}", id);
        if (enrollmentService.deleteEnrollment(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-student/{studentId}")
    @Operation(summary = "Get enrollments by student ID")
    @ApiResponse(responseCode = "200", description = "List of enrollments for the given student ID")
    public ResponseEntity<List<EnrollmentResponseDTO>> getEnrollmentsByStudentId(@PathVariable Long studentId) {
        log.info("Received GET request for /api/enrollments/by-student/{}", studentId);
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByStudentId(studentId));
    }

    @GetMapping("/by-course/{courseId}")
    @Operation(summary = "Get enrollments by course ID")
    @ApiResponse(responseCode = "200", description = "List of enrollments for the given course ID")
    public ResponseEntity<List<EnrollmentResponseDTO>> getEnrollmentsByCourseId(@PathVariable Long courseId) {
        log.info("Received GET request for /api/enrollments/by-course/{}", courseId);
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByCourseId(courseId));
    }

    @GetMapping("/by-enrollment-date/{enrollmentDate}")
    @Operation(summary = "Get enrollments by enrollment date")
    @ApiResponse(responseCode = "200", description = "List of enrollments for the given enrollment date")
    public ResponseEntity<List<EnrollmentResponseDTO>> getEnrollmentsByEnrollmentDate(@PathVariable LocalDate enrollmentDate) {
        log.info("Received GET request for /api/enrollments/by-enrollment-date/{}", enrollmentDate);
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByEnrollmentDate(enrollmentDate));
    }

    @GetMapping("/by-status/{enrollmentStatus}")
    @Operation(summary = "Get enrollments by enrollment status")
    @ApiResponse(responseCode = "200", description = "List of enrollments for the given status")
    public ResponseEntity<List<EnrollmentResponseDTO>> getEnrollmentsByEnrollmentStatus(@PathVariable EnrollmentStatus enrollmentStatus) {
        log.info("Received GET request for /api/enrollments/by-status/{}", enrollmentStatus);
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByEnrollmentStatus(enrollmentStatus));
    }

    @GetMapping("/by-student/{studentId}/and-course/{courseId}")
    @Operation(summary = "Get enrollment by student ID and course ID")
    @ApiResponse(responseCode = "200", description = "Enrollment found for the given student and course")
    @ApiResponse(responseCode = "404", description = "Enrollment not found for the given student and course")
    public ResponseEntity<EnrollmentResponseDTO> getEnrollmentByStudentIdAndCourseId(@PathVariable Long studentId, @PathVariable Long courseId) {
        log.info("Received GET request for /api/enrollments/by-student/{}/and-course/{}", studentId, courseId);
        Optional<EnrollmentResponseDTO> enrollment = enrollmentService.getEnrollmentByStudentIdAndCourseId(studentId, courseId);
        return enrollment.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}