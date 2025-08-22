package com.schoolmgt.auth.grades.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.grades.entity.Grade;

public interface GradeRepository extends BaseRepository<Grade>{

    Optional<Grade> findByStudentIdAndClassSubjectId(String studentId, String classSubjectId);

    List<Grade> findByStudentId(String studentId);

    List<Grade> findByClassSubjectId(String classSubjectId);

    List<Grade> findByStudentIdOrderByTotalScoreDesc(String studentId);

    List<Grade> findByClassSubjectIdOrderByTotalScoreDesc(String classSubjectId);

    @Query("SELECT g FROM Grade g WHERE g.studentId IN :studentIds")
    List<Grade> findByStudentIds(@Param("studentIds") List<String> studentIds);

    @Query("SELECT g FROM Grade g WHERE g.classSubjectId IN :classSubjectIds")
    List<Grade> findByClassSubjectIds(@Param("classSubjectIds") List<String> classSubjectIds);

    @Query("SELECT g FROM Grade g WHERE g.studentId = :studentId AND g.classSubjectId IN :classSubjectIds")
    List<Grade> findByStudentIdAndClassSubjectIds(@Param("studentId") String studentId,
                                                  @Param("classSubjectIds") List<String> classSubjectIds);

    @Query("SELECT AVG(g.totalScore) FROM Grade g WHERE g.studentId = :studentId")
    Double findAverageScoreByStudentId(@Param("studentId") String studentId);

    @Query("SELECT AVG(g.totalScore) FROM Grade g WHERE g.classSubjectId = :classSubjectId")
    Double findAverageScoreByClassSubjectId(@Param("classSubjectId") String classSubjectId);

    @Query("SELECT COUNT(g) FROM Grade g WHERE g.classSubjectId = :classSubjectId AND g.totalScore >= :minScore")
    long countStudentsAboveScore(@Param("classSubjectId") String classSubjectId, @Param("minScore") Double minScore);

    @Query("SELECT g FROM Grade g WHERE g.totalScore >= :minScore AND g.totalScore <= :maxScore")
    List<Grade> findByScoreRange(@Param("minScore") Double minScore, @Param("maxScore") Double maxScore);

    @Query("SELECT g FROM Grade g WHERE g.gradeLetter = :gradeLetter")
    List<Grade> findByGradeLetter(@Param("gradeLetter") String gradeLetter);

    @Query("SELECT g FROM Grade g WHERE g.classSubjectId = :classSubjectId AND g.gradeLetter = :gradeLetter")
    List<Grade> findByClassSubjectIdAndGradeLetter(@Param("classSubjectId") String classSubjectId,
                                                   @Param("gradeLetter") String gradeLetter);
}
