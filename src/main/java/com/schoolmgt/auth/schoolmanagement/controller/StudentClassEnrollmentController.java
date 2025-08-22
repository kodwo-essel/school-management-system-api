package com.schoolmgt.auth.schoolmanagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmgt.auth.base.dto.ApiResponse;
import com.schoolmgt.auth.schoolmanagement.dto.BulkStudentEnrollmentRequestDTO;
import com.schoolmgt.auth.schoolmanagement.dto.StudentClassEnrollmentRequestDTO;
import com.schoolmgt.auth.schoolmanagement.dto.StudentClassEnrollmentResponseDTO;
import com.schoolmgt.auth.schoolmanagement.service.StudentClassEnrollmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/student-enrollments")
@RequiredArgsConstructor
@Tag(name = "Student Class Enrollment Management", description = "Student Class Enrollment Endpoints")
public class StudentClassEnrollmentController {

    private final StudentClassEnrollmentService enrollmentService;

    @PostMapping("/enroll")
    @Operation(summary = "Enroll a student in a class")
    public ResponseEntity<ApiResponse<StudentClassEnrollmentResponseDTO>> enrollStudent(
            @Valid @RequestBody StudentClassEnrollmentRequestDTO requestDTO) {
        StudentClassEnrollmentResponseDTO enrollment = enrollmentService.enrollStudent(requestDTO);
        return ResponseEntity.ok(ApiResponse.success(enrollment, "Student enrolled successfully"));
    }

    @PostMapping("/enroll-bulk")
    @Operation(summary = "Enroll multiple students in a class")
    public ResponseEntity<ApiResponse<List<StudentClassEnrollmentResponseDTO>>> enrollStudentsBulk(
            @Valid @RequestBody BulkStudentEnrollmentRequestDTO requestDTO) {
        List<StudentClassEnrollmentResponseDTO> enrollments = enrollmentService.enrollStudentsBulk(requestDTO);
        return ResponseEntity.ok(ApiResponse.success(enrollments, "Students enrolled successfully"));
    }

    @GetMapping("/student/{studentId}/year/{academicYear}")
    @Operation(summary = "Get student enrollment for a specific academic year")
    public ResponseEntity<ApiResponse<StudentClassEnrollmentResponseDTO>> getStudentEnrollment(
            @PathVariable String studentId,
            @PathVariable String academicYear) {
        StudentClassEnrollmentResponseDTO enrollment = enrollmentService.getStudentEnrollment(studentId, academicYear);
        return ResponseEntity.ok(ApiResponse.success(enrollment, "Student enrollment retrieved successfully"));
    }

    @GetMapping("/student/{studentId}/history")
    @Operation(summary = "Get student enrollment history")
    public ResponseEntity<ApiResponse<List<StudentClassEnrollmentResponseDTO>>> getStudentEnrollmentHistory(
            @PathVariable String studentId) {
        List<StudentClassEnrollmentResponseDTO> enrollments = enrollmentService.getStudentEnrollmentHistory(studentId);
        return ResponseEntity.ok(ApiResponse.success(enrollments, "Student enrollment history retrieved successfully"));
    }

    @GetMapping("/class/{classId}/year/{academicYear}")
    @Operation(summary = "Get all enrollments for a class in an academic year")
    public ResponseEntity<ApiResponse<List<StudentClassEnrollmentResponseDTO>>> getClassEnrollments(
            @PathVariable String classId,
            @PathVariable String academicYear) {
        List<StudentClassEnrollmentResponseDTO> enrollments = enrollmentService.getClassEnrollments(classId, academicYear);
        return ResponseEntity.ok(ApiResponse.success(enrollments, "Class enrollments retrieved successfully"));
    }

    @GetMapping("/class/{classId}/year/{academicYear}/active")
    @Operation(summary = "Get active enrollments for a class in an academic year")
    public ResponseEntity<ApiResponse<List<StudentClassEnrollmentResponseDTO>>> getActiveClassEnrollments(
            @PathVariable String classId,
            @PathVariable String academicYear) {
        List<StudentClassEnrollmentResponseDTO> enrollments = enrollmentService.getActiveClassEnrollments(classId, academicYear);
        return ResponseEntity.ok(ApiResponse.success(enrollments, "Active class enrollments retrieved successfully"));
    }

    @GetMapping("/class/{classId}/year/{academicYear}/count")
    @Operation(summary = "Get enrollment count for a class")
    public ResponseEntity<ApiResponse<Long>> getClassEnrollmentCount(
            @PathVariable String classId,
            @PathVariable String academicYear) {
        long count = enrollmentService.getClassEnrollmentCount(classId, academicYear);
        return ResponseEntity.ok(ApiResponse.success(count, "Class enrollment count retrieved successfully"));
    }

    @GetMapping("/check/{studentId}/class/{classId}/year/{academicYear}")
    @Operation(summary = "Check if student is enrolled in a specific class")
    public ResponseEntity<ApiResponse<Boolean>> isStudentEnrolledInClass(
            @PathVariable String studentId,
            @PathVariable String classId,
            @PathVariable String academicYear) {
        boolean isEnrolled = enrollmentService.isStudentEnrolledInClass(studentId, classId, academicYear);
        return ResponseEntity.ok(ApiResponse.success(isEnrolled, "Enrollment status checked successfully"));
    }

    @PutMapping("/student/{studentId}/class/{classId}/year/{academicYear}/status")
    @Operation(summary = "Update enrollment status")
    public ResponseEntity<ApiResponse<StudentClassEnrollmentResponseDTO>> updateEnrollmentStatus(
            @PathVariable String studentId,
            @PathVariable String classId,
            @PathVariable String academicYear,
            @RequestParam String status) {
        StudentClassEnrollmentResponseDTO enrollment = enrollmentService.updateEnrollmentStatus(
                studentId, classId, academicYear, status);
        return ResponseEntity.ok(ApiResponse.success(enrollment, "Enrollment status updated successfully"));
    }

    @PutMapping("/student/{studentId}/transfer")
    @Operation(summary = "Transfer student from one class to another")
    public ResponseEntity<ApiResponse<Void>> transferStudent(
            @PathVariable String studentId,
            @RequestParam String fromClassId,
            @RequestParam String toClassId,
            @RequestParam String academicYear) {
        enrollmentService.transferStudent(studentId, fromClassId, toClassId, academicYear);
        return ResponseEntity.ok(ApiResponse.success(null, "Student transferred successfully"));
    }

    @PutMapping("/student/{studentId}/withdraw")
    @Operation(summary = "Withdraw student from class")
    public ResponseEntity<ApiResponse<Void>> withdrawStudent(
            @PathVariable String studentId,
            @RequestParam String classId,
            @RequestParam String academicYear) {
        enrollmentService.withdrawStudent(studentId, classId, academicYear);
        return ResponseEntity.ok(ApiResponse.success(null, "Student withdrawn successfully"));
    }
}
