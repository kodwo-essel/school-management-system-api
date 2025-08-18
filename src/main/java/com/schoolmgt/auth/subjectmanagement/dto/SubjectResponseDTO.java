package com.schoolmgt.auth.subjectmanagement.dto;

import lombok.Data;

@Data
public class SubjectResponseDTO {
    private String subjectId; // e.g. "SUB-0001-0001"
    private String name;
    private String schoolId;
}
