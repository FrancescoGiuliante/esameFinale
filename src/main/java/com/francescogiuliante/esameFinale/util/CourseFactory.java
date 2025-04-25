package com.francescogiuliante.esameFinale.util;

import com.francescogiuliante.esameFinale.dto.request.CourseDTO;
import com.francescogiuliante.esameFinale.enums.CourseCategory;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseFactory {

    @Autowired
    Random random;

    private static final String[] courseNames = {"Physics 101", "Calculus 201", "Introduction to Programming", "Data Structures", "Database Management", "Operating Systems", "Software Engineering", "Web Development", "Artificial Intelligence", "Machine Learning"};
    private static final String[] descriptions = {
            "Introduction to Classical Mechanics",
            "Advanced Calculus and Multivariable Calculus",
            "Fundamentals of programming with Java",
            "Implementation and analysis of fundamental data structures",
            "Principles of database design and management",
            "Overview of operating system concepts",
            "Principles of software design and development",
            "Building web applications with HTML, CSS, and JavaScript",
            "Introduction to the principles of artificial intelligence",
            "Algorithms for machine learning"
    };
    private static final CourseCategory[] categories = CourseCategory.values();

    public CourseDTO createRandomCourseDTO() {
        String name = courseNames[random.nextInt(courseNames.length)];
        String description = descriptions[random.nextInt(descriptions.length)];
        int duration = 30 + random.nextInt(120);
        CourseCategory category = categories[random.nextInt(categories.length)];
        String academicYear = generateRandomAcademicYear();
        return new CourseDTO(name, description, duration, category, academicYear);
    }

    private String generateRandomAcademicYear() {
        int startYear = 2020 + random.nextInt(5);
        int endYear = startYear + 1;
        return startYear + "-" + endYear;
    }
}
