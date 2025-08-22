package com.schoolmgt.auth.attendance.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassAttendanceReportDTO {
    private String classId;
    private String className;
    private LocalDate date;
    private int totalStudents;
    private int presentStudents;
    private int absentStudents;
    private double attendancePercentage;
    private List<StudentAttendanceResponseDTO> studentAttendances;
}
