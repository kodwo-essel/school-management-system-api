package com.schoolmgt.auth.schoolmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for registering a new school
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolRegistrationDTO {

    @NotBlank(message = "School name is required")
    @Size(max = 100, message = "School name must not exceed 100 characters")
    private String name;

    private String address;
    private String city;
    private String region;
    private String country;
}
