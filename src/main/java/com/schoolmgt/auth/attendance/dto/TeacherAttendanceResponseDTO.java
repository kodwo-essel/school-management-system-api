package com.schoolmgt.auth.attendance.dto;

import java.time.LocalDate;

import lombok.Data;


@Data
public class TeacherAttendanceResponseDTO {
    private String teacherId;
    
    private LocalDate date;

    private boolean isPresent;

    
}
