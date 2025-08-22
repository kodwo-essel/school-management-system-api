package com.schoolmgt.auth.schoolmanagement.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolReportDTO {
    private String schoolId;
    private String schoolName;
    private String principalName;
    private String academicYear;
    private SchoolStatisticsDTO statistics;
    private List<DepartmentSummary> departments;
    private List<ClassSummary> classes;
    private List<String> achievements;
    private List<String> challenges;
    private String overallPerformance;
    private FinancialSummary financialSummary;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DepartmentSummary {
        private String departmentId;
        private String departmentName;
        private String headOfDepartment;
        private Integer studentCount;
        private Integer teacherCount;
        private Double averageGrade;
        private String performanceRating;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ClassSummary {
        private String classId;
        private String className;
        private String department;
        private Integer studentCount;
        private String classTeacher;
        private Double averageGrade;
        private Double attendanceRate;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FinancialSummary {
        private Double totalRevenue;
        private Double totalExpenses;
        private Double outstandingFees;
        private Double scholarshipAmount;
        private String financialHealth; // "EXCELLENT", "GOOD", "AVERAGE", "POOR"
    }
}
