package com.francescogiuliante.esameFinale.service;

import com.francescogiuliante.esameFinale.dto.request.CourseTeacherDTO;
import com.francescogiuliante.esameFinale.dto.response.CourseTeacherResponseDTO;
import com.francescogiuliante.esameFinale.mapper.CourseTeacherMapper;
import com.francescogiuliante.esameFinale.model.Course;
import com.francescogiuliante.esameFinale.model.CourseTeacher;
import com.francescogiuliante.esameFinale.model.Teacher;
import com.francescogiuliante.esameFinale.repository.CourseRepository;
import com.francescogiuliante.esameFinale.repository.CourseTeacherRepository;
import com.francescogiuliante.esameFinale.repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CourseTeacherService {

    @Autowired
    private CourseTeacherRepository courseTeacherRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseTeacherMapper courseTeacherMapper;

    public List<CourseTeacherResponseDTO> getAllCourseTeachers() {
        log.info("Fetching all CourseTeacher assignments.");
        List<CourseTeacher> courseTeachers = courseTeacherRepository.findAll();
        List<CourseTeacherResponseDTO> responseDTOs = new ArrayList<>();
        for (CourseTeacher courseTeacher : courseTeachers) {
            responseDTOs.add(courseTeacherMapper.toResponseDTO(courseTeacher));
        }
        return responseDTOs;
    }

    public Optional<CourseTeacherResponseDTO> getCourseTeacherById(Long id) {
        log.info("Fetching CourseTeacher assignment with ID: {}", id);
        Optional<CourseTeacher> courseTeacherOptional = courseTeacherRepository.findById(id);
        return courseTeacherOptional.map(courseTeacherMapper::toResponseDTO);
    }

    public CourseTeacherResponseDTO createCourseTeacher(CourseTeacherDTO courseTeacherDTO) {
        log.info("Creating a new CourseTeacher assignment: {}", courseTeacherDTO);
        CourseTeacher courseTeacher = courseTeacherMapper.toEntity(courseTeacherDTO);

        Optional<Course> courseOptional = courseRepository.findById(courseTeacherDTO.getCourseId());
        if (courseOptional.isEmpty()) {
            log.warn("Course with ID {} not found.", courseTeacherDTO.getCourseId());
            return null;
        }
        courseTeacher.setCourse(courseOptional.get());

        Optional<Teacher> teacherOptional = teacherRepository.findById(courseTeacherDTO.getTeacherId());
        if (teacherOptional.isEmpty()) {
            log.warn("Teacher with ID {} not found.", courseTeacherDTO.getTeacherId());
            return null;
        }
        courseTeacher.setTeacher(teacherOptional.get());

        CourseTeacher savedCourseTeacher = courseTeacherRepository.save(courseTeacher);
        return courseTeacherMapper.toResponseDTO(savedCourseTeacher);
    }

    public Optional<CourseTeacherResponseDTO> updateCourseTeacher(Long id, CourseTeacherDTO courseTeacherDTO) {
        log.info("Updating CourseTeacher assignment with ID {}: {}", id, courseTeacherDTO);
        Optional<CourseTeacher> existingCourseTeacherOptional = courseTeacherRepository.findById(id);
        if (existingCourseTeacherOptional.isPresent()) {
            CourseTeacher updatedCourseTeacher = courseTeacherMapper.toEntity(courseTeacherDTO);
            updatedCourseTeacher.setId(id);

            Optional<Course> courseOptional = courseRepository.findById(courseTeacherDTO.getCourseId());
            if (courseOptional.isEmpty()) {
                log.warn("Course with ID {} not found.", courseTeacherDTO.getCourseId());
                return Optional.empty();
            }
            updatedCourseTeacher.setCourse(courseOptional.get());

            Optional<Teacher> teacherOptional = teacherRepository.findById(courseTeacherDTO.getTeacherId());
            if (teacherOptional.isEmpty()) {
                log.warn("Teacher with ID {} not found.", courseTeacherDTO.getTeacherId());
                return Optional.empty();
            }
            updatedCourseTeacher.setTeacher(teacherOptional.get());

            CourseTeacher savedCourseTeacher = courseTeacherRepository.save(updatedCourseTeacher);
            return Optional.of(courseTeacherMapper.toResponseDTO(savedCourseTeacher));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteCourseTeacher(Long id) {
        log.info("Deleting CourseTeacher assignment with ID: {}", id);
        if (courseTeacherRepository.existsById(id)) {
            courseTeacherRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<CourseTeacherResponseDTO> getCourseTeachersByCourseId(Long courseId) {
        log.info("Fetching CourseTeacher assignments for Course ID: {}", courseId);
        List<CourseTeacher> courseTeachers = courseTeacherRepository.findByCourse_Id(courseId);
        List<CourseTeacherResponseDTO> responseDTOs = new ArrayList<>();
        for (CourseTeacher courseTeacher : courseTeachers) {
            responseDTOs.add(courseTeacherMapper.toResponseDTO(courseTeacher));
        }
        return responseDTOs;
    }

    public List<CourseTeacherResponseDTO> getCourseTeachersByTeacherId(Long teacherId) {
        log.info("Fetching CourseTeacher assignments for Teacher ID: {}", teacherId);
        List<CourseTeacher> courseTeachers = courseTeacherRepository.findByTeacher_Id(teacherId);
        List<CourseTeacherResponseDTO> responseDTOs = new ArrayList<>();
        for (CourseTeacher courseTeacher : courseTeachers) {
            responseDTOs.add(courseTeacherMapper.toResponseDTO(courseTeacher));
        }
        return responseDTOs;
    }

    public List<CourseTeacherResponseDTO> getCourseTeachersByStartDate(LocalDate startDate) {
        log.info("Fetching CourseTeacher assignments with start date: {}", startDate);
        List<CourseTeacher> courseTeachers = courseTeacherRepository.findByStartDate(startDate);
        List<CourseTeacherResponseDTO> responseDTOs = new ArrayList<>();
        for (CourseTeacher courseTeacher : courseTeachers) {
            responseDTOs.add(courseTeacherMapper.toResponseDTO(courseTeacher));
        }
        return responseDTOs;
    }
}