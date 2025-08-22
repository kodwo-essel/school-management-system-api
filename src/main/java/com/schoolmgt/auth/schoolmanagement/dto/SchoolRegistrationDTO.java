package com.schoolmgt.auth.schoolmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for registering a new school
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolRegistrationDTO {

    @NotBlank(message = "School name is required")
    @Size(max = 100, message = "School name must not exceed 100 characters")
    private String name;

    private String address;
    private String city;
    private String region;
    private String country;

    @Email(message = "Invalid email format")
    private String contactEmail;

    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Invalid phone number format")
    private String contactPhone;

    private String website;
    private String motto;
    private String logoUrl;
}
