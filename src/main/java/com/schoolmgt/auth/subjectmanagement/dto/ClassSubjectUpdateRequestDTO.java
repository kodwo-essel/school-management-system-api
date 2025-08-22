package com.schoolmgt.auth.subjectmanagement.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassSubjectUpdateRequestDTO {
    private String teacherId;
    private String status;
    private String schedule;
    private String classroom;
    
    @Min(value = 1, message = "Max students must be at least 1")
    private Integer maxStudents;
    
    private String assessmentSchedule;
    private String syllabusCoverage;
    private String notes;
    private String performanceRating;
}
