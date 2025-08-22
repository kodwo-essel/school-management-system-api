package com.schoolmgt.auth.fees.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeStructureItemResponseDTO {
    private Long id;
    private String feeTypeId;
    private BigDecimal amount;
    private String currency;
    private Boolean mandatory;
    private String description;
}
