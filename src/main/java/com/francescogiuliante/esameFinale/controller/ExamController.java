package com.francescogiuliante.esameFinale.controller;

import com.francescogiuliante.esameFinale.dto.request.ExamDTO;
import com.francescogiuliante.esameFinale.dto.response.ExamResponseDTO;
import com.francescogiuliante.esameFinale.service.ExamService;
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
@RequestMapping("/api/exams")
@Tag(name = "Exam", description = "Operations related to exams")
public class ExamController {

    @Autowired
    private ExamService examService;

    @GetMapping
    @Operation(summary = "Get all exams")
    @ApiResponse(responseCode = "200", description = "List of all exams")
    public ResponseEntity<List<ExamResponseDTO>> getAllExams() {
        log.info("Received GET request for /api/exams");
        return ResponseEntity.ok(examService.getAllExams());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get exam by ID")
    @ApiResponse(responseCode = "200", description = "Exam found")
    @ApiResponse(responseCode = "404", description = "Exam not found")
    public ResponseEntity<ExamResponseDTO> getExamById(@PathVariable Long id) {
        log.info("Received GET request for /api/exams/{}", id);
        Optional<ExamResponseDTO> exam = examService.getExamById(id);
        return exam.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new exam")
    @ApiResponse(responseCode = "201", description = "Exam created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<ExamResponseDTO> createExam(@RequestBody ExamDTO examDTO) {
        log.info("Received POST request for /api/exams with body: {}", examDTO);
        ExamResponseDTO createdExam = examService.createExam(examDTO);
        return new ResponseEntity<>(createdExam, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing exam")
    @ApiResponse(responseCode = "200", description = "Exam updated")
    @ApiResponse(responseCode = "404", description = "Exam not found")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<ExamResponseDTO> updateExam(@PathVariable Long id, @RequestBody ExamDTO examDTO) {
        log.info("Received PUT request for /api/exams/{} with body: {}", id, examDTO);
        Optional<ExamResponseDTO> updatedExam = examService.updateExam(id, examDTO);
        return updatedExam.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an exam by ID")
    @ApiResponse(responseCode = "204", description = "Exam deleted")
    @ApiResponse(responseCode = "404", description = "Exam not found")
    public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
        log.warn("Received DELETE request for /api/exams/{}", id);
        if (examService.deleteExam(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-type/{type}")
    @Operation(summary = "Get exams by type")
    @ApiResponse(responseCode = "200", description = "List of exams of the given type")
    public ResponseEntity<List<ExamResponseDTO>> getExamsByType(@PathVariable String type) {
        log.info("Received GET request for /api/exams/by-type/{}", type);
        return ResponseEntity.ok(examService.getExamsByType(type));
    }

    @GetMapping("/by-credits/{credits}")
    @Operation(summary = "Get exams by credits")
    @ApiResponse(responseCode = "200", description = "List of exams with the given credits")
    public ResponseEntity<List<ExamResponseDTO>> getExamsByCredits(@PathVariable int credits) {
        log.info("Received GET request for /api/exams/by-credits/{}", credits);
        return ResponseEntity.ok(examService.getExamsByCredits(credits));
    }

    @GetMapping("/by-duration-greater-than/{duration}")
    @Operation(summary = "Get exams with duration greater than a value")
    @ApiResponse(responseCode = "200", description = "List of exams with duration greater than the given value")
    public ResponseEntity<List<ExamResponseDTO>> getExamsByDurationGreaterThan(@PathVariable int duration) {
        log.info("Received GET request for /api/exams/by-duration-greater-than/{}", duration);
        return ResponseEntity.ok(examService.getExamsByDurationGreaterThan(duration));
    }
}