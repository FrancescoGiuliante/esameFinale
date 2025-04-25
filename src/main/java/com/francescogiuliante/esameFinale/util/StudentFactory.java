package com.francescogiuliante.esameFinale.util;

import com.francescogiuliante.esameFinale.dto.request.registration.StudentRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class StudentFactory {

    @Autowired
    Random random;

    private static final String[] firstNames = {"Mario", "Luigi", "Giovanna", "Carlo", "Francesca", "Gianmarino", "Carlotta"};
    private static final String[] lastNames = {"Rossi", "Verdi", "Bianchi", "Neri", "Gialli", "Giuliante"};
    private static final String[] cities = {"Roma", "Milano", "Napoli", "Torino", "Firenze", "Sant'Apollinare", "Lanciano"};
    private static final String[] phonePrefixes = {"333", "347", "328"};
    private static final String defaultPassword = "password";

    public StudentRegistrationDTO createRandomStudent() {
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        String phone = phonePrefixes[random.nextInt(phonePrefixes.length)] + String.format("%07d", random.nextInt(10000000));
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com";
        LocalDate birthDate = generateRandomDate(LocalDate.of(1995, 1, 1), LocalDate.of(2005, 12, 31));
        String birthPlace = cities[random.nextInt(cities.length)];
        int enrollmentYear = 2020 + random.nextInt(2024 - 2020 + 1);
        return new StudentRegistrationDTO(firstName, lastName, phone, email, defaultPassword, birthDate, birthPlace, enrollmentYear);
    }

    private LocalDate generateRandomDate(LocalDate start, LocalDate end) {
        long startEpochDay = start.toEpochDay();
        long endEpochDay = end.toEpochDay();
        long randomDay = startEpochDay + random.nextInt((int) (endEpochDay - startEpochDay + 1));
        return LocalDate.ofEpochDay(randomDay);
    }

    public List<StudentRegistrationDTO> createMultipleRandomStudents(int count) {
        List<StudentRegistrationDTO> students = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            students.add(createRandomStudent());
        }
        return students;
    }
}
