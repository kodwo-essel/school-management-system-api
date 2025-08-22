package com.schoolmgt.auth.grades.service;

import java.util.List;

import com.schoolmgt.auth.base.service.BaseService;
import com.schoolmgt.auth.grades.dto.*;
import com.schoolmgt.auth.grades.entity.Grade;

public interface GradeService extends BaseService<Grade>{

    GradeResponseDTO saveGrade(GradeRequestDTO gradeRequestDTO);

    List<GradeResponseDTO> saveBulkGrades(BulkGradeRequestDTO bulkGradeRequestDTO);

    GradeResponseDTO getGradeByStudentAndSubject(String studentId, String classSubjectId);

    List<GradeResponseDTO> getGradesByStudent(String studentId);

    List<GradeResponseDTO> getGradesByStudentAndTerm(String studentId, String academicYear, String term);

    List<GradeResponseDTO> getGradesByClassSubject(String classSubjectId);

    List<GradeResponseDTO> getGradesByClassSubjectAndTerm(String classSubjectId, String academicYear, String term);

    StudentGradeReportDTO getStudentGradeReport(String studentId, String academicYear, String term);

    ClassGradeReportDTO getClassGradeReport(String classSubjectId, String academicYear, String term);

    GradeStatisticsDTO getGradeStatistics(String classSubjectId, String academicYear, String term);

    TranscriptDTO getStudentTranscript(String studentId, String academicYear);

    List<GradeResponseDTO> getTopPerformers(String classSubjectId, String academicYear, String term, int limit);

    List<GradeResponseDTO> getGradesByRange(Double minScore, Double maxScore);

    List<GradeResponseDTO> getGradesByLetter(String gradeLetter);

    Double calculateClassAverage(String classSubjectId, String academicYear, String term);

    GradeResponseDTO updateGrade(String studentId, String classSubjectId, GradeRequestDTO gradeRequestDTO);

    void deleteGrade(String studentId, String classSubjectId);
}
