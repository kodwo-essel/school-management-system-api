package com.schoolmgt.auth.semester.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SemesterReportDTO {
    private String semesterId;
    private String semesterName;
    private String academicYear;
    private String coordinatorName;
    private SemesterStatisticsDTO statistics;
    private List<ClassSummary> classes;
    private List<SubjectSummary> subjects;
    private List<String> achievements;
    private List<String> challenges;
    private String overallPerformance;
    private AcademicProgress academicProgress;
    
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
        private Double attendanceRate;
        private String performanceRating;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SubjectSummary {
        private String subjectId;
        private String subjectName;
        private Integer totalClasses;
        private Integer totalStudents;
        private Double averageGrade;
        private String performanceRating;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AcademicProgress {
        private Double syllabusCompletionRate;
        private Integer assessmentsCompleted;
        private Integer assessmentsPending;
        private String expectedCompletionDate;
        private List<String> upcomingMilestones;
    }
}
