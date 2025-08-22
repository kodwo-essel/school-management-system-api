package com.schoolmgt.auth.schoolmanagement.service;

import java.util.List;

import com.schoolmgt.auth.base.service.BaseService;
import com.schoolmgt.auth.schoolmanagement.dto.BulkStudentEnrollmentRequestDTO;
import com.schoolmgt.auth.schoolmanagement.dto.StudentClassEnrollmentRequestDTO;
import com.schoolmgt.auth.schoolmanagement.dto.StudentClassEnrollmentResponseDTO;
import com.schoolmgt.auth.schoolmanagement.entity.StudentClassEnrollment;

public interface StudentClassEnrollmentService extends BaseService<StudentClassEnrollment> {
    
    StudentClassEnrollmentResponseDTO enrollStudent(StudentClassEnrollmentRequestDTO requestDTO);
    
    List<StudentClassEnrollmentResponseDTO> enrollStudentsBulk(BulkStudentEnrollmentRequestDTO requestDTO);
    
    StudentClassEnrollmentResponseDTO getStudentEnrollment(String studentId, String academicYear);
    
    List<StudentClassEnrollmentResponseDTO> getStudentEnrollmentHistory(String studentId);
    
    List<StudentClassEnrollmentResponseDTO> getClassEnrollments(String classId, String academicYear);
    
    List<StudentClassEnrollmentResponseDTO> getActiveClassEnrollments(String classId, String academicYear);
    
    StudentClassEnrollmentResponseDTO updateEnrollmentStatus(String studentId, String classId, String academicYear, String status);
    
    void transferStudent(String studentId, String fromClassId, String toClassId, String academicYear);
    
    void withdrawStudent(String studentId, String classId, String academicYear);
    
    long getClassEnrollmentCount(String classId, String academicYear);
    
    boolean isStudentEnrolledInClass(String studentId, String classId, String academicYear);
}
