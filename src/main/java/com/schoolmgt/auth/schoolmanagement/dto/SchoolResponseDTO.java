package com.schoolmgt.auth.schoolmanagement.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for responding with school details
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolResponseDTO {

    private String schoolId;
    private String name;
    private String address;
    private String city;
    private String region;
    private String country;
    private String contactEmail;
    private String contactPhone;
    private String website;
    private String motto;
    private String logoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

