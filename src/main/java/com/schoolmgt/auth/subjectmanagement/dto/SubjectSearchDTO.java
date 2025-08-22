package com.schoolmgt.auth.subjectmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectSearchDTO {
    private String name;
    private String schoolId;
    private String departmentId;
    private String status;
    private String subjectType;
    private String educationLevel;
    private String difficulty;
    private String assessmentMethod;
    private String gradeScale;
    private Integer minCreditHours;
    private Integer maxCreditHours;
    private Integer minWeeklyHours;
    private Integer maxWeeklyHours;
    private Double minPassingGrade;
    private Double maxPassingGrade;
}
