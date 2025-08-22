package com.schoolmgt.auth.subjectmanagement.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassSubjectResponseDTO {
    private Long id;
    private String classSubjectId;
    private String classId;
    private String className; // Populated from class service
    private String subjectId;
    private String subjectName; // Populated from subject service
    private String teacherId;
    private String teacherName; // Populated from teacher service
    private String semesterId;
    private String semesterName; // Populated from semester service
    private String academicYear;
    private String status;
    private String schedule;
    private String classroom;
    private Integer maxStudents;
    private Integer currentStudents;
    private String assessmentSchedule;
    private String syllabusCoverage;
    private String notes;
    private Double averageGrade;
    private String performanceRating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}