package com.schoolmgt.auth.schoolmanagement.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentClassEnrollmentResponseDTO {
    private Long id;
    private String studentId;
    private String classId;
    private String className;
    private String academicYear;
    private LocalDateTime enrollmentDate;
    private String status;
}
