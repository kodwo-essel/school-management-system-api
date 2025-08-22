package com.schoolmgt.auth.fees.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentFeeAssessmentRequestDTO {
    
    @NotBlank(message = "Student ID is required")
    private String studentId;
    
    @NotBlank(message = "Class ID is required")
    private String classId;
    
    @NotBlank(message = "Academic year is required")
    private String academicYear;
    
    @NotBlank(message = "Term is required")
    private String term;
    
    @NotBlank(message = "Fee structure ID is required")
    private String feeStructureId;
    
    @NotNull(message = "Due date is required")
    private LocalDateTime dueDate;
}
