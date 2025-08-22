package com.schoolmgt.auth.grades.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeResponseDTO {

    private Long id;
    private String studentId;
    private String classSubjectId;

    private Double sbaScore;
    private Double examScore;

    private Double sbaPercentage;
    private Double examPercentage;

    private Double totalScore;
    private String gradeLetter;

    private String semesterId;
    private String academicYear;
    private String term;
    private String teacherId;
    private String remarks;
}

