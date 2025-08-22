package com.schoolmgt.auth.schoolmanagement.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcademicDepartmentResponseDTO {
    private String departmentId;
    private String name; // "Pre-school", "Lower Primary", "Upper Primary", "JHS"
    private String schoolId;
    private String description;
    private String headOfDepartment; // Teacher ID
    private String headOfDepartmentName; // Populated from teacher service
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
