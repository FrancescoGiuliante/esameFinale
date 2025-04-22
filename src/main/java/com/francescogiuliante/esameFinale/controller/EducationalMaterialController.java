package com.francescogiuliante.esameFinale.controller;

import com.francescogiuliante.esameFinale.dto.request.EducationalMaterialDTO;
import com.francescogiuliante.esameFinale.dto.response.EducationalMaterialResponseDTO;
import com.francescogiuliante.esameFinale.enums.EducationalMaterialType;
import com.francescogiuliante.esameFinale.service.EducationalMaterialService;
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
@RequestMapping("/api/educational-materials")
@Tag(name = "EducationalMaterial", description = "Operations related to educational materials")
public class EducationalMaterialController {

    @Autowired
    private EducationalMaterialService educationalMaterialService;

    @GetMapping
    @Operation(summary = "Get all educational materials")
    @ApiResponse(responseCode = "200", description = "List of all educational materials")
    public ResponseEntity<List<EducationalMaterialResponseDTO>> getAllEducationalMaterials() {
        log.info("Received GET request for /api/educational-materials");
        return ResponseEntity.ok(educationalMaterialService.getAllEducationalMaterials());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get educational material by ID")
    @ApiResponse(responseCode = "200", description = "Educational material found")
    @ApiResponse(responseCode = "404", description = "Educational material not found")
    public ResponseEntity<EducationalMaterialResponseDTO> getEducationalMaterialById(@PathVariable Long id) {
        log.info("Received GET request for /api/educational-materials/{}", id);
        Optional<EducationalMaterialResponseDTO> material = educationalMaterialService.getEducationalMaterialById(id);
        return material.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new educational material")
    @ApiResponse(responseCode = "201", description = "Educational material created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<EducationalMaterialResponseDTO> createEducationalMaterial(@RequestBody EducationalMaterialDTO educationalMaterialDTO) {
        log.info("Received POST request for /api/educational-materials with body: {}", educationalMaterialDTO);
        EducationalMaterialResponseDTO createdMaterial = educationalMaterialService.createEducationalMaterial(educationalMaterialDTO);
        if (createdMaterial != null) {
            return new ResponseEntity<>(createdMaterial, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing educational material")
    @ApiResponse(responseCode = "200", description = "Educational material updated")
    @ApiResponse(responseCode = "404", description = "Educational material not found")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<EducationalMaterialResponseDTO> updateEducationalMaterial(@PathVariable Long id, @RequestBody EducationalMaterialDTO educationalMaterialDTO) {
        log.info("Received PUT request for /api/educational-materials/{} with body: {}", id, educationalMaterialDTO);
        Optional<EducationalMaterialResponseDTO> updatedMaterial = educationalMaterialService.updateEducationalMaterial(id, educationalMaterialDTO);
        return updatedMaterial.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an educational material by ID")
    @ApiResponse(responseCode = "204", description = "Educational material deleted")
    @ApiResponse(responseCode = "404", description = "Educational material not found")
    public ResponseEntity<Void> deleteEducationalMaterial(@PathVariable Long id) {
        log.warn("Received DELETE request for /api/educational-materials/{}", id);
        if (educationalMaterialService.deleteEducationalMaterial(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-course/{courseId}")
    @Operation(summary = "Get educational materials by course ID")
    @ApiResponse(responseCode = "200", description = "List of educational materials for the given course ID")
    public ResponseEntity<List<EducationalMaterialResponseDTO>> getEducationalMaterialsByCourseId(@PathVariable Long courseId) {
        log.info("Received GET request for /api/educational-materials/by-course/{}", courseId);
        return ResponseEntity.ok(educationalMaterialService.getEducationalMaterialsByCourseId(courseId));
    }

    @GetMapping("/by-teacher/{teacherId}")
    @Operation(summary = "Get educational materials by teacher ID")
    @ApiResponse(responseCode = "200", description = "List of educational materials for the given teacher ID")
    public ResponseEntity<List<EducationalMaterialResponseDTO>> getEducationalMaterialsByTeacherId(@PathVariable Long teacherId) {
        log.info("Received GET request for /api/educational-materials/by-teacher/{}", teacherId);
        return ResponseEntity.ok(educationalMaterialService.getEducationalMaterialsByTeacherId(teacherId));
    }

    @GetMapping("/by-type/{type}")
    @Operation(summary = "Get educational materials by type")
    @ApiResponse(responseCode = "200", description = "List of educational materials for the given type")
    public ResponseEntity<List<EducationalMaterialResponseDTO>> getEducationalMaterialsByType(@PathVariable EducationalMaterialType type) {
        log.info("Received GET request for /api/educational-materials/by-type/{}", type);
        return ResponseEntity.ok(educationalMaterialService.getEducationalMaterialsByType(type));
    }

    @GetMapping("/by-publication-date/{publicationDate}")
    @Operation(summary = "Get educational materials by publication date")
    @ApiResponse(responseCode = "200", description = "List of educational materials for the given publication date")
    public ResponseEntity<List<EducationalMaterialResponseDTO>> getEducationalMaterialsByPublicationDate(@PathVariable LocalDate publicationDate) {
        log.info("Received GET request for /api/educational-materials/by-publication-date/{}", publicationDate);
        return ResponseEntity.ok(educationalMaterialService.getEducationalMaterialsByPublicationDate(publicationDate));
    }

    @GetMapping("/by-title-containing/{title}")
    @Operation(summary = "Get educational materials by title containing a specific string")
    @ApiResponse(responseCode = "200", description = "List of educational materials whose title contains the given string")
    public ResponseEntity<List<EducationalMaterialResponseDTO>> getEducationalMaterialsByTitleContaining(@PathVariable String title) {
        log.info("Received GET request for /api/educational-materials/by-title-containing/{}", title);
        return ResponseEntity.ok(educationalMaterialService.getEducationalMaterialsByTitleContaining(title));
    }
}