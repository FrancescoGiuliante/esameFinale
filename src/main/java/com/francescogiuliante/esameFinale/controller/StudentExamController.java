package com.francescogiuliante.esameFinale.controller;

import com.francescogiuliante.esameFinale.dto.request.StudentExamDTO;
import com.francescogiuliante.esameFinale.dto.response.StudentExamResponseDTO;
import com.francescogiuliante.esameFinale.service.StudentExamService;
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
@RequestMapping("/api/student-exams")
@Tag(name = "StudentExam", description = "Operations related to student exams")
public class StudentExamController {

    @Autowired
    private StudentExamService studentExamService;

    @GetMapping
    @Operation(summary = "Get all student exams")
    @ApiResponse(responseCode = "200", description = "List of all student exams")
    public ResponseEntity<List<StudentExamResponseDTO>> getAllStudentExams() {
        log.info("Received GET request for /api/student-exams");
        return ResponseEntity.ok(studentExamService.getAllStudentExams());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student exam by ID")
    @ApiResponse(responseCode = "200", description = "Student exam found")
    @ApiResponse(responseCode = "404", description = "Student exam not found")
    public ResponseEntity<StudentExamResponseDTO> getStudentExamById(@PathVariable Long id) {
        log.info("Received GET request for /api/student-exams/{}", id);
        Optional<StudentExamResponseDTO> studentExam = studentExamService.getStudentExamById(id);
        return studentExam.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new student exam")
    @ApiResponse(responseCode = "201", description = "Student exam created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<StudentExamResponseDTO> createStudentExam(@RequestBody StudentExamDTO studentExamDTO) {
        log.info("Received POST request for /api/student-exams with body: {}", studentExamDTO);
        StudentExamResponseDTO createdStudentExam = studentExamService.createStudentExam(studentExamDTO);
        if (createdStudentExam != null) {
            return new ResponseEntity<>(createdStudentExam, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing student exam")
    @ApiResponse(responseCode = "200", description = "Student exam updated")
    @ApiResponse(responseCode = "404", description = "Student exam not found")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<StudentExamResponseDTO> updateStudentExam(@PathVariable Long id, @RequestBody StudentExamDTO studentExamDTO) {
        log.info("Received PUT request for /api/student-exams/{} with body: {}", id, studentExamDTO);
        Optional<StudentExamResponseDTO> updatedStudentExam = studentExamService.updateStudentExam(id, studentExamDTO);
        return updatedStudentExam.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a student exam by ID")
    @ApiResponse(responseCode = "204", description = "Student exam deleted")
    @ApiResponse(responseCode = "404", description = "Student exam not found")
    public ResponseEntity<Void> deleteStudentExam(@PathVariable Long id) {
        log.warn("Received DELETE request for /api/student-exams/{}", id);
        if (studentExamService.deleteStudentExam(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-student/{studentId}")
    @Operation(summary = "Get student exams by student ID")
    @ApiResponse(responseCode = "200", description = "List of student exams for the given student ID")
    public ResponseEntity<List<StudentExamResponseDTO>> getStudentExamsByStudentId(@PathVariable Long studentId) {
        log.info("Received GET request for /api/student-exams/by-student/{}", studentId);
        return ResponseEntity.ok(studentExamService.getStudentExamsByStudentId(studentId));
    }

    @GetMapping("/by-exam/{examId}")
    @Operation(summary = "Get student exams by exam ID")
    @ApiResponse(responseCode = "200", description = "List of student exams for the given exam ID")
    public ResponseEntity<List<StudentExamResponseDTO>> getStudentExamsByExamId(@PathVariable Long examId) {
        log.info("Received GET request for /api/student-exams/by-exam/{}", examId);
        return ResponseEntity.ok(studentExamService.getStudentExamsByExamId(examId));
    }

    @GetMapping("/by-student/{studentId}/and-exam/{examId}")
    @Operation(summary = "Get student exam by student ID and exam ID")
    @ApiResponse(responseCode = "200", description = "Student exam found for the given student and exam")
    @ApiResponse(responseCode = "404", description = "Student exam not found for the given student and exam")
    public ResponseEntity<StudentExamResponseDTO> getStudentExamByStudentIdAndExamId(@PathVariable Long studentId, @PathVariable Long examId) {
        log.info("Received GET request for /api/student-exams/by-student/{}/and-exam/{}", studentId, examId);
        Optional<StudentExamResponseDTO> studentExam = studentExamService.getStudentExamByStudentIdAndExamId(studentId, examId);
        return studentExam.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-grade/{grade}")
    @Operation(summary = "Get student exams by grade")
    @ApiResponse(responseCode = "200", description = "List of student exams with the given grade")
    public ResponseEntity<List<StudentExamResponseDTO>> getStudentExamsByGrade(@PathVariable int grade) {
        log.info("Received GET request for /api/student-exams/by-grade/{}", grade);
        return ResponseEntity.ok(studentExamService.getStudentExamsByGrade(grade));
    }

    @GetMapping("/by-grade/greater-than-equal/{grade}")
    @Operation(summary = "Get student exams with grade greater than or equal to")
    @ApiResponse(responseCode = "200", description = "List of student exams with the given minimum grade")
    public ResponseEntity<List<StudentExamResponseDTO>> getStudentExamsByGradeGreaterThanEqual(@PathVariable int grade) {
        log.info("Received GET request for /api/student-exams/by-grade/greater-than-equal/{}", grade);
        return ResponseEntity.ok(studentExamService.getStudentExamsByGradeGreaterThanEqual(grade));
    }

    @GetMapping("/by-grade/less-than-equal/{grade}")
    @Operation(summary = "Get student exams with grade less than or equal to")
    @ApiResponse(responseCode = "200", description = "List of student exams with the given maximum grade")
    public ResponseEntity<List<StudentExamResponseDTO>> getStudentExamsByGradeLessThanEqual(@PathVariable int grade) {
        log.info("Received GET request for /api/student-exams/by-grade/less-than-equal/{}", grade);
        return ResponseEntity.ok(studentExamService.getStudentExamsByGradeLessThanEqual(grade));
    }
}