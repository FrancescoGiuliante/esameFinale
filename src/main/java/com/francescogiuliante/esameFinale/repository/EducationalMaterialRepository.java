package com.francescogiuliante.esameFinale.repository;

import com.francescogiuliante.esameFinale.enums.EducationalMaterialType;
import com.francescogiuliante.esameFinale.model.EducationalMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EducationalMaterialRepository extends JpaRepository<EducationalMaterial, Long> {

    List<EducationalMaterial> findByCourse_Id(Long courseId);
    List<EducationalMaterial> findByTeacher_Id(Long teacherId);
    List<EducationalMaterial> findByType(EducationalMaterialType type);
    List<EducationalMaterial> findByPublicationDate(LocalDate publicationDate);
    List<EducationalMaterial> findByTitleContainingIgnoreCase(String title);
}