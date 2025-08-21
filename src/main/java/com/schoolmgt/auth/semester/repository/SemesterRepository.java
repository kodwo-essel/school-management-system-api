package com.schoolmgt.auth.semester.repository;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.semester.entity.Semester;

import java.util.Optional;

public interface SemesterRepository extends BaseRepository<Semester>{
    Optional<Semester> findByNameAndSchoolIdAndAcademicYear(String name, String schoolId, String academicYear);
}
