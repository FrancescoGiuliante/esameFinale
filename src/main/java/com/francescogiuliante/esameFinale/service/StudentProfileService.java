package com.francescogiuliante.esameFinale.service;

import com.francescogiuliante.esameFinale.dto.response.EnrollmentResponseDTO;
import com.francescogiuliante.esameFinale.dto.response.FinalEvaluationResponseDTO;
import com.francescogiuliante.esameFinale.dto.response.StudentExamResponseDTO;
import com.francescogiuliante.esameFinale.dto.response.StudentProfileDTO;
import com.francescogiuliante.esameFinale.model.Student;
import com.francescogiuliante.esameFinale.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StudentProfileService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private FinalEvaluationService finalEvaluationService;

    @Autowired
    private StudentExamService studentExamService;

    public Optional<byte[]> generateStudentPdfProfile(Long studentId) throws IOException {
        log.info("Generating PDF profile for student with ID: {}", studentId);
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if (studentOptional.isEmpty()) {
            log.warn("Student with ID {} not found.", studentId);
            return Optional.empty();
        }

        Student student = studentOptional.get();
        List<EnrollmentResponseDTO> enrollments = enrollmentService.getEnrollmentsByStudentId(studentId);
        List<FinalEvaluationResponseDTO> finalEvaluations = finalEvaluationService.getFinalEvaluationsByStudentId(studentId);
        List<StudentExamResponseDTO> studentExams = studentExamService.getStudentExamsByStudentId(studentId);

        StudentProfileDTO profileDTO = new StudentProfileDTO(
                student.getId(),
                student.getAccount().getFirstName(),
                student.getAccount().getLastName(),
                student.getBirthDate(),
                student.getBirthPlace(),
                student.getEnrollmentYear(),
                enrollments,
                finalEvaluations,
                studentExams
        );

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        document.add(new Paragraph("Student Profile"));
        document.add(new Paragraph("ID: " + profileDTO.getId()));
        document.add(new Paragraph("First Name: " + profileDTO.getFirstName()));
        document.add(new Paragraph("Last Name: " + profileDTO.getLastName()));
        document.add(new Paragraph("Birth Date: " + profileDTO.getBirthDate()));
        document.add(new Paragraph("Birth Place: " + profileDTO.getBirthPlace()));
        document.add(new Paragraph("Enrollment Year: " + profileDTO.getEnrollmentYear()));

        if (!profileDTO.getEnrollments().isEmpty()) {
            document.add(new Paragraph("\nEnrollments:"));
            for (EnrollmentResponseDTO enrollment : profileDTO.getEnrollments()) {
                document.add(new Paragraph("- Course: " + enrollment.getCourseName() +
                        ", Enrollment Date: " + enrollment.getEnrollmentDate() +
                        ", Status: " + enrollment.getEnrollmentStatus()));
            }
        }

        if (!profileDTO.getFinalEvaluations().isEmpty()) {
            document.add(new Paragraph("\nFinal Evaluations:"));
            for (FinalEvaluationResponseDTO evaluation : profileDTO.getFinalEvaluations()) {
                document.add(new Paragraph("- Course: " + evaluation.getCourseName() +
                        ", Final Grade: " + evaluation.getFinalGrade() +
                        ", Feedback: " + evaluation.getFinalFeedback()));
            }
        }

        if (!profileDTO.getStudentExams().isEmpty()) {
            document.add(new Paragraph("\nExams Taken:"));
            for (StudentExamResponseDTO exam : profileDTO.getStudentExams()) {
                document.add(new Paragraph("- Exam: " + exam.getExamName() +
                        ", Grade: " + exam.getGrade()));
            }
        }

        document.close();
        log.info("PDF profile generated successfully for student with ID: {}", studentId);
        return Optional.of(outputStream.toByteArray());
    }
}