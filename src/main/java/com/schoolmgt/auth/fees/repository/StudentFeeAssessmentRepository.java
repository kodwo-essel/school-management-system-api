package com.schoolmgt.auth.fees.repository;

import java.util.Optional;
import java.util.List;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.fees.entity.StudentFeeAssessment;

public interface StudentFeeAssessmentRepository extends BaseRepository<StudentFeeAssessment> {
    Optional<StudentFeeAssessment> findByAssessmentId(String assessmentId);
    List<StudentFeeAssessment> findByStudentId(String studentId);
    List<StudentFeeAssessment> findByStudentIdAndAcademicYear(String studentId, String academicYear);
    List<StudentFeeAssessment> findByStudentIdAndAcademicYearAndTerm(String studentId, String academicYear, String term);
    List<StudentFeeAssessment> findByClassIdAndAcademicYearAndTerm(String classId, String academicYear, String term);
    List<StudentFeeAssessment> findByStatus(String status);
    Optional<StudentFeeAssessment> findByStudentIdAndFeeStructureIdAndTerm(String studentId, String feeStructureId, String term);
}
