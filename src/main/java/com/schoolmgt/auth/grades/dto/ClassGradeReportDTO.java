package com.schoolmgt.auth.grades.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassGradeReportDTO {
    private String classSubjectId;
    private String subjectName;
    private String className;
    private String academicYear;
    private String term;
    private Integer totalStudents;
    private Double classAverage;
    private Double highestScore;
    private Double lowestScore;
    private String topStudentId;
    private List<GradeDistribution> gradeDistribution;
    private List<StudentGradeSummary> studentGrades;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GradeDistribution {
        private String gradeLetter;
        private Integer count;
        private Double percentage;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StudentGradeSummary {
        private String studentId;
        private String studentName;
        private Double totalScore;
        private String gradeLetter;
        private Integer position;
    }
}
