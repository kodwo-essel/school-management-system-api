package com.schoolmgt.auth.grades.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeRequestDTO {

    @NotBlank(message = "Student ID is required")
    private String studentId;

    @NotBlank(message = "Class subject ID is required")
    private String classSubjectId;

    @DecimalMin(value = "0.0", message = "SBA score must be at least 0")
    @DecimalMax(value = "100.0", message = "SBA score must not exceed 100")
    private Double sbaScore;

    @DecimalMin(value = "0.0", message = "Exam score must be at least 0")
    @DecimalMax(value = "100.0", message = "Exam score must not exceed 100")
    private Double examScore;

    @DecimalMin(value = "0.0", message = "SBA percentage must be at least 0")
    @DecimalMax(value = "100.0", message = "SBA percentage must not exceed 100")
    private Double sbaPercentage;

    @DecimalMin(value = "0.0", message = "Exam percentage must be at least 0")
    @DecimalMax(value = "100.0", message = "Exam percentage must not exceed 100")
    private Double examPercentage;

    private String semesterId;
    private String academicYear;
    private String term;
    private String teacherId;
    private String remarks;
}

