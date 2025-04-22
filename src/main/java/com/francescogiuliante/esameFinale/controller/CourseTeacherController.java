package com.francescogiuliante.esameFinale.controller;

import com.francescogiuliante.esameFinale.dto.request.CourseTeacherDTO;
import com.francescogiuliante.esameFinale.dto.response.CourseTeacherResponseDTO;
import com.francescogiuliante.esameFinale.service.CourseTeacherService;
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
@RequestMapping("/api/course-teachers")
@Tag(name = "CourseTeacher", description = "Operations related to course-teacher assignments")
public class CourseTeacherController {

    @Autowired
    private CourseTeacherService courseTeacherService;

    @GetMapping
    @Operation(summary = "Get all course-teacher assignments")
    @ApiResponse(responseCode = "200", description = "List of all course-teacher assignments")
    public ResponseEntity<List<CourseTeacherResponseDTO>> getAllCourseTeachers() {
        log.info("Received GET request for /api/course-teachers");
        return ResponseEntity.ok(courseTeacherService.getAllCourseTeachers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course-teacher assignment by ID")
    @ApiResponse(responseCode = "200", description = "Course-teacher assignment found")
    @ApiResponse(responseCode = "404", description = "Course-teacher assignment not found")
    public ResponseEntity<CourseTeacherResponseDTO> getCourseTeacherById(@PathVariable Long id) {
        log.info("Received GET request for /api/course-teachers/{}", id);
        Optional<CourseTeacherResponseDTO> courseTeacher = courseTeacherService.getCourseTeacherById(id);
        return courseTeacher.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new course-teacher assignment")
    @ApiResponse(responseCode = "201", description = "Course-teacher assignment created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<CourseTeacherResponseDTO> createCourseTeacher(@RequestBody CourseTeacherDTO courseTeacherDTO) {
        log.info("Received POST request for /api/course-teachers with body: {}", courseTeacherDTO);
        CourseTeacherResponseDTO createdCourseTeacher = courseTeacherService.createCourseTeacher(courseTeacherDTO);
        if (createdCourseTeacher != null) {
            return new ResponseEntity<>(createdCourseTeacher, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing course-teacher assignment")
    @ApiResponse(responseCode = "200", description = "Course-teacher assignment updated")
    @ApiResponse(responseCode = "404", description = "Course-teacher assignment not found")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<CourseTeacherResponseDTO> updateCourseTeacher(@PathVariable Long id, @RequestBody CourseTeacherDTO courseTeacherDTO) {
        log.info("Received PUT request for /api/course-teachers/{} with body: {}", id, courseTeacherDTO);
        Optional<CourseTeacherResponseDTO> updatedCourseTeacher = courseTeacherService.updateCourseTeacher(id, courseTeacherDTO);
        return updatedCourseTeacher.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a course-teacher assignment by ID")
    @ApiResponse(responseCode = "204", description = "Course-teacher assignment deleted")
    @ApiResponse(responseCode = "404", description = "Course-teacher assignment not found")
    public ResponseEntity<Void> deleteCourseTeacher(@PathVariable Long id) {
        log.warn("Received DELETE request for /api/course-teachers/{}", id);
        if (courseTeacherService.deleteCourseTeacher(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-course/{courseId}")
    @Operation(summary = "Get course-teacher assignments by course ID")
    @ApiResponse(responseCode = "200", description = "List of course-teacher assignments for the given course ID")
    public ResponseEntity<List<CourseTeacherResponseDTO>> getCourseTeachersByCourseId(@PathVariable Long courseId) {
        log.info("Received GET request for /api/course-teachers/by-course/{}", courseId);
        return ResponseEntity.ok(courseTeacherService.getCourseTeachersByCourseId(courseId));
    }

    @GetMapping("/by-teacher/{teacherId}")
    @Operation(summary = "Get course-teacher assignments by teacher ID")
    @ApiResponse(responseCode = "200", description = "List of course-teacher assignments for the given teacher ID")
    public ResponseEntity<List<CourseTeacherResponseDTO>> getCourseTeachersByTeacherId(@PathVariable Long teacherId) {
        log.info("Received GET request for /api/course-teachers/by-teacher/{}", teacherId);
        return ResponseEntity.ok(courseTeacherService.getCourseTeachersByTeacherId(teacherId));
    }

    @GetMapping("/by-start-date/{startDate}")
    @Operation(summary = "Get course-teacher assignments by start date")
    @ApiResponse(responseCode = "200", description = "List of course-teacher assignments for the given start date")
    public ResponseEntity<List<CourseTeacherResponseDTO>> getCourseTeachersByStartDate(@PathVariable LocalDate startDate) {
        log.info("Received GET request for /api/course-teachers/by-start-date/{}", startDate);
        return ResponseEntity.ok(courseTeacherService.getCourseTeachersByStartDate(startDate));
    }
}