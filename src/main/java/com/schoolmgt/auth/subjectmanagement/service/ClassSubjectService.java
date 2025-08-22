package com.schoolmgt.auth.subjectmanagement.service;

import java.util.List;

import com.schoolmgt.auth.base.service.BaseService;
import com.schoolmgt.auth.subjectmanagement.dto.*;
import com.schoolmgt.auth.subjectmanagement.entity.ClassSubject;

public interface ClassSubjectService extends BaseService<ClassSubject>{

    ClassSubjectResponseDTO assignSubjectToClass(ClassSubjectRequestDTO request);

    ClassSubjectResponseDTO assignTeacherToClassSubject(Long classSubjectId, String teacherId);

    ClassSubjectResponseDTO getClassSubjectById(String classSubjectId);

    List<ClassSubjectResponseDTO> getClassSubjectsByClass(String classId);

    List<ClassSubjectResponseDTO> getClassSubjectsBySubject(String subjectId);

    List<ClassSubjectResponseDTO> getClassSubjectsByTeacher(String teacherId);

    List<ClassSubjectResponseDTO> getClassSubjectsBySemester(String semesterId);

    List<ClassSubjectResponseDTO> getClassSubjectsByStatus(String status);

    List<ClassSubjectResponseDTO> getClassSubjectsByClassAndSemester(String classId, String semesterId);

    List<ClassSubjectResponseDTO> getClassSubjectsByTeacherAndSemester(String teacherId, String semesterId);

    List<ClassSubjectResponseDTO> getClassSubjectsByAcademicYear(String academicYear);

    ClassSubjectResponseDTO updateClassSubject(String classSubjectId, ClassSubjectUpdateRequestDTO updateRequest);

    ClassSubjectResponseDTO updateClassSubjectStatus(String classSubjectId, String status);

    ClassSubjectResponseDTO updateStudentCount(String classSubjectId, Integer studentCount);

    ClassSubjectResponseDTO updateSyllabusCoverage(String classSubjectId, String coverage);

    ClassSubjectResponseDTO updateAverageGrade(String classSubjectId, Double averageGrade);

    void removeSubjectFromClass(String classSubjectId);

    void transferTeacher(String fromClassSubjectId, String toClassSubjectId, String teacherId);

    long getActiveSubjectCountByClass(String classId);

    long getActiveClassSubjectCountByTeacher(String teacherId);

    boolean isSubjectAssignedToClass(String classId, String subjectId, String semesterId);

    List<ClassSubjectResponseDTO> getTeacherWorkload(String teacherId, String academicYear);

    List<ClassSubjectResponseDTO> getClassSchedule(String classId, String semesterId);
}
