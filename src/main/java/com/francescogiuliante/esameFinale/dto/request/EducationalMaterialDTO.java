package com.francescogiuliante.esameFinale.dto.request;

import com.francescogiuliante.esameFinale.enums.EducationalMaterialType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationalMaterialDTO {

    private Long courseId;
    private Long teacherId;
    private String title;
    private EducationalMaterialType type;
    private String url;
    private java.time.LocalDate publicationDate;
}
