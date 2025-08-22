package com.schoolmgt.auth.grades.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeStatisticsDTO {
    private String classSubjectId;
    private String subjectName;
    private String academicYear;
    private String term;
    private Integer totalStudents;
    private Double averageScore;
    private Double highestScore;
    private Double lowestScore;
    private Double standardDeviation;
    private Double passRate; // Percentage of students who passed
    private Map<String, Integer> gradeDistribution; // Grade letter -> count
    private Map<String, Double> gradePercentages; // Grade letter -> percentage
}
