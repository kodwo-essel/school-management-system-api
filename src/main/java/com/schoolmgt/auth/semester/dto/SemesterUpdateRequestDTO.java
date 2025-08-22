package com.schoolmgt.auth.semester.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SemesterUpdateRequestDTO {
    
    @Size(max = 100, message = "Term name must not exceed 100 characters")
    private String name;

    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
