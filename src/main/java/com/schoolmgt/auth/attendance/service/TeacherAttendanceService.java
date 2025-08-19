package com.schoolmgt.auth.attendance.service;

import com.schoolmgt.auth.attendance.dto.TeacherAttendanceRequestDTO;
import com.schoolmgt.auth.attendance.dto.TeacherAttendanceResponseDTO;
import com.schoolmgt.auth.attendance.entity.TeacherAttendance;
import com.schoolmgt.auth.base.service.BaseService;

public interface TeacherAttendanceService extends BaseService<TeacherAttendance>{
    TeacherAttendanceResponseDTO recordAttendance(TeacherAttendanceRequestDTO teacherAttendanceRequestDTO);
    
}
