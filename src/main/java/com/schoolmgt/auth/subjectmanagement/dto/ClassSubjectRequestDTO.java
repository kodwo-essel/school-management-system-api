package com.schoolmgt.auth.subjectmanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassSubjectRequestDTO {

    @NotBlank(message = "Class ID is required")
    private String classId;   // which class (CLS-xxxx)

    @NotBlank(message = "Subject ID is required")
    private String subjectId; // which subject (SUB-xxxx)

    private String teacherId; // which teacher (TEA-xxxx)

    @NotBlank(message = "Semester ID is required")
    private String semesterId;

    @NotBlank(message = "Academic year is required")
    private String academicYear; // e.g., "2024-2025"

    @Builder.Default
    private String status = "ACTIVE";

    private String schedule; // JSON object with day/time schedule
    private String classroom; // Room/location where subject is taught

    @Min(value = 1, message = "Max students must be at least 1")
    private Integer maxStudents;

    private String assessmentSchedule; // JSON object with assessment dates
    private String notes; // Additional notes for this assignment
}
