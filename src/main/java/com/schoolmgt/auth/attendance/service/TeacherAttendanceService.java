package com.schoolmgt.auth.attendance.service;

import java.time.LocalDate;
import java.util.List;

import com.schoolmgt.auth.attendance.dto.TeacherAttendanceRequestDTO;
import com.schoolmgt.auth.attendance.dto.TeacherAttendanceResponseDTO;
import com.schoolmgt.auth.attendance.dto.TeacherAttendanceSummaryDTO;
import com.schoolmgt.auth.attendance.entity.TeacherAttendance;
import com.schoolmgt.auth.base.service.BaseService;

public interface TeacherAttendanceService extends BaseService<TeacherAttendance>{

    TeacherAttendanceResponseDTO recordAttendance(TeacherAttendanceRequestDTO teacherAttendanceRequestDTO);

    TeacherAttendanceResponseDTO getTeacherAttendanceByDate(String teacherId, LocalDate date);

    List<TeacherAttendanceResponseDTO> getTeacherAttendanceHistory(String teacherId);

    List<TeacherAttendanceResponseDTO> getTeacherAttendanceByDateRange(String teacherId, LocalDate startDate, LocalDate endDate);

    List<TeacherAttendanceResponseDTO> getAllTeacherAttendanceByDate(LocalDate date);

    List<TeacherAttendanceResponseDTO> getAllTeacherAttendanceByDateRange(LocalDate startDate, LocalDate endDate);

    TeacherAttendanceSummaryDTO getTeacherAttendanceSummary(String teacherId, LocalDate startDate, LocalDate endDate);

    List<TeacherAttendanceSummaryDTO> getAllTeacherAttendanceSummary(LocalDate startDate, LocalDate endDate);

    List<TeacherAttendanceResponseDTO> getAbsentTeachers(LocalDate date);

    double calculateTeacherAttendancePercentage(String teacherId, LocalDate startDate, LocalDate endDate);

    TeacherAttendanceResponseDTO updateTeacherAttendance(String teacherId, LocalDate date, boolean isPresent, String remarks);

    void deleteTeacherAttendance(String teacherId, LocalDate date);
}
