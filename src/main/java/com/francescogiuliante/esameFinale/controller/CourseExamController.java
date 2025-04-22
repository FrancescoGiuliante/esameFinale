package com.francescogiuliante.esameFinale.controller;

import com.francescogiuliante.esameFinale.dto.request.CourseExamDTO;
import com.francescogiuliante.esameFinale.dto.response.CourseExamResponseDTO;
import com.francescogiuliante.esameFinale.service.CourseExamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/course-exams")
@Tag(name = "CourseExam", description = "Operations related to course exams")
public class CourseExamController {

    @Autowired
    private CourseExamService courseExamService;

    @GetMapping
    @Operation(summary = "Get all course exams")
    @ApiResponse(responseCode = "200", description = "List of all course exams")
    public ResponseEntity<List<CourseExamResponseDTO>> getAllCourseExams() {
        log.info("Received GET request for /api/course-exams");
        return ResponseEntity.ok(courseExamService.getAllCourseExams());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course exam by ID")
    @ApiResponse(responseCode = "200", description = "Course exam found")
    @ApiResponse(responseCode = "404", description = "Course exam not found")
    public ResponseEntity<CourseExamResponseDTO> getCourseExamById(@PathVariable Long id) {
        log.info("Received GET request for /api/course-exams/{}", id);
        Optional<CourseExamResponseDTO> courseExam = courseExamService.getCourseExamById(id);
        return courseExam.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new course exam")
    @ApiResponse(responseCode = "201", description = "Course exam created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<CourseExamResponseDTO> createCourseExam(@RequestBody CourseExamDTO courseExamDTO) {
        log.info("Received POST request for /api/course-exams with body: {}", courseExamDTO);
        CourseExamResponseDTO createdCourseExam = courseExamService.createCourseExam(courseExamDTO);
        if (createdCourseExam != null) {
            return new ResponseEntity<>(createdCourseExam, HttpStatus.CREATED);
        } else {
            log.warn("Failed to create course exam due to invalid input.");
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing course exam")
    @ApiResponse(responseCode = "200", description = "Course exam updated")
    @ApiResponse(responseCode = "404", description = "Course exam not found")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<CourseExamResponseDTO> updateCourseExam(@PathVariable Long id, @RequestBody CourseExamDTO courseExamDTO) {
        log.info("Received PUT request for /api/course-exams/{} with body: {}", id, courseExamDTO);
        Optional<CourseExamResponseDTO> updatedCourseExam = courseExamService.updateCourseExam(id, courseExamDTO);
        return updatedCourseExam.map(ResponseEntity::ok).orElseGet(() -> {
            log.warn("Course exam with ID {} not found for update.", id);
            return ResponseEntity.notFound().build();
        });
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a course exam by ID")
    @ApiResponse(responseCode = "204", description = "Course exam deleted")
    @ApiResponse(responseCode = "404", description = "Course exam not found")
    public ResponseEntity<Void> deleteCourseExam(@PathVariable Long id) {
        log.warn("Received DELETE request for /api/course-exams/{}", id);
        if (courseExamService.deleteCourseExam(id)) {
            log.info("Course exam with ID {} deleted.", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            log.warn("Course exam with ID {} not found for deletion.", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-course/{courseId}")
    @Operation(summary = "Get course exams by course ID")
    @ApiResponse(responseCode = "200", description = "List of course exams for the given course ID")
    public ResponseEntity<List<CourseExamResponseDTO>> getCourseExamsByCourseId(@PathVariable Long courseId) {
        log.info("Received GET request for /api/course-exams/by-course/{}", courseId);
        return ResponseEntity.ok(courseExamService.getCourseExamsByCourseId(courseId));
    }

    @GetMapping("/by-exam/{examId}")
    @Operation(summary = "Get course exams by exam ID")
    @ApiResponse(responseCode = "200", description = "List of course exams for the given exam ID")
    public ResponseEntity<List<CourseExamResponseDTO>> getCourseExamsByExamId(@PathVariable Long examId) {
        log.info("Received GET request for /api/course-exams/by-exam/{}", examId);
        return ResponseEntity.ok(courseExamService.getCourseExamsByExamId(examId));
    }

    @GetMapping("/by-date/{examDate}")
    @Operation(summary = "Get course exams by exam date")
    @ApiResponse(responseCode = "200", description = "List of course exams on the given date")
    public ResponseEntity<List<CourseExamResponseDTO>> getCourseExamsByExamDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate examDate) {
        log.info("Received GET request for /api/course-exams/by-date/{}", examDate);
        return ResponseEntity.ok(courseExamService.getCourseExamsByExamDate(examDate));
    }

    @GetMapping("/by-date-greater-than/{examDate}")
    @Operation(summary = "Get course exams after a certain date")
    @ApiResponse(responseCode = "200", description = "List of course exams after the given date")
    public ResponseEntity<List<CourseExamResponseDTO>> getCourseExamsByExamDateGreaterThan(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate examDate) {
        log.info("Received GET request for /api/course-exams/by-date-greater-than/{}", examDate);
        return ResponseEntity.ok(courseExamService.getCourseExamsByExamDateGreaterThan(examDate));
    }

    @GetMapping("/by-academic-year/{academicYear}")
    @Operation(summary = "Get course exams by academic year of the course")
    @ApiResponse(responseCode = "200", description = "List of course exams for the given academic year")
    public ResponseEntity<List<CourseExamResponseDTO>> getCourseExamsByAcademicYear(@PathVariable String academicYear) {
        log.info("Received GET request for /api/course-exams/by-academic-year/{}", academicYear);
        return ResponseEntity.ok(courseExamService.getCourseExamsByAcademicYear(academicYear));
    }
}