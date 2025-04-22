package com.francescogiuliante.esameFinale.service;

import com.francescogiuliante.esameFinale.dto.response.ExamResponseDTO;
import com.francescogiuliante.esameFinale.mapper.ExamMapper;
import com.francescogiuliante.esameFinale.model.Course;
import com.francescogiuliante.esameFinale.model.Exam;
import com.francescogiuliante.esameFinale.repository.CourseRepository;
import com.francescogiuliante.esameFinale.repository.ExamRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class StudyPathExportService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ExamMapper examMapper;

    public byte[] exportStudyPathsToExcel() throws IOException {
        log.info("Starting to export study paths and exams to Excel.");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Study Paths and Exams");

        int rowNum = 0;
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue("Study Path");
        headerRow.createCell(1).setCellValue("Exam Name");
        headerRow.createCell(2).setCellValue("Exam Type");
        headerRow.createCell(3).setCellValue("Duration (minutes)");
        headerRow.createCell(4).setCellValue("Credits");

        List<Course> courses = courseRepository.findAll();
        List<Exam> allExams = examRepository.findAll();
        List<ExamResponseDTO> examDTOs = allExams.stream().map(examMapper::toResponseDTO).toList();

        for (Course course : courses) {
            for (ExamResponseDTO exam : examDTOs) {
                Row dataRow = sheet.createRow(rowNum++);
                dataRow.createCell(0).setCellValue(course.getName());
                dataRow.createCell(1).setCellValue(exam.getName());
                dataRow.createCell(2).setCellValue(exam.getType());
                dataRow.createCell(3).setCellValue(exam.getDuration());
                dataRow.createCell(4).setCellValue(exam.getCredits());
            }
            if (rowNum > 1) {
                sheet.createRow(rowNum++);
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        log.info("Study paths and exams exported to Excel successfully.");
        return outputStream.toByteArray();
    }
}