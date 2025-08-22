package com.schoolmgt.auth.fees.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class FeeStructureRequestDTO {
    
    @NotBlank(message = "Class ID is required")
    private String classId;
    
    @NotBlank(message = "Academic year is required")
    private String academicYear;
    
    @NotNull(message = "Effective date is required")
    private LocalDateTime effectiveDate;
    
    private LocalDateTime expiryDate;
    
    private String status = "ACTIVE";
    
    @NotEmpty(message = "Fee structure items are required")
    @Valid
    private List<FeeStructureItemDTO> feeItems;
}
