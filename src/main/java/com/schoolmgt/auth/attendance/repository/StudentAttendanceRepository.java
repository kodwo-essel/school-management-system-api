package com.schoolmgt.auth.attendance.repository;

import java.time.LocalDate;

import com.schoolmgt.auth.attendance.entity.StudentAttendance;
import com.schoolmgt.auth.base.repository.BaseRepository;

public interface StudentAttendanceRepository extends BaseRepository<StudentAttendance>{
    StudentAttendance findByStudentIdAndDate(String studentId, LocalDate date);
}
