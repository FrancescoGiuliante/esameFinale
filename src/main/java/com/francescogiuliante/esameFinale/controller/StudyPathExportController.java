package com.francescogiuliante.esameFinale.controller;

import com.francescogiuliante.esameFinale.service.StudyPathExportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/export")
@Tag(name = "Export", description = "Operations for exporting data")
public class StudyPathExportController {

    @Autowired
    private StudyPathExportService studyPathExportService;

    @GetMapping("/study-paths/excel")
    @Operation(summary = "Export study paths and exams to Excel")
    public ResponseEntity<byte[]> exportStudyPathsToExcel() throws IOException {
        log.info("Received request to export study paths and exams to Excel.");
        byte[] excelBytes = studyPathExportService.exportStudyPathsToExcel();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"study_paths_and_exams.xlsx\"")
                .body(excelBytes);
    }
}