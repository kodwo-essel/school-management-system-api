package com.schoolmgt.auth.attendance.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.schoolmgt.auth.attendance.entity.TeacherAttendance;
import com.schoolmgt.auth.base.repository.BaseRepository;

public interface TeacherAttendanceRepository extends BaseRepository<TeacherAttendance>{
    Optional<TeacherAttendance> findByTeacherIdAndDate(String teacherId, LocalDate date);

    List<TeacherAttendance> findByTeacherId(String teacherId);

    List<TeacherAttendance> findByTeacherIdAndDateBetween(String teacherId, LocalDate startDate, LocalDate endDate);

    List<TeacherAttendance> findByDate(LocalDate date);

    List<TeacherAttendance> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT ta FROM TeacherAttendance ta WHERE ta.teacherId IN :teacherIds AND ta.date = :date")
    List<TeacherAttendance> findByTeacherIdsAndDate(@Param("teacherIds") List<String> teacherIds, @Param("date") LocalDate date);

    @Query("SELECT COUNT(ta) FROM TeacherAttendance ta WHERE ta.teacherId = :teacherId AND ta.isPresent = true AND ta.date BETWEEN :startDate AND :endDate")
    long countPresentDaysByTeacherAndDateRange(@Param("teacherId") String teacherId,
                                              @Param("startDate") LocalDate startDate,
                                              @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(ta) FROM TeacherAttendance ta WHERE ta.teacherId = :teacherId AND ta.isPresent = false AND ta.date BETWEEN :startDate AND :endDate")
    long countAbsentDaysByTeacherAndDateRange(@Param("teacherId") String teacherId,
                                             @Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);
}
