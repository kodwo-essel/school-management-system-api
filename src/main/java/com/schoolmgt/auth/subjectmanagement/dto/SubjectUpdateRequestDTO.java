package com.schoolmgt.auth.subjectmanagement.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectUpdateRequestDTO {
    
    @Size(max = 100, message = "Subject name must not exceed 100 characters")
    private String name;

    private String departmentId;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
}
