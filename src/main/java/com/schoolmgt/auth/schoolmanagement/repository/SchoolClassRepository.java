package com.schoolmgt.auth.schoolmanagement.repository;

import java.util.Optional;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.schoolmanagement.entity.SchoolClass;

public interface SchoolClassRepository extends BaseRepository<SchoolClass>{
    Optional<SchoolClass> findByClassId(String classId);
    Optional<SchoolClass> findByNameAndAcademicYear(String name, String academicYear);
}
