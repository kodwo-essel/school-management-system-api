package com.schoolmgt.auth.subjectmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectStatisticsDTO {
    private String subjectId;
    private String subjectName;
    private Integer totalClasses;
    private Integer totalStudents;
    private Integer totalTeachers;
    private Double averageGrade;
    private Double passRate;
    private String performanceRating; // "EXCELLENT", "GOOD", "AVERAGE", "POOR"
    private Integer creditHours;
    private Integer weeklyHours;
    private String difficulty;
    private String mostCommonGrade;
    private Double attendanceRate;
    private String popularityRating; // Based on enrollment numbers
}
