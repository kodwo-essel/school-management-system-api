package com.schoolmgt.auth.schoolmanagement.repository;

import java.util.List;
import java.util.Optional;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.schoolmanagement.entity.StudentClassEnrollment;

public interface StudentClassEnrollmentRepository extends BaseRepository<StudentClassEnrollment> {
    Optional<StudentClassEnrollment> findByStudentIdAndAcademicYear(String studentId, String academicYear);
    List<StudentClassEnrollment> findByStudentId(String studentId);
    List<StudentClassEnrollment> findBySchoolClass_ClassId(String classId);
    List<StudentClassEnrollment> findBySchoolClass_ClassIdAndAcademicYear(String classId, String academicYear);
    List<StudentClassEnrollment> findBySchoolClass_ClassIdAndAcademicYearAndStatus(String classId, String academicYear, String status);
    List<StudentClassEnrollment> findByStatus(String status);
    Optional<StudentClassEnrollment> findByStudentIdAndSchoolClass_ClassIdAndAcademicYear(String studentId, String classId, String academicYear);
    boolean existsByStudentIdAndAcademicYearAndStatus(String studentId, String academicYear, String status);
}
