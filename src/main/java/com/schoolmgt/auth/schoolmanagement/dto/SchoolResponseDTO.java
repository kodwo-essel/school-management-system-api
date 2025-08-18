package com.schoolmgt.auth.schoolmanagement.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for responding with school details
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolResponseDTO {

    private String schoolId;
    private String name;
    private String address;
    private String city;
    private String region;
    private String country;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

