package com.schoolmgt.auth.fees.service;

import java.util.List;

import com.schoolmgt.auth.fees.dto.StudentFeeAssessmentRequestDTO;
import com.schoolmgt.auth.fees.dto.StudentFeeAssessmentResponseDTO;

public interface StudentFeeAssessmentService {
    StudentFeeAssessmentResponseDTO createAssessment(StudentFeeAssessmentRequestDTO requestDTO);
    StudentFeeAssessmentResponseDTO getAssessmentById(String assessmentId);
    List<StudentFeeAssessmentResponseDTO> getAssessmentsByStudent(String studentId);
    List<StudentFeeAssessmentResponseDTO> getAssessmentsByStudentAndYear(String studentId, String academicYear);
    List<StudentFeeAssessmentResponseDTO> getAssessmentsByClass(String classId, String academicYear, String term);
    List<StudentFeeAssessmentResponseDTO> getPendingAssessments();
    StudentFeeAssessmentResponseDTO updateAssessmentStatus(String assessmentId, String status);
    void deleteAssessment(String assessmentId);
    List<StudentFeeAssessmentResponseDTO> generateBulkAssessments(String classId, String academicYear, String term, String feeStructureId);
}
