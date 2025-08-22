package com.schoolmgt.auth.subjectmanagement.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectResponseDTO {
    private String subjectId; // e.g. "SUB-0001-0001"
    private String name; // "Mathematics", "English", "Science", "Social Studies"
    private String schoolId;
    private String departmentId;
    private String departmentName; // Populated from department service
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
