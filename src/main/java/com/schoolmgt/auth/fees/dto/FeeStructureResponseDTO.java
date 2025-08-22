package com.schoolmgt.auth.fees.dto;

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
public class FeeStructureResponseDTO {
    private String feeStructureId;
    private String classId;
    private String academicYear;
    private Integer version;
    private LocalDateTime effectiveDate;
    private LocalDateTime expiryDate;
    private String status;
    private List<FeeStructureItemResponseDTO> feeItems;
}
