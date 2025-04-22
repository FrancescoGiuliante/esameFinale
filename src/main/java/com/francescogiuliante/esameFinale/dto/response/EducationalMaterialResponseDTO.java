package com.francescogiuliante.esameFinale.dto.response;

import com.francescogiuliante.esameFinale.enums.EducationalMaterialType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationalMaterialResponseDTO {

    private String courseName;
    private String teacherName;
    private String title;
    private EducationalMaterialType type;
    private String url;
    private java.time.LocalDate publicationDate;
}
