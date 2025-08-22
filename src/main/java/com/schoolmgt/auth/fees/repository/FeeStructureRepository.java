package com.schoolmgt.auth.fees.repository;

import java.util.Optional;
import java.util.List;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.fees.entity.FeeStructure;

public interface FeeStructureRepository extends BaseRepository<FeeStructure> {
    Optional<FeeStructure> findByFeeStructureId(String feeStructureId);
    List<FeeStructure> findByClassIdAndAcademicYear(String classId, String academicYear);
    List<FeeStructure> findByClassIdAndAcademicYearAndStatus(String classId, String academicYear, String status);
    Optional<FeeStructure> findByClassIdAndAcademicYearAndStatusOrderByVersionDesc(String classId, String academicYear, String status);
    List<FeeStructure> findByStatus(String status);
}
