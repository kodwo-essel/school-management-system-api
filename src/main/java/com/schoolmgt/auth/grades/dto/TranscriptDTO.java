package com.schoolmgt.auth.grades.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TranscriptDTO {
    private String studentId;
    private String studentName;
    private String className;
    private String academicYear;
    private Map<String, List<GradeResponseDTO>> termGrades; // Term -> Grades
    private Map<String, Double> termAverages; // Term -> Average
    private Map<String, String> termOverallGrades; // Term -> Overall Grade
    private Double yearlyAverage;
    private String yearlyGrade;
    private String promotionStatus; // "PROMOTED", "REPEAT", "PROBATION"
}
