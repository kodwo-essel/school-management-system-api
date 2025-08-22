package com.schoolmgt.auth.fees.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeTypeResponseDTO {
    private String feeTypeId;
    private String name;
    private String category;
    private String description;
    private String status;
}
