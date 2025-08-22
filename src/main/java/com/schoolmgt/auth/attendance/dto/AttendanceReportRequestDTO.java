package com.schoolmgt.auth.attendance.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceReportRequestDTO {
    
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    
    @NotNull(message = "End date is required")
    private LocalDate endDate;
    
    private List<String> studentIds; // Optional: specific students
    
    private String classId; // Optional: specific class
    
    private String teacherId; // Optional: specific teacher
    
    private String reportType; // "SUMMARY", "DETAILED", "STATISTICS"
}
