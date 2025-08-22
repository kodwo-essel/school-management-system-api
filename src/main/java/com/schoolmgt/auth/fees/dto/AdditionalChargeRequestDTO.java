package com.schoolmgt.auth.fees.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdditionalChargeRequestDTO {
    
    @NotBlank(message = "Student ID is required")
    private String studentId;
    
    @NotBlank(message = "Charge type is required")
    private String chargeType;
    
    private String feeTypeId;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amount;
    
    private String description;
    
    @NotNull(message = "Due date is required")
    private LocalDateTime dueDate;
    
    private String referenceId;
    
    @NotBlank(message = "Created by is required")
    private String createdBy;
}
