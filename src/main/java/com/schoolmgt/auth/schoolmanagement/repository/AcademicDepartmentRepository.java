package com.schoolmgt.auth.schoolmanagement.repository;

import java.util.Optional;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.schoolmanagement.entity.AcademicDepartment;

public interface AcademicDepartmentRepository extends BaseRepository<AcademicDepartment>{
    Optional<AcademicDepartment> findByNameAndSchoolId(String name, String schoolId);
}
