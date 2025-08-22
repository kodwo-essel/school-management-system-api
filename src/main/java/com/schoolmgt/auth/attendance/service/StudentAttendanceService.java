package com.schoolmgt.auth.attendance.service;

import java.time.LocalDate;
import java.util.List;


import com.schoolmgt.auth.attendance.dto.AttendanceSummaryDTO;
import com.schoolmgt.auth.attendance.dto.BulkStudentAttendanceRequestDTO;
import com.schoolmgt.auth.attendance.dto.ClassAttendanceReportDTO;
import com.schoolmgt.auth.attendance.dto.StudentAttendanceRequestDTO;
import com.schoolmgt.auth.attendance.dto.StudentAttendanceResponseDTO;
import com.schoolmgt.auth.attendance.entity.StudentAttendance;
import com.schoolmgt.auth.base.service.BaseService;

public interface StudentAttendanceService extends BaseService<StudentAttendance>{

    StudentAttendanceResponseDTO recordAttendance(StudentAttendanceRequestDTO studentAttendanceRequestDTO);

    List<StudentAttendanceResponseDTO> recordBulkAttendance(BulkStudentAttendanceRequestDTO bulkRequestDTO);

    StudentAttendanceResponseDTO getStudentAttendanceByDate(String studentId, LocalDate date);

    List<StudentAttendanceResponseDTO> getStudentAttendanceHistory(String studentId);

    List<StudentAttendanceResponseDTO> getStudentAttendanceByDateRange(String studentId, LocalDate startDate, LocalDate endDate);

    List<StudentAttendanceResponseDTO> getAttendanceByDate(LocalDate date);

    List<StudentAttendanceResponseDTO> getAttendanceByDateRange(LocalDate startDate, LocalDate endDate);

    ClassAttendanceReportDTO getClassAttendanceReport(String classId, LocalDate date);

    List<ClassAttendanceReportDTO> getClassAttendanceReportByDateRange(String classId, LocalDate startDate, LocalDate endDate);

    AttendanceSummaryDTO getStudentAttendanceSummary(String studentId, LocalDate startDate, LocalDate endDate);

    List<AttendanceSummaryDTO> getClassAttendanceSummary(String classId, LocalDate startDate, LocalDate endDate);

    List<StudentAttendanceResponseDTO> getAbsentStudents(LocalDate date);

    List<StudentAttendanceResponseDTO> getAbsentStudentsByDateRange(LocalDate startDate, LocalDate endDate);

    double calculateAttendancePercentage(String studentId, LocalDate startDate, LocalDate endDate);

    StudentAttendanceResponseDTO updateAttendance(String studentId, LocalDate date, boolean isPresent, String remarks);

    void deleteAttendance(String studentId, LocalDate date);
}
