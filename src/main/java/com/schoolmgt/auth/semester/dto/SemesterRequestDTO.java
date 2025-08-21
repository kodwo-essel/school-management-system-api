package com.schoolmgt.auth.semester.dto;


import lombok.Data;

@Data
public class SemesterRequestDTO {
    
    private String semesterId;

    private String name;

    private String academicYear;

    private String schoolId;
}
