package com.schoolmgt.auth.semester.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SemesterResponseDTO {

    private String semesterId;
    private String name; // "Term 1", "Term 2", "Term 3"
    private String academicYear; // "2024-2025"
    private String schoolId;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isCurrentSemester;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
