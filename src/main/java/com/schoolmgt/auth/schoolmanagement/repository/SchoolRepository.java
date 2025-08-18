package com.schoolmgt.auth.schoolmanagement.repository;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.schoolmanagement.entity.School;

import java.util.Optional;


public interface SchoolRepository extends BaseRepository<School>{
    Optional<School> findBySchoolId(String schoolId);
}
