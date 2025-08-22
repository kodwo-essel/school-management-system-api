package com.schoolmgt.auth.schoolmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentStatisticsDTO {
    private String departmentId;
    private String departmentName;
    private Integer totalStudents;
    private Integer totalTeachers;
    private Integer totalClasses;
    private Double averageClassSize;
    private String performanceRating; // "EXCELLENT", "GOOD", "AVERAGE", "POOR"
    private Double averageGrade;
}
