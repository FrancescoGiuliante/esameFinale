package com.francescogiuliante.esameFinale.service;

import com.francescogiuliante.esameFinale.model.Student;
import com.francescogiuliante.esameFinale.repository.StudentRepository;
import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Slf4j
@Service
public class StudentExportService {

    @Autowired
    private StudentRepository studentRepository;

    public void writeStudentsToCsv(Writer writer) throws IOException {
        log.info("Starting to write student data to CSV.");
        try (CSVWriter csvWriter = new CSVWriter(writer)) {
            String[] header = {"Student ID", "Name", "Surname", "Birth Date", "Birth Place", "Enrollment Year", "Phone"};
            csvWriter.writeNext(header);

            List<Student> students = studentRepository.findAll();

            for (Student student : students) {
                String[] data = {
                        String.valueOf(student.getId()),
                        student.getAccount().getFirstName(),
                        student.getAccount().getLastName(),
                        student.getBirthDate().toString(),
                        student.getBirthPlace(),
                        String.valueOf(student.getEnrollmentYear()),
                        student.getAccount().getPhone()
                };
                csvWriter.writeNext(data);
            }
        }
        log.info("Student data written to CSV completed.");
    }
}