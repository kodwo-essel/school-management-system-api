package com.schoolmgt.auth.fees.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeTypeRequestDTO {
    
    @NotBlank(message = "Fee type name is required")
    @Size(max = 100, message = "Fee type name must not exceed 100 characters")
    private String name;
    
    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category must not exceed 50 characters")
    private String category;
    
    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;
    
    private String status = "ACTIVE";
}
