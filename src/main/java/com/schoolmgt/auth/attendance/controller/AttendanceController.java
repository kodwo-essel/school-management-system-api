package com.schoolmgt.auth.attendance.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmgt.auth.attendance.dto.*;
import com.schoolmgt.auth.attendance.service.StudentAttendanceService;
import com.schoolmgt.auth.attendance.service.TeacherAttendanceService;
import com.schoolmgt.auth.base.dto.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@Tag(name = "Attendance Management", description = "Attendance Endpoints")
public class AttendanceController {

    private final StudentAttendanceService studentAttendanceService;
    private final TeacherAttendanceService teacherAttendanceService;

    // Student Attendance Endpoints
    @PostMapping("/students")
    @Operation(summary = "Record student attendance")
    public ResponseEntity<ApiResponse<StudentAttendanceResponseDTO>> recordStudentAttendance(
        @Valid @RequestBody StudentAttendanceRequestDTO dto) {
            StudentAttendanceResponseDTO attendance = studentAttendanceService.recordAttendance(dto);
            return ResponseEntity.ok(ApiResponse.success(attendance, "Attendance recorded successfully"));
    }

    @PostMapping("/students/bulk")
    @Operation(summary = "Record bulk student attendance")
    public ResponseEntity<ApiResponse<List<StudentAttendanceResponseDTO>>> recordBulkStudentAttendance(
            @Valid @RequestBody BulkStudentAttendanceRequestDTO dto) {
        List<StudentAttendanceResponseDTO> attendances = studentAttendanceService.recordBulkAttendance(dto);
        return ResponseEntity.ok(ApiResponse.success(attendances, "Bulk attendance recorded successfully"));
    }

    @GetMapping("/students/{studentId}/date/{date}")
    @Operation(summary = "Get student attendance by date")
    public ResponseEntity<ApiResponse<StudentAttendanceResponseDTO>> getStudentAttendanceByDate(
            @PathVariable String studentId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        StudentAttendanceResponseDTO attendance = studentAttendanceService.getStudentAttendanceByDate(studentId, date);
        return ResponseEntity.ok(ApiResponse.success(attendance, "Student attendance retrieved successfully"));
    }

    @GetMapping("/students/{studentId}/history")
    @Operation(summary = "Get student attendance history")
    public ResponseEntity<ApiResponse<List<StudentAttendanceResponseDTO>>> getStudentAttendanceHistory(
            @PathVariable String studentId) {
        List<StudentAttendanceResponseDTO> attendances = studentAttendanceService.getStudentAttendanceHistory(studentId);
        return ResponseEntity.ok(ApiResponse.success(attendances, "Student attendance history retrieved successfully"));
    }

    @GetMapping("/students/{studentId}/range")
    @Operation(summary = "Get student attendance by date range")
    public ResponseEntity<ApiResponse<List<StudentAttendanceResponseDTO>>> getStudentAttendanceByDateRange(
            @PathVariable String studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<StudentAttendanceResponseDTO> attendances = studentAttendanceService.getStudentAttendanceByDateRange(studentId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(attendances, "Student attendance range retrieved successfully"));
    }

