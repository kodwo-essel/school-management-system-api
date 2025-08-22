package com.schoolmgt.auth.attendance.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulkStudentAttendanceRequestDTO {
    
    @NotEmpty(message = "Student attendance records cannot be empty")
    private List<StudentAttendanceRecord> attendanceRecords;
    
    @NotNull(message = "Date is required")
    private LocalDate date;
    
    private String teacherId;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StudentAttendanceRecord {
        private String studentId;
        private boolean isPresent;
        private String remarks;
    }
}
