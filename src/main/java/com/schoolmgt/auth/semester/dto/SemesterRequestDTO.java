package com.schoolmgt.auth.semester.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SemesterRequestDTO {

    @NotBlank(message = "Term name is required")
    @Size(max = 100, message = "Term name must not exceed 100 characters")
    private String name; // "Term 1", "Term 2", "Term 3"

    @NotBlank(message = "Academic year is required")
    private String academicYear; // "2024-2025"

    @NotBlank(message = "School ID is required")
    private String schoolId;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    private LocalDate startDate;
    private LocalDate endDate;

    private String coordinatorId;
    private String notes;
}
