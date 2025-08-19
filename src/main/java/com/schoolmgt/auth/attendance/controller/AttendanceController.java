package com.schoolmgt.auth.attendance.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmgt.auth.attendance.dto.StudentAttendanceRequestDTO;
import com.schoolmgt.auth.attendance.dto.StudentAttendanceResponseDTO;
import com.schoolmgt.auth.attendance.dto.TeacherAttendanceRequestDTO;
import com.schoolmgt.auth.attendance.dto.TeacherAttendanceResponseDTO;
import com.schoolmgt.auth.attendance.service.StudentAttendanceService;
import com.schoolmgt.auth.attendance.service.TeacherAttendanceService;
import com.schoolmgt.auth.base.dto.ApiResponse;

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

    @PostMapping("/students")
    public ResponseEntity<ApiResponse<StudentAttendanceResponseDTO>> recordStudentAttendance( 
        @Valid @RequestBody StudentAttendanceRequestDTO dto) {
            StudentAttendanceResponseDTO attendance = studentAttendanceService.recordAttendance(dto);
            return ResponseEntity.ok(ApiResponse.success(attendance, "Attendance recorded successfully"));
    }

    @PostMapping("/teachers")
    public ResponseEntity<ApiResponse<TeacherAttendanceResponseDTO>> recordTeacherAttendance( 
        @Valid @RequestBody TeacherAttendanceRequestDTO dto) {
            TeacherAttendanceResponseDTO attendance = teacherAttendanceService.recordAttendance(dto);
            return ResponseEntity.ok(ApiResponse.success(attendance, "Attendance recorded successfully"));
    }
    
}
