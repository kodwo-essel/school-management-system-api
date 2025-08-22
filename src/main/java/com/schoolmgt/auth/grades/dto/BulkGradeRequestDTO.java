package com.schoolmgt.auth.grades.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulkGradeRequestDTO {
    
    @NotBlank(message = "Class subject ID is required")
    private String classSubjectId;
    
    @NotBlank(message = "Semester ID is required")
    private String semesterId;
    
    @NotBlank(message = "Academic year is required")
    private String academicYear;
    
    @NotBlank(message = "Term is required")
    private String term;
    
    @NotBlank(message = "Teacher ID is required")
    private String teacherId;
    
    @NotEmpty(message = "Grade records cannot be empty")
    @Valid
    private List<StudentGradeRecord> gradeRecords;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StudentGradeRecord {
        private String studentId;
        private Double sbaScore;
        private Double examScore;
        private Double sbaPercentage;
        private Double examPercentage;
        private String remarks;
    }
}
