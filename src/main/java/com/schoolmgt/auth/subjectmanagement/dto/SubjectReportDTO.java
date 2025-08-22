package com.schoolmgt.auth.subjectmanagement.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectReportDTO {
    private String subjectId;
    private String subjectName;
    private String coordinatorName;
    private String academicYear;
    private SubjectStatisticsDTO statistics;
    private List<ClassSummary> classes;
    private List<TeacherSummary> teachers;
    private String overallPerformance;
    private List<String> achievements;
    private List<String> challenges;
    private SyllabusProgress syllabusProgress;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ClassSummary {
        private String classId;
        private String className;
        private String teacherName;
        private Integer studentCount;
        private Double averageGrade;
        private Double attendanceRate;
        private String performanceRating;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TeacherSummary {
        private String teacherId;
        private String teacherName;
        private Integer classesTeaching;
        private Integer totalStudents;
        private Double averageClassGrade;
        private String performanceRating;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SyllabusProgress {
        private Double overallProgress;
        private List<String> completedTopics;
        private List<String> pendingTopics;
        private String expectedCompletionDate;
    }
}
