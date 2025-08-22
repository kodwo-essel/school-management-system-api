package com.schoolmgt.auth.schoolmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolStatisticsDTO {
    private String schoolId;
    private String schoolName;
    private Integer totalStudents;
    private Integer totalTeachers;
    private Integer totalDepartments;
    private Integer totalClasses;
    private Double studentTeacherRatio;
    private Double averageClassSize;
    private String performanceRating; // "EXCELLENT", "GOOD", "AVERAGE", "POOR"
    private Double overallGPA;
    private Double attendanceRate;
    private Integer graduatesLastYear;
    private String accreditationStatus;
    private Double capacityUtilization; // Current students / Max students * 100
}
