package com.francescogiuliante.esameFinale.controller;

import com.francescogiuliante.esameFinale.dto.request.FinalEvaluationDTO;
import com.francescogiuliante.esameFinale.dto.response.FinalEvaluationResponseDTO;
import com.francescogiuliante.esameFinale.service.FinalEvaluationService;
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
@RequestMapping("/api/final-evaluations")
@Tag(name = "FinalEvaluation", description = "Operations related to final evaluations")
public class FinalEvaluationController {

    @Autowired
    private FinalEvaluationService finalEvaluationService;

    @GetMapping
    @Operation(summary = "Get all final evaluations")
    @ApiResponse(responseCode = "200", description = "List of all final evaluations")
    public ResponseEntity<List<FinalEvaluationResponseDTO>> getAllFinalEvaluations() {
        log.info("Received GET request for /api/final-evaluations");
        return ResponseEntity.ok(finalEvaluationService.getAllFinalEvaluations());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get final evaluation by ID")
    @ApiResponse(responseCode = "200", description = "Final evaluation found")
    @ApiResponse(responseCode = "404", description = "Final evaluation not found")
    public ResponseEntity<FinalEvaluationResponseDTO> getFinalEvaluationById(@PathVariable Long id) {
        log.info("Received GET request for /api/final-evaluations/{}", id);
        Optional<FinalEvaluationResponseDTO> evaluation = finalEvaluationService.getFinalEvaluationById(id);
        return evaluation.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new final evaluation")
    @ApiResponse(responseCode = "201", description = "Final evaluation created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<FinalEvaluationResponseDTO> createFinalEvaluation(@RequestBody FinalEvaluationDTO finalEvaluationDTO) {
        log.info("Received POST request for /api/final-evaluations with body: {}", finalEvaluationDTO);
        FinalEvaluationResponseDTO createdEvaluation = finalEvaluationService.createFinalEvaluation(finalEvaluationDTO);
        if (createdEvaluation != null) {
            return new ResponseEntity<>(createdEvaluation, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing final evaluation")
    @ApiResponse(responseCode = "200", description = "Final evaluation updated")
    @ApiResponse(responseCode = "404", description = "Final evaluation not found")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<FinalEvaluationResponseDTO> updateFinalEvaluation(@PathVariable Long id, @RequestBody FinalEvaluationDTO finalEvaluationDTO) {
        log.info("Received PUT request for /api/final-evaluations/{} with body: {}", id, finalEvaluationDTO);
        Optional<FinalEvaluationResponseDTO> updatedEvaluation = finalEvaluationService.updateFinalEvaluation(id, finalEvaluationDTO);
        return updatedEvaluation.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a final evaluation by ID")
    @ApiResponse(responseCode = "204", description = "Final evaluation deleted")
    @ApiResponse(responseCode = "404", description = "Final evaluation not found")
    public ResponseEntity<Void> deleteFinalEvaluation(@PathVariable Long id) {
        log.warn("Received DELETE request for /api/final-evaluations/{}", id);
        if (finalEvaluationService.deleteFinalEvaluation(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-student/{studentId}")
    @Operation(summary = "Get final evaluations by student ID")
    @ApiResponse(responseCode = "200", description = "List of final evaluations for the given student ID")
    public ResponseEntity<List<FinalEvaluationResponseDTO>> getFinalEvaluationsByStudentId(@PathVariable Long studentId) {
        log.info("Received GET request for /api/final-evaluations/by-student/{}", studentId);
        return ResponseEntity.ok(finalEvaluationService.getFinalEvaluationsByStudentId(studentId));
    }

    @GetMapping("/by-course/{courseId}")
    @Operation(summary = "Get final evaluations by course ID")
    @ApiResponse(responseCode = "200", description = "List of final evaluations for the given course ID")
    public ResponseEntity<List<FinalEvaluationResponseDTO>> getFinalEvaluationsByCourseId(@PathVariable Long courseId) {
        log.info("Received GET request for /api/final-evaluations/by-course/{}", courseId);
        return ResponseEntity.ok(finalEvaluationService.getFinalEvaluationsByCourseId(courseId));
    }

    @GetMapping("/by-student/{studentId}/and-course/{courseId}")
    @Operation(summary = "Get final evaluation by student ID and course ID")
    @ApiResponse(responseCode = "200", description = "Final evaluation found for the given student and course")
    @ApiResponse(responseCode = "404", description = "Final evaluation not found for the given student and course")
    public ResponseEntity<FinalEvaluationResponseDTO> getFinalEvaluationByStudentIdAndCourseId(@PathVariable Long studentId, @PathVariable Long courseId) {
        log.info("Received GET request for /api/final-evaluations/by-student/{}/and-course/{}", studentId, courseId);
        Optional<FinalEvaluationResponseDTO> evaluation = finalEvaluationService.getFinalEvaluationByStudentIdAndCourseId(studentId, courseId);
        return evaluation.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-grade/greater-than-equal/{grade}")
    @Operation(summary = "Get final evaluations with grade greater than or equal to a value")
    @ApiResponse(responseCode = "200", description = "List of final evaluations with the specified minimum grade")
    public ResponseEntity<List<FinalEvaluationResponseDTO>> getFinalEvaluationsByFinalGradeGreaterThanEqual(@PathVariable int grade) {
        log.info("Received GET request for /api/final-evaluations/by-grade/greater-than-equal/{}", grade);
        return ResponseEntity.ok(finalEvaluationService.getFinalEvaluationsByFinalGradeGreaterThanEqual(grade));
    }

    @GetMapping("/by-grade/less-than-equal/{grade}")
    @Operation(summary = "Get final evaluations with grade less than or equal to a value")
    @ApiResponse(responseCode = "200", description = "List of final evaluations with the specified maximum grade")
    public ResponseEntity<List<FinalEvaluationResponseDTO>> getFinalEvaluationsByFinalGradeLessThanEqual(@PathVariable int grade) {
        log.info("Received GET request for /api/final-evaluations/by-grade/less-than-equal/{}", grade);
        return ResponseEntity.ok(finalEvaluationService.getFinalEvaluationsByFinalGradeLessThanEqual(grade));
    }
}