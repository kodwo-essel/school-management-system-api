package com.schoolmgt.auth.schoolmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolSearchDTO {
    private String name;
    private String city;
    private String region;
    private String country;
    private String status;
    private String schoolType;
    private String educationLevel;
    private String accreditationStatus;
    private Integer minStudents;
    private Integer maxStudents;
    private Integer establishedAfter;
    private Integer establishedBefore;
}
