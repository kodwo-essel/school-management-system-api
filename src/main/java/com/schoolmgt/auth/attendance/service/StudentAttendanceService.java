package com.schoolmgt.auth.attendance.service;

import com.schoolmgt.auth.attendance.dto.StudentAttendanceRequestDTO;
import com.schoolmgt.auth.attendance.dto.StudentAttendanceResponseDTO;
import com.schoolmgt.auth.attendance.entity.StudentAttendance;
import com.schoolmgt.auth.base.service.BaseService;

public interface StudentAttendanceService extends BaseService<StudentAttendance>{
    StudentAttendanceResponseDTO recordAttendance(StudentAttendanceRequestDTO studentAttendanceRequestDTO); 
}
