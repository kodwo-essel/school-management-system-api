package com.schoolmgt.auth.grades.dto;

import lombok.Data;

@Data
public class GradeResponseDTO {

    private String studentId;
    private String classSubjectId;

    private Double sbaScore;
    private Double examScore;

    private Double sbaPercentage;
    private Double examPercentage;

    private Double totalScore;
    private String gradeLetter;
}

