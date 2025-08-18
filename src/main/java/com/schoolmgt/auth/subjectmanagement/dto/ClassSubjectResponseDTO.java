package com.schoolmgt.auth.subjectmanagement.dto;

import lombok.Data;

@Data
public class ClassSubjectResponseDTO {
    private Long id;
    private String classId;
    private String subjectId;
    private String teacherId;
}