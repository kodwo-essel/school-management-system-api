package com.schoolmgt.auth.grades.repository;

import java.util.Optional;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.grades.entity.Grade;

public interface GradeRepository extends BaseRepository<Grade>{
    Optional<Grade> findByStudentIdAndClassSubjectId(String studentId, String classSubjectId);
}
