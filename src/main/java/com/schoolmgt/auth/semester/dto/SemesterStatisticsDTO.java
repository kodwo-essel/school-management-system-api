package com.schoolmgt.auth.semester.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SemesterStatisticsDTO {
    private String semesterId;
    private String semesterName;
    private String academicYear;
    private Integer totalStudents;
    private Integer totalClasses;
    private Integer totalSubjects;
    private Integer totalTeachers;
    private Double averageGrade;
    private Double attendanceRate;
    private String performanceRating; // "EXCELLENT", "GOOD", "AVERAGE", "POOR"
}
