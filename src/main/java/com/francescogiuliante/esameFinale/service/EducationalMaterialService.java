package com.francescogiuliante.esameFinale.service;

import com.francescogiuliante.esameFinale.dto.request.EducationalMaterialDTO;
import com.francescogiuliante.esameFinale.dto.response.EducationalMaterialResponseDTO;
import com.francescogiuliante.esameFinale.enums.EducationalMaterialType;
import com.francescogiuliante.esameFinale.mapper.EducationalMaterialMapper;
import com.francescogiuliante.esameFinale.model.Course;
import com.francescogiuliante.esameFinale.model.EducationalMaterial;
import com.francescogiuliante.esameFinale.model.Teacher;
import com.francescogiuliante.esameFinale.repository.CourseRepository;
import com.francescogiuliante.esameFinale.repository.EducationalMaterialRepository;
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
public class EducationalMaterialService {

    @Autowired
    private EducationalMaterialRepository educationalMaterialRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private EducationalMaterialMapper educationalMaterialMapper;

    public List<EducationalMaterialResponseDTO> getAllEducationalMaterials() {
        log.info("Fetching all educational materials.");
        List<EducationalMaterial> educationalMaterials = educationalMaterialRepository.findAll();
        List<EducationalMaterialResponseDTO> responseDTOs = new ArrayList<>();
        for (EducationalMaterial material : educationalMaterials) {
            responseDTOs.add(educationalMaterialMapper.toResponseDTO(material));
        }
        return responseDTOs;
    }

    public Optional<EducationalMaterialResponseDTO> getEducationalMaterialById(Long id) {
        log.info("Fetching educational material with ID: {}", id);
        Optional<EducationalMaterial> materialOptional = educationalMaterialRepository.findById(id);
        return materialOptional.map(educationalMaterialMapper::toResponseDTO);
    }

    public EducationalMaterialResponseDTO createEducationalMaterial(EducationalMaterialDTO educationalMaterialDTO) {
        log.info("Creating a new educational material: {}", educationalMaterialDTO);
        EducationalMaterial material = educationalMaterialMapper.toEntity(educationalMaterialDTO);

        Optional<Course> courseOptional = courseRepository.findById(educationalMaterialDTO.getCourseId());
        if (courseOptional.isEmpty()) {
            log.warn("Course with ID {} not found.", educationalMaterialDTO.getCourseId());
            return null;
        }
        material.setCourse(courseOptional.get());

        Optional<Teacher> teacherOptional = teacherRepository.findById(educationalMaterialDTO.getTeacherId());
        if (teacherOptional.isEmpty()) {
            log.warn("Teacher with ID {} not found.", educationalMaterialDTO.getTeacherId());
            return null;
        }
        material.setTeacher(teacherOptional.get());

        EducationalMaterial savedMaterial = educationalMaterialRepository.save(material);
        return educationalMaterialMapper.toResponseDTO(savedMaterial);
    }

    public Optional<EducationalMaterialResponseDTO> updateEducationalMaterial(Long id, EducationalMaterialDTO educationalMaterialDTO) {
        log.info("Updating educational material with ID {}: {}", id, educationalMaterialDTO);
        Optional<EducationalMaterial> existingMaterialOptional = educationalMaterialRepository.findById(id);
        if (existingMaterialOptional.isPresent()) {
            EducationalMaterial updatedMaterial = educationalMaterialMapper.toEntity(educationalMaterialDTO);
            updatedMaterial.setId(id);

            Optional<Course> courseOptional = courseRepository.findById(educationalMaterialDTO.getCourseId());
            if (courseOptional.isEmpty()) {
                log.warn("Course with ID {} not found.", educationalMaterialDTO.getCourseId());
                return Optional.empty();
            }
            updatedMaterial.setCourse(courseOptional.get());

            Optional<Teacher> teacherOptional = teacherRepository.findById(educationalMaterialDTO.getTeacherId());
            if (teacherOptional.isEmpty()) {
                log.warn("Teacher with ID {} not found.", educationalMaterialDTO.getTeacherId());
                return Optional.empty();
            }
            updatedMaterial.setTeacher(teacherOptional.get());

            EducationalMaterial savedMaterial = educationalMaterialRepository.save(updatedMaterial);
            return Optional.of(educationalMaterialMapper.toResponseDTO(savedMaterial));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteEducationalMaterial(Long id) {
        log.info("Deleting educational material with ID: {}", id);
        if (educationalMaterialRepository.existsById(id)) {
            educationalMaterialRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<EducationalMaterialResponseDTO> getEducationalMaterialsByCourseId(Long courseId) {
        log.info("Fetching educational materials for Course ID: {}", courseId);
        List<EducationalMaterial> materials = educationalMaterialRepository.findByCourse_Id(courseId);
        List<EducationalMaterialResponseDTO> responseDTOs = new ArrayList<>();
        for (EducationalMaterial material : materials) {
            responseDTOs.add(educationalMaterialMapper.toResponseDTO(material));
        }
        return responseDTOs;
    }

    public List<EducationalMaterialResponseDTO> getEducationalMaterialsByTeacherId(Long teacherId) {
        log.info("Fetching educational materials for Teacher ID: {}", teacherId);
        List<EducationalMaterial> materials = educationalMaterialRepository.findByTeacher_Id(teacherId);
        List<EducationalMaterialResponseDTO> responseDTOs = new ArrayList<>();
        for (EducationalMaterial material : materials) {
            responseDTOs.add(educationalMaterialMapper.toResponseDTO(material));
        }
        return responseDTOs;
    }

    public List<EducationalMaterialResponseDTO> getEducationalMaterialsByType(EducationalMaterialType type) {
        log.info("Fetching educational materials by type: {}", type);
        List<EducationalMaterial> materials = educationalMaterialRepository.findByType(type);
        List<EducationalMaterialResponseDTO> responseDTOs = new ArrayList<>();
        for (EducationalMaterial material : materials) {
            responseDTOs.add(educationalMaterialMapper.toResponseDTO(material));
        }
        return responseDTOs;
    }

    public List<EducationalMaterialResponseDTO> getEducationalMaterialsByPublicationDate(LocalDate publicationDate) {
        log.info("Fetching educational materials by publication date: {}", publicationDate);
        List<EducationalMaterial> materials = educationalMaterialRepository.findByPublicationDate(publicationDate);
        List<EducationalMaterialResponseDTO> responseDTOs = new ArrayList<>();
        for (EducationalMaterial material : materials) {
            responseDTOs.add(educationalMaterialMapper.toResponseDTO(material));
        }
        return responseDTOs;
    }

    public List<EducationalMaterialResponseDTO> getEducationalMaterialsByTitleContaining(String title) {
        log.info("Fetching educational materials containing title: {}", title);
        List<EducationalMaterial> materials = educationalMaterialRepository.findByTitleContainingIgnoreCase(title);
        List<EducationalMaterialResponseDTO> responseDTOs = new ArrayList<>();
        for (EducationalMaterial material : materials) {
            responseDTOs.add(educationalMaterialMapper.toResponseDTO(material));
        }
        return responseDTOs;
    }
}