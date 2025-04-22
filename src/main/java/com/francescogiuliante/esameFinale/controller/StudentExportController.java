package com.francescogiuliante.esameFinale.controller;

import com.francescogiuliante.esameFinale.service.StudentExportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/export")
@Tag(name = "Export", description = "Operations for exporting data")
public class StudentExportController {

    @Autowired
    private StudentExportService studentExportService;

    @GetMapping("/students/csv")
    @Operation(summary = "Export all students to CSV")
    public void exportAllStudentsToCsv(HttpServletResponse response) throws IOException {
        log.info("Received request to export all students to CSV.");
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"students.csv\"");
        studentExportService.writeStudentsToCsv(response.getWriter());
        log.info("Student CSV export completed.");
    }
}