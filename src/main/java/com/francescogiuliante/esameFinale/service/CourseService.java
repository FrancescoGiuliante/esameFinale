package com.francescogiuliante.esameFinale.service;

import com.francescogiuliante.esameFinale.dto.request.CourseDTO;
import com.francescogiuliante.esameFinale.dto.response.CourseResponseDTO;
import com.francescogiuliante.esameFinale.enums.CourseCategory;
import com.francescogiuliante.esameFinale.mapper.CourseMapper;
import com.francescogiuliante.esameFinale.model.Course;
import com.francescogiuliante.esameFinale.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    public List<CourseResponseDTO> getAllCourses() {
        log.info("Fetching all courses.");
        List<Course> courses = courseRepository.findAll();
        List<CourseResponseDTO> responseDTOs = new ArrayList<>();
        for (Course course : courses) {
            responseDTOs.add(courseMapper.toResponseDTO(course));
        }
        return responseDTOs;
    }

    public Optional<CourseResponseDTO> getCourseById(Long id) {
        log.info("Fetching course with ID: {}", id);
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            return Optional.of(courseMapper.toResponseDTO(courseOptional.get()));
        } else {
            return Optional.empty();
        }
    }

    public CourseResponseDTO createCourse(CourseDTO courseDTO) {
        log.info("Creating a new course: {}", courseDTO);
        Course course = courseMapper.toEntity(courseDTO);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toResponseDTO(savedCourse);
    }

    public Optional<CourseResponseDTO> updateCourse(Long id, CourseDTO courseDTO) {
        log.info("Updating course with ID {}: {}", id, courseDTO);
        Optional<Course> existingCourseOptional = courseRepository.findById(id);
        if (existingCourseOptional.isPresent()) {
            Course existingCourse = existingCourseOptional.get();
            Course updatedCourse = courseMapper.toEntity(courseDTO);
            updatedCourse.setId(id);
            existingCourse.setName(updatedCourse.getName());
            existingCourse.setDescription(updatedCourse.getDescription());
            existingCourse.setDuration(updatedCourse.getDuration());
            existingCourse.setCategory(updatedCourse.getCategory());
            existingCourse.setAcademicYear(updatedCourse.getAcademicYear());
            Course savedCourse = courseRepository.save(existingCourse);
            return Optional.of(courseMapper.toResponseDTO(savedCourse));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteCourse(Long id) {
        log.info("Deleting course with ID: {}", id);
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<CourseResponseDTO> getCoursesByCategory(String category) {
        log.info("Fetching courses by category: {}", category);
        try {
            CourseCategory courseCategory = CourseCategory.valueOf(category.toUpperCase());
            List<Course> courses = courseRepository.findByCategory(courseCategory);
            List<CourseResponseDTO> responseDTOs = new ArrayList<>();
            for (Course course : courses) {
                responseDTOs.add(courseMapper.toResponseDTO(course));
            }
            return responseDTOs;
        } catch (IllegalArgumentException e) {
            log.warn("Invalid course category: {}", category);
            return new ArrayList<>();
        }
    }

    public List<CourseResponseDTO> getCoursesByAcademicYear(String academicYear) {
        log.info("Fetching courses by academic year: {}", academicYear);
        List<Course> courses = courseRepository.findByAcademicYear(academicYear);
        List<CourseResponseDTO> responseDTOs = new ArrayList<>();
        for (Course course : courses) {
            responseDTOs.add(courseMapper.toResponseDTO(course));
        }
        return responseDTOs;
    }
}