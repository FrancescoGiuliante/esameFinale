package com.francescogiuliante.esameFinale.util;

import com.francescogiuliante.esameFinale.dto.request.ExamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ExamFactory {

    @Autowired
    Random random;

    private static final String[] examNames = {"Written Exam", "Oral Exam", "Class Assignment", "Final Exam", "Project Presentation"};
    private static final String[] examTypes = {"Written", "Oral", "Project", "Practical"};

    public ExamDTO createRandomExamDTO() {
        String name = examNames[random.nextInt(examNames.length)];
        String type = examTypes[random.nextInt(examTypes.length)];
        int duration = 60 + random.nextInt(120);
        int credits = 3 + random.nextInt(6);
        return new ExamDTO(name, type, duration, credits);
    }
}
