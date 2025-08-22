package com.schoolmgt.auth.schoolmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// Response DTO (for fetching details)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolClassResponseDTO {
    private String classId; // "CLS-0001-0001"
    private String name; // "P1A", "P2B", "JHS1A", "JHS2B"
    private String schoolId;
    private String departmentId;
    private String departmentName; // Populated from department service
    private String academicYear; // "2024-2025"
    private String classTeacherId;
    private String classTeacherName; // Populated from teacher service
    private Integer capacity; // Maximum number of students
    private Integer currentStudents; // Current number of enrolled students
    private Double utilizationRate; // (currentStudents / capacity) * 100
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
