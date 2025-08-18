package com.schoolmgt.auth.usermanagement.repository;

import java.util.Optional;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.usermanagement.entity.Student;

public interface StudentRepository extends BaseRepository<Student>{
    Optional<Student> findByEmail(String email);
    Optional<Student> findByStudentId(String studentId);
}