    @GetMapping("/students/date/{date}")
    @Operation(summary = "Get all student attendance by date")
    public ResponseEntity<ApiResponse<List<StudentAttendanceResponseDTO>>> getAttendanceByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<StudentAttendanceResponseDTO> attendances = studentAttendanceService.getAttendanceByDate(date);
        return ResponseEntity.ok(ApiResponse.success(attendances, "Daily attendance retrieved successfully"));
    }

    @GetMapping("/students/absent/date/{date}")
    @Operation(summary = "Get absent students by date")
    public ResponseEntity<ApiResponse<List<StudentAttendanceResponseDTO>>> getAbsentStudents(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<StudentAttendanceResponseDTO> absentStudents = studentAttendanceService.getAbsentStudents(date);
        return ResponseEntity.ok(ApiResponse.success(absentStudents, "Absent students retrieved successfully"));
    }

    @GetMapping("/students/{studentId}/summary")
    @Operation(summary = "Get student attendance summary")
    public ResponseEntity<ApiResponse<AttendanceSummaryDTO>> getStudentAttendanceSummary(
            @PathVariable String studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        AttendanceSummaryDTO summary = studentAttendanceService.getStudentAttendanceSummary(studentId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(summary, "Student attendance summary retrieved successfully"));
    }

    @GetMapping("/class/{classId}/report/date/{date}")
    @Operation(summary = "Get class attendance report by date")
    public ResponseEntity<ApiResponse<ClassAttendanceReportDTO>> getClassAttendanceReport(
            @PathVariable String classId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        ClassAttendanceReportDTO report = studentAttendanceService.getClassAttendanceReport(classId, date);
        return ResponseEntity.ok(ApiResponse.success(report, "Class attendance report retrieved successfully"));
    }

    @PutMapping("/students/{studentId}/date/{date}")
    @Operation(summary = "Update student attendance")
    public ResponseEntity<ApiResponse<StudentAttendanceResponseDTO>> updateStudentAttendance(
            @PathVariable String studentId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam boolean isPresent,
            @RequestParam(required = false) String remarks) {
        StudentAttendanceResponseDTO attendance = studentAttendanceService.updateAttendance(studentId, date, isPresent, remarks);
        return ResponseEntity.ok(ApiResponse.success(attendance, "Student attendance updated successfully"));
    }

    @DeleteMapping("/students/{studentId}/date/{date}")
    @Operation(summary = "Delete student attendance")
    public ResponseEntity<ApiResponse<Void>> deleteStudentAttendance(
            @PathVariable String studentId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        studentAttendanceService.deleteAttendance(studentId, date);
        return ResponseEntity.ok(ApiResponse.success(null, "Student attendance deleted successfully"));
    }

    // Teacher Attendance Endpoints
    @PostMapping("/teachers")
    @Operation(summary = "Record teacher attendance")
    public ResponseEntity<ApiResponse<TeacherAttendanceResponseDTO>> recordTeacherAttendance(
        @Valid @RequestBody TeacherAttendanceRequestDTO dto) {
            TeacherAttendanceResponseDTO attendance = teacherAttendanceService.recordAttendance(dto);
            return ResponseEntity.ok(ApiResponse.success(attendance, "Teacher attendance recorded successfully"));
    }

    @GetMapping("/teachers/{teacherId}/date/{date}")
    @Operation(summary = "Get teacher attendance by date")
    public ResponseEntity<ApiResponse<TeacherAttendanceResponseDTO>> getTeacherAttendanceByDate(
            @PathVariable String teacherId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        TeacherAttendanceResponseDTO attendance = teacherAttendanceService.getTeacherAttendanceByDate(teacherId, date);
        return ResponseEntity.ok(ApiResponse.success(attendance, "Teacher attendance retrieved successfully"));
    }

    @GetMapping("/teachers/{teacherId}/history")
    @Operation(summary = "Get teacher attendance history")
    public ResponseEntity<ApiResponse<List<TeacherAttendanceResponseDTO>>> getTeacherAttendanceHistory(
            @PathVariable String teacherId) {
        List<TeacherAttendanceResponseDTO> attendances = teacherAttendanceService.getTeacherAttendanceHistory(teacherId);
        return ResponseEntity.ok(ApiResponse.success(attendances, "Teacher attendance history retrieved successfully"));
    }

    @GetMapping("/teachers/{teacherId}/summary")
    @Operation(summary = "Get teacher attendance summary")
    public ResponseEntity<ApiResponse<TeacherAttendanceSummaryDTO>> getTeacherAttendanceSummary(
            @PathVariable String teacherId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        TeacherAttendanceSummaryDTO summary = teacherAttendanceService.getTeacherAttendanceSummary(teacherId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(summary, "Teacher attendance summary retrieved successfully"));
    }

    @GetMapping("/teachers/date/{date}")
    @Operation(summary = "Get all teacher attendance by date")
    public ResponseEntity<ApiResponse<List<TeacherAttendanceResponseDTO>>> getAllTeacherAttendanceByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<TeacherAttendanceResponseDTO> attendances = teacherAttendanceService.getAllTeacherAttendanceByDate(date);
        return ResponseEntity.ok(ApiResponse.success(attendances, "Daily teacher attendance retrieved successfully"));
    }

    @GetMapping("/teachers/absent/date/{date}")
    @Operation(summary = "Get absent teachers by date")
    public ResponseEntity<ApiResponse<List<TeacherAttendanceResponseDTO>>> getAbsentTeachers(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<TeacherAttendanceResponseDTO> absentTeachers = teacherAttendanceService.getAbsentTeachers(date);
        return ResponseEntity.ok(ApiResponse.success(absentTeachers, "Absent teachers retrieved successfully"));
    }

    @PutMapping("/teachers/{teacherId}/date/{date}")
    @Operation(summary = "Update teacher attendance")
    public ResponseEntity<ApiResponse<TeacherAttendanceResponseDTO>> updateTeacherAttendance(
            @PathVariable String teacherId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam boolean isPresent,
            @RequestParam(required = false) String remarks) {
        TeacherAttendanceResponseDTO attendance = teacherAttendanceService.updateTeacherAttendance(teacherId, date, isPresent, remarks);
        return ResponseEntity.ok(ApiResponse.success(attendance, "Teacher attendance updated successfully"));
    }

    @DeleteMapping("/teachers/{teacherId}/date/{date}")
    @Operation(summary = "Delete teacher attendance")
    public ResponseEntity<ApiResponse<Void>> deleteTeacherAttendance(
            @PathVariable String teacherId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        teacherAttendanceService.deleteTeacherAttendance(teacherId, date);
        return ResponseEntity.ok(ApiResponse.success(null, "Teacher attendance deleted successfully"));
    }
}
