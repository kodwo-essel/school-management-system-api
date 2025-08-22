package com.schoolmgt.auth.attendance.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceSummaryDTO {
    private String studentId;
    private String studentName;
    private LocalDate startDate;
    private LocalDate endDate;
    private long totalDays;
    private long presentDays;
    private long absentDays;
    private double attendancePercentage;
    private String status; // "EXCELLENT", "GOOD", "AVERAGE", "POOR"
}
