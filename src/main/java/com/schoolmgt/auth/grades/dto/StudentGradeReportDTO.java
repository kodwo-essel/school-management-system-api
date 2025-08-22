package com.schoolmgt.auth.grades.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentGradeReportDTO {
    private String studentId;
    private String studentName;
    private String academicYear;
    private String term;
    private List<GradeResponseDTO> grades;
    private Double overallAverage;
    private String overallGrade;
    private Double gpa;
    private Integer totalSubjects;
    private Integer passedSubjects;
    private Integer failedSubjects;
    private String academicStatus; // "EXCELLENT", "GOOD", "AVERAGE", "POOR"
}
