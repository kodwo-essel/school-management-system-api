package com.schoolmgt.auth.subjectmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.subjectmanagement.entity.ClassSubject;

public interface ClassSubjectRepository extends BaseRepository<ClassSubject>{

    List<ClassSubject> findByClassId(String classId);

    List<ClassSubject> findBySubjectId(String subjectId);

    List<ClassSubject> findByTeacherId(String teacherId);

    List<ClassSubject> findBySemesterId(String semesterId);

    List<ClassSubject> findByStatus(String status);

    Optional<ClassSubject> findBySubjectIdAndClassId(String subjectId, String classId);

    Optional<ClassSubject> findBySubjectIdAndClassIdAndSemesterId(String subjectId, String classId, String semesterId);

    Optional<ClassSubject> findByClassSubjectId(String classSubjectId);

    List<ClassSubject> findByClassIdAndSemesterId(String classId, String semesterId);

    List<ClassSubject> findByTeacherIdAndSemesterId(String teacherId, String semesterId);

    List<ClassSubject> findByClassIdAndStatus(String classId, String status);

    @Query("SELECT cs FROM ClassSubject cs WHERE cs.classId = :classId AND cs.academicYear = :academicYear")
    List<ClassSubject> findByClassIdAndAcademicYear(@Param("classId") String classId, @Param("academicYear") String academicYear);

    @Query("SELECT cs FROM ClassSubject cs WHERE cs.teacherId = :teacherId AND cs.academicYear = :academicYear")
    List<ClassSubject> findByTeacherIdAndAcademicYear(@Param("teacherId") String teacherId, @Param("academicYear") String academicYear);

    @Query("SELECT COUNT(cs) FROM ClassSubject cs WHERE cs.teacherId = :teacherId AND cs.status = 'ACTIVE'")
    long countActiveClassSubjectsByTeacher(@Param("teacherId") String teacherId);

    @Query("SELECT COUNT(cs) FROM ClassSubject cs WHERE cs.classId = :classId AND cs.status = 'ACTIVE'")
    long countActiveSubjectsByClass(@Param("classId") String classId);

    boolean existsByClassIdAndSubjectIdAndSemesterId(String classId, String subjectId, String semesterId);
}
