package com.schoolmgt.auth.fees.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentFeeAssessmentResponseDTO {
    private String assessmentId;
    private String studentId;
    private String classId;
    private String academicYear;
    private String term;
    private String feeStructureId;
    private LocalDateTime assessmentDate;
    private LocalDateTime dueDate;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
    private String status;
    private List<StudentFeeAssessmentItemResponseDTO> assessmentItems;
}
