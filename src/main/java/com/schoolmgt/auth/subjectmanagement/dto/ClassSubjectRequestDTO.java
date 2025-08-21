package com.schoolmgt.auth.subjectmanagement.dto;

import lombok.Data;

@Data
public class ClassSubjectRequestDTO {
    private String classId;   // which class (CLS-xxxx)
    private String subjectId; // which subject (SUB-xxxx)
    private String teacherId; // which teacher (TEA-xxxx)
    private String semesterId;
}
