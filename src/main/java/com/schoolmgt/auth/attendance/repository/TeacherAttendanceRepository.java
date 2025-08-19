package com.schoolmgt.auth.attendance.repository;

import java.time.LocalDate;

import com.schoolmgt.auth.attendance.entity.TeacherAttendance;
import com.schoolmgt.auth.base.repository.BaseRepository;

public interface TeacherAttendanceRepository extends BaseRepository<TeacherAttendance>{
    TeacherAttendance findByTeacherIdAndDate(String teacherId, LocalDate date);
    
}
