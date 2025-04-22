package com.francescogiuliante.esameFinale.controller;

import com.francescogiuliante.esameFinale.dto.request.CourseDTO;
import com.francescogiuliante.esameFinale.dto.response.CourseResponseDTO;
import com.francescogiuliante.esameFinale.service.CourseService;
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
@RequestMapping("/api/courses")
@Tag(name = "Course", description = "Operations related to courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    @Operation(summary = "Get all courses")
    @ApiResponse(responseCode = "200", description = "List of all courses")
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses() {
        log.info("Received GET request for /api/courses");
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID")
    @ApiResponse(responseCode = "200", description = "Course found")
    @ApiResponse(responseCode = "404", description = "Course not found")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Long id) {
        log.info("Received GET request for /api/courses/{}", id);
        Optional<CourseResponseDTO> course = courseService.getCourseById(id);
        return course.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new course")
    @ApiResponse(responseCode = "201", description = "Course created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<CourseResponseDTO> createCourse(@RequestBody CourseDTO courseDTO) {
        log.info("Received POST request for /api/courses with body: {}", courseDTO);
        CourseResponseDTO createdCourse = courseService.createCourse(courseDTO);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing course")
    @ApiResponse(responseCode = "200", description = "Course updated")
    @ApiResponse(responseCode = "404", description = "Course not found")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<CourseResponseDTO> updateCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        log.info("Received PUT request for /api/courses/{} with body: {}", id, courseDTO);
        Optional<CourseResponseDTO> updatedCourse = courseService.updateCourse(id, courseDTO);
        return updatedCourse.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a course by ID")
    @ApiResponse(responseCode = "204", description = "Course deleted")
    @ApiResponse(responseCode = "404", description = "Course not found")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        log.info("Received DELETE request for /api/courses/{}", id);
        if (courseService.deleteCourse(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-category/{category}")
    @Operation(summary = "Get courses by category")
    @ApiResponse(responseCode = "200", description = "List of courses in the given category")
    public ResponseEntity<List<CourseResponseDTO>> getCoursesByCategory(@PathVariable String category) {
        log.info("Received GET request for /api/courses/by-category/{}", category);
        List<CourseResponseDTO> courses = courseService.getCoursesByCategory(category);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/by-academic-year/{academicYear}")
    @Operation(summary = "Get courses by academic year")
    @ApiResponse(responseCode = "200", description = "List of courses for the given academic year")
    public ResponseEntity<List<CourseResponseDTO>> getCoursesByAcademicYear(@PathVariable String academicYear) {
        log.info("Received GET request for /api/courses/by-academic-year/{}", academicYear);
        List<CourseResponseDTO> courses = courseService.getCoursesByAcademicYear(academicYear);
        return ResponseEntity.ok(courses);
    }
}