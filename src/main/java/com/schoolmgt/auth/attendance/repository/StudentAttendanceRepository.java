package com.schoolmgt.auth.attendance.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.schoolmgt.auth.attendance.entity.StudentAttendance;
import com.schoolmgt.auth.base.repository.BaseRepository;

public interface StudentAttendanceRepository extends BaseRepository<StudentAttendance>{
    Optional<StudentAttendance> findByStudentIdAndDate(String studentId, LocalDate date);

    List<StudentAttendance> findByStudentId(String studentId);

    List<StudentAttendance> findByStudentIdAndDateBetween(String studentId, LocalDate startDate, LocalDate endDate);

    List<StudentAttendance> findByDate(LocalDate date);

    List<StudentAttendance> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<StudentAttendance> findByTeacherId(String teacherId);

    List<StudentAttendance> findByTeacherIdAndDate(String teacherId, LocalDate date);

    List<StudentAttendance> findByTeacherIdAndDateBetween(String teacherId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT sa FROM StudentAttendance sa WHERE sa.studentId IN :studentIds AND sa.date = :date")
    List<StudentAttendance> findByStudentIdsAndDate(@Param("studentIds") List<String> studentIds, @Param("date") LocalDate date);

    @Query("SELECT sa FROM StudentAttendance sa WHERE sa.studentId IN :studentIds AND sa.date BETWEEN :startDate AND :endDate")
    List<StudentAttendance> findByStudentIdsAndDateBetween(@Param("studentIds") List<String> studentIds,
                                                          @Param("startDate") LocalDate startDate,
                                                          @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(sa) FROM StudentAttendance sa WHERE sa.studentId = :studentId AND sa.isPresent = true AND sa.date BETWEEN :startDate AND :endDate")
    long countPresentDaysByStudentAndDateRange(@Param("studentId") String studentId,
                                              @Param("startDate") LocalDate startDate,
                                              @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(sa) FROM StudentAttendance sa WHERE sa.studentId = :studentId AND sa.isPresent = false AND sa.date BETWEEN :startDate AND :endDate")
    long countAbsentDaysByStudentAndDateRange(@Param("studentId") String studentId,
                                             @Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(sa) FROM StudentAttendance sa WHERE sa.studentId = :studentId AND sa.date BETWEEN :startDate AND :endDate")
    long countTotalDaysByStudentAndDateRange(@Param("studentId") String studentId,
                                            @Param("startDate") LocalDate startDate,
                                            @Param("endDate") LocalDate endDate);
}
