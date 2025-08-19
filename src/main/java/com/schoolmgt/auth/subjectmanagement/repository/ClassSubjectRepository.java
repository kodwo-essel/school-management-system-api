package com.schoolmgt.auth.subjectmanagement.repository;

import java.util.List;
import java.util.Optional;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.subjectmanagement.entity.ClassSubject;
import com.schoolmgt.auth.subjectmanagement.entity.Semester;

public interface ClassSubjectRepository extends BaseRepository<ClassSubject>{
    List<ClassSubject> findByClassId(String classId);

    List<ClassSubject> findBySubjectId(String subjectId);

    Optional<ClassSubject> findBySubjectIdAndClassId(String subjectId, String classId);

    Optional<ClassSubject> findBySubjectIdAndClassIdAndSemester(String subjectId, String classId, Semester semester);
}
