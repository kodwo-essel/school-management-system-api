package com.schoolmgt.auth.schoolmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentClassEnrollmentRequestDTO {
    
    @NotBlank(message = "Student ID is required")
    private String studentId;
    
    @NotBlank(message = "Class ID is required")
    private String classId;
    
    @NotBlank(message = "Academic year is required")
    private String academicYear;
    
    private String status = "ACTIVE";
}
