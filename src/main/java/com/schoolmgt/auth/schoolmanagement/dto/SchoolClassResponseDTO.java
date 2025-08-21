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
    private String classId;
    private String name;
    private String departmentId;
    private String academicYear;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
