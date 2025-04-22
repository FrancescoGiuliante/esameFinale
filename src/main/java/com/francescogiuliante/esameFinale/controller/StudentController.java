package com.francescogiuliante.esameFinale.controller;

import com.francescogiuliante.esameFinale.dto.request.StudentDTO;
import com.francescogiuliante.esameFinale.dto.response.StudentResponseDTO;
import com.francescogiuliante.esameFinale.service.StudentService;
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
@RequestMapping("/api/students")
@Tag(name = "Student", description = "Operations related to students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    @Operation(summary = "Get all students")
    @ApiResponse(responseCode = "200", description = "List of all students")
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        log.info("Received GET request for /api/students");
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID")
    @ApiResponse(responseCode = "200", description = "Student found")
    @ApiResponse(responseCode = "404", description = "Student not found")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        log.info("Received GET request for /api/students/{}", id);
        Optional<StudentResponseDTO> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new student")
    @ApiResponse(responseCode = "201", description = "Student created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<StudentResponseDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        log.info("Received POST request for /api/students with body: {}", studentDTO);
        StudentResponseDTO createdStudent = studentService.createStudent(studentDTO);
        if (createdStudent != null) {
            return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
        } else {
            log.warn("Failed to create student due to invalid input (e.g., Account not found).");
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing student")
    @ApiResponse(responseCode = "200", description = "Student updated")
    @ApiResponse(responseCode = "404", description = "Student not found")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        log.info("Received PUT request for /api/students/{} with body: {}", id, studentDTO);
        Optional<StudentResponseDTO> updatedStudent = studentService.updateStudent(id, studentDTO);
        return updatedStudent.map(ResponseEntity::ok).orElseGet(() -> {
            log.warn("Student with ID {} not found for update.", id);
            return ResponseEntity.notFound().build();
        });
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a student by ID")
    @ApiResponse(responseCode = "204", description = "Student deleted")
    @ApiResponse(responseCode = "404", description = "Student not found")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        log.warn("Received DELETE request for /api/students/{}", id);
        if (studentService.deleteStudent(id)) {
            log.info("Student with ID {} deleted.", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            log.warn("Student with ID {} not found for deletion.", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-birth-date/{birthDate}")
    @Operation(summary = "Get students by birth date")
    @ApiResponse(responseCode = "200", description = "List of students with the given birth date")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByBirthDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate) {
        log.info("Received GET request for /api/students/by-birth-date/{}", birthDate);
        return ResponseEntity.ok(studentService.getStudentsByBirthDate(birthDate));
    }

    @GetMapping("/born-before/{birthDate}")
    @Operation(summary = "Get students born before a certain date")
    @ApiResponse(responseCode = "200", description = "List of students born before the given date")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsBornBefore(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate) {
        log.info("Received GET request for /api/students/born-before/{}", birthDate);
        return ResponseEntity.ok(studentService.getStudentsBornBefore(birthDate));
    }

    @GetMapping("/born-after/{birthDate}")
    @Operation(summary = "Get students born after a certain date")
    @ApiResponse(responseCode = "200", description = "List of students born after the given date")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsBornAfter(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate) {
        log.info("Received GET request for /api/students/born-after/{}", birthDate);
        return ResponseEntity.ok(studentService.getStudentsBornAfter(birthDate));
    }

    @GetMapping("/by-birth-place/{birthPlace}")
    @Operation(summary = "Get students by birth place")
    @ApiResponse(responseCode = "200", description = "List of students born in the given place")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByBirthPlace(@PathVariable String birthPlace) {
        log.info("Received GET request for /api/students/by-birth-place/{}", birthPlace);
        return ResponseEntity.ok(studentService.getStudentsByBirthPlace(birthPlace));
    }

    @GetMapping("/by-enrollment-year/{enrollmentYear}")
    @Operation(summary = "Get students by enrollment year")
    @ApiResponse(responseCode = "200", description = "List of students enrolled in the given year")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByEnrollmentYear(@PathVariable int enrollmentYear) {
        log.info("Received GET request for /api/students/by-enrollment-year/{}", enrollmentYear);
        return ResponseEntity.ok(studentService.getStudentsByEnrollmentYear(enrollmentYear));
    }

    @GetMapping("/enrolled-after-or-in/{enrollmentYear}")
    @Operation(summary = "Get students enrolled after or in the given year")
    @ApiResponse(responseCode = "200", description = "List of students enrolled after or in the given year")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsEnrolledAfterOrIn(@PathVariable int enrollmentYear) {
        log.info("Received GET request for /api/students/enrolled-after-or-in/{}", enrollmentYear);
        return ResponseEntity.ok(studentService.getStudentsEnrolledAfterOrIn(enrollmentYear));
    }

    @GetMapping("/enrolled-before-or-in/{enrollmentYear}")
    @Operation(summary = "Get students enrolled before or in the given year")
    @ApiResponse(responseCode = "200", description = "List of students enrolled before or in the given year")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsEnrolledBeforeOrIn(@PathVariable int enrollmentYear) {
        log.info("Received GET request for /api/students/enrolled-before-or-in/{}", enrollmentYear);
        return ResponseEntity.ok(studentService.getStudentsEnrolledBeforeOrIn(enrollmentYear));
    }
}