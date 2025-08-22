package com.schoolmgt.auth.subjectmanagement.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
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
public class SubjectRequestDTO {

    @NotBlank(message = "Subject name is required")
    @Size(max = 100, message = "Subject name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "School ID is required")
    private String schoolId;

    private String departmentId; // Which department this subject belongs to

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
}
