package com.francescogiuliante.esameFinale.controller;

import com.francescogiuliante.esameFinale.service.StudentProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/export")
@Tag(name = "Export", description = "Operations for exporting data")
public class StudentProfileController {

    @Autowired
    private StudentProfileService studentProfileService;

    @GetMapping("/students/{studentId}/pdf")
    @Operation(summary = "Export student profile to PDF")
    public ResponseEntity<byte[]> exportStudentProfileToPdf(@PathVariable Long studentId) throws IOException {
        log.info("Received request to export student {} profile to PDF.", studentId);
        Optional<byte[]> pdfBytesOptional = studentProfileService.generateStudentPdfProfile(studentId);

        return pdfBytesOptional.map(pdfBytes -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"student_" + studentId + "_profile.pdf\"")
                        .body(pdfBytes))
                .orElse(ResponseEntity.notFound().build());
    }
}