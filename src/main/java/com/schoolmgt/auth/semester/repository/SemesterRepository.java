package com.schoolmgt.auth.semester.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.semester.entity.Semester;

public interface SemesterRepository extends BaseRepository<Semester>{

    Optional<Semester> findByNameAndSchoolIdAndAcademicYear(String name, String schoolId, String academicYear);

    Optional<Semester> findBySemesterId(String semesterId);

    List<Semester> findBySchoolId(String schoolId);

    List<Semester> findByAcademicYear(String academicYear);

    List<Semester> findBySchoolIdAndAcademicYear(String schoolId, String academicYear);

    @Query("SELECT s FROM Semester s WHERE s.name LIKE %:name%")
    List<Semester> findByNameContaining(@Param("name") String name);

    @Query("SELECT s FROM Semester s WHERE s.schoolId = :schoolId AND s.name LIKE %:name%")
    List<Semester> findBySchoolIdAndNameContaining(@Param("schoolId") String schoolId, @Param("name") String name);

    @Query("SELECT s FROM Semester s WHERE s.schoolId = :schoolId AND s.startDate <= :date AND s.endDate >= :date")
    Optional<Semester> findCurrentSemesterBySchool(@Param("schoolId") String schoolId, @Param("date") LocalDate date);

    boolean existsByNameAndSchoolIdAndAcademicYear(String name, String schoolId, String academicYear);


}
