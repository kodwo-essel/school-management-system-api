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
public class DepartmentReportDTO {
    private String departmentId;
    private String departmentName;
    private String headOfDepartmentName;
    private String academicYear;
    private DepartmentStatisticsDTO statistics;
    private List<ClassSummary> classes;
    private List<TeacherSummary> teachers;
    private List<SubjectSummary> subjects;
    private String overallPerformance;
    private List<String> achievements;
    private List<String> challenges;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ClassSummary {
        private String classId;
        private String className;
        private Integer studentCount;
        private String classTeacher;
        private Double averageGrade;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TeacherSummary {
        private String teacherId;
        private String teacherName;
        private String specialization;
        private Integer yearsOfExperience;
        private String qualification;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SubjectSummary {
        private String subjectId;
        private String subjectName;
        private String teacherId;
        private Integer totalStudents;
        private Double averageGrade;
    }
}
