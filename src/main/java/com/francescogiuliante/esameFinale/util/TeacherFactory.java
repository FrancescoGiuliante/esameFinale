package com.francescogiuliante.esameFinale.util;

import com.francescogiuliante.esameFinale.dto.request.registration.TeacherRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class TeacherFactory {

    @Autowired
    Random random;

    private static final String[] firstNames = {"Alberto", "Marie", "Isaac", "Galileo", "Ada", "Alan", "Hypatia", "Nikola", "Emmy", "Stephen"};
    private static final String[] lastNames = {"Einstein", "Curie", "Newton", "Galilei", "Lovelace", "Turing", "of Alexandria", "Tesla", "Noether", "Hawking"};
    private static final String[] specializations = {"Physics", "Chemistry", "Mathematics", "Astronomy", "Computer Science", "Biology", "Engineering", "Philosophy", "History", "Literature"};
    private static final String[] phonePrefixes = {"333", "347", "328"};
    private static final String defaultPassword = "password";

    public TeacherRegistrationDTO createRandomTeacher() {
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        String phone = phonePrefixes[random.nextInt(phonePrefixes.length)] + String.format("%07d", random.nextInt(10000000));
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase().replaceAll("\\s+", "") + "@university.com";
        String specialization = specializations[random.nextInt(specializations.length)];
        int yearsOfExperience = random.nextInt(30);
        LocalDate hiringDate = generateRandomDate(LocalDate.of(2000, 1, 1), LocalDate.now().minusYears(1));
        return new TeacherRegistrationDTO(firstName, lastName, phone, email, defaultPassword, specialization, yearsOfExperience, hiringDate);
    }

    private LocalDate generateRandomDate(LocalDate start, LocalDate end) {
        long startEpochDay = start.toEpochDay();
        long endEpochDay = end.toEpochDay();
        long randomDay = startEpochDay + random.nextInt((int) (endEpochDay - startEpochDay + 1));
        return LocalDate.ofEpochDay(randomDay);
    }

    public List<TeacherRegistrationDTO> createMultipleRandomTeachers(int count) {
        List<TeacherRegistrationDTO> teachers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            teachers.add(createRandomTeacher());
        }
        return teachers;
    }
}
