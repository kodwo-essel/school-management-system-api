package com.schoolmgt.auth.fees.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdditionalChargeResponseDTO {
    private String chargeId;
    private String studentId;
    private String chargeType;
    private String feeTypeId;
    private BigDecimal amount;
    private String description;
    private LocalDateTime chargeDate;
    private LocalDateTime dueDate;
    private String status;
    private String referenceId;
    private String createdBy;
}
