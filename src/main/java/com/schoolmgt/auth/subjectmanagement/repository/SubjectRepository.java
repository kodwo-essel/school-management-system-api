package com.schoolmgt.auth.subjectmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.subjectmanagement.entity.Subject;

public interface SubjectRepository extends BaseRepository<Subject> {

    Optional<Subject> findBySubjectId(String subjectId);

    Optional<Subject> findByNameAndSchoolId(String name, String schoolId);

    List<Subject> findBySchoolId(String schoolId);

    List<Subject> findByDepartmentId(String departmentId);

    @Query("SELECT s FROM Subject s WHERE s.name LIKE %:name%")
    List<Subject> findByNameContaining(@Param("name") String name);

    @Query("SELECT s FROM Subject s WHERE s.schoolId = :schoolId AND s.name LIKE %:name%")
    List<Subject> findBySchoolIdAndNameContaining(@Param("schoolId") String schoolId, @Param("name") String name);

    boolean existsByNameAndSchoolId(String name, String schoolId);

}
