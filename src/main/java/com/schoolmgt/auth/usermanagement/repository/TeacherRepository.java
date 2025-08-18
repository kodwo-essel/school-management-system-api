package com.schoolmgt.auth.usermanagement.repository;

import java.util.Optional;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.usermanagement.entity.Teacher;

public interface TeacherRepository extends BaseRepository<Teacher>{
    Optional<Teacher> findByEmail(String email);
    Optional<Teacher> findByTeacherId(String teacherId);
}
