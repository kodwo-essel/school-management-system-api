package com.schoolmgt.auth.schoolmanagement.dto;

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
public class AcademicDepartmentRequestDTO {

    @NotBlank(message = "Department name is required")
    @Size(max = 100, message = "Department name must not exceed 100 characters")
    private String name; // "Pre-school", "Lower Primary", "Upper Primary", "JHS"

    @NotBlank(message = "School ID is required")
    private String schoolId;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    private String headOfDepartment; // Teacher ID who heads the department
}
