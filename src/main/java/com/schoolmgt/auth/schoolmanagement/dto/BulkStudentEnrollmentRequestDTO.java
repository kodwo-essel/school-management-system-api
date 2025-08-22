package com.schoolmgt.auth.schoolmanagement.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulkStudentEnrollmentRequestDTO {
    
    @NotEmpty(message = "Student IDs list cannot be empty")
    private List<String> studentIds;
    
    @NotBlank(message = "Class ID is required")
    private String classId;
    
    @NotBlank(message = "Academic year is required")
    private String academicYear;
    
    private String status = "ACTIVE";
}
