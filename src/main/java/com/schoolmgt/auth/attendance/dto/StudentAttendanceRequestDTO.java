package com.schoolmgt.auth.attendance.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class StudentAttendanceRequestDTO {
    private String studentId;
    private LocalDate date;

    private boolean isPresent;

    private String teacherId;
}
