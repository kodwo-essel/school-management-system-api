package com.schoolmgt.auth.fees.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmgt.auth.base.dto.ApiResponse;
import com.schoolmgt.auth.fees.dto.StudentFeeAssessmentRequestDTO;
import com.schoolmgt.auth.fees.dto.StudentFeeAssessmentResponseDTO;
import com.schoolmgt.auth.fees.service.StudentFeeAssessmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fees/assessments")
@RequiredArgsConstructor
@Tag(name = "Student Fee Assessment Management", description = "Student Fee Assessment Management Endpoints")
public class StudentFeeAssessmentController {

    private final StudentFeeAssessmentService assessmentService;

    @PostMapping("/create")
    @Operation(summary = "Create a new student fee assessment")
    public ResponseEntity<ApiResponse<StudentFeeAssessmentResponseDTO>> createAssessment(
            @Valid @RequestBody StudentFeeAssessmentRequestDTO requestDTO) {
        StudentFeeAssessmentResponseDTO assessment = assessmentService.createAssessment(requestDTO);
        return ResponseEntity.ok(ApiResponse.success(assessment, "Fee assessment created successfully"));
    }

    @GetMapping("/{assessmentId}")
    @Operation(summary = "Get assessment by ID")
    public ResponseEntity<ApiResponse<StudentFeeAssessmentResponseDTO>> getAssessmentById(
            @PathVariable String assessmentId) {
        StudentFeeAssessmentResponseDTO assessment = assessmentService.getAssessmentById(assessmentId);
        return ResponseEntity.ok(ApiResponse.success(assessment, "Assessment retrieved successfully"));
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get assessments by student ID")
    public ResponseEntity<ApiResponse<List<StudentFeeAssessmentResponseDTO>>> getAssessmentsByStudent(
            @PathVariable String studentId) {
        List<StudentFeeAssessmentResponseDTO> assessments = assessmentService.getAssessmentsByStudent(studentId);
        return ResponseEntity.ok(ApiResponse.success(assessments, "Student assessments retrieved successfully"));
    }

    @GetMapping("/student/{studentId}/year/{academicYear}")
    @Operation(summary = "Get assessments by student and academic year")
    public ResponseEntity<ApiResponse<List<StudentFeeAssessmentResponseDTO>>> getAssessmentsByStudentAndYear(
            @PathVariable String studentId,
            @PathVariable String academicYear) {
        List<StudentFeeAssessmentResponseDTO> assessments = assessmentService.getAssessmentsByStudentAndYear(studentId, academicYear);
        return ResponseEntity.ok(ApiResponse.success(assessments, "Student assessments retrieved successfully"));
    }

    @GetMapping("/class/{classId}/year/{academicYear}/term/{term}")
    @Operation(summary = "Get assessments by class, year, and term")
    public ResponseEntity<ApiResponse<List<StudentFeeAssessmentResponseDTO>>> getAssessmentsByClass(
            @PathVariable String classId,
            @PathVariable String academicYear,
            @PathVariable String term) {
        List<StudentFeeAssessmentResponseDTO> assessments = assessmentService.getAssessmentsByClass(classId, academicYear, term);
        return ResponseEntity.ok(ApiResponse.success(assessments, "Class assessments retrieved successfully"));
    }

    @GetMapping("/pending")
    @Operation(summary = "Get all pending assessments")
    public ResponseEntity<ApiResponse<List<StudentFeeAssessmentResponseDTO>>> getPendingAssessments() {
        List<StudentFeeAssessmentResponseDTO> assessments = assessmentService.getPendingAssessments();
        return ResponseEntity.ok(ApiResponse.success(assessments, "Pending assessments retrieved successfully"));
    }

    @PutMapping("/{assessmentId}/status")
    @Operation(summary = "Update assessment status")
    public ResponseEntity<ApiResponse<StudentFeeAssessmentResponseDTO>> updateAssessmentStatus(
            @PathVariable String assessmentId,
            @RequestParam String status) {
        StudentFeeAssessmentResponseDTO assessment = assessmentService.updateAssessmentStatus(assessmentId, status);
        return ResponseEntity.ok(ApiResponse.success(assessment, "Assessment status updated successfully"));
    }

    @DeleteMapping("/{assessmentId}")
    @Operation(summary = "Delete assessment")
    public ResponseEntity<ApiResponse<Void>> deleteAssessment(@PathVariable String assessmentId) {
        assessmentService.deleteAssessment(assessmentId);
        return ResponseEntity.ok(ApiResponse.success(null, "Assessment deleted successfully"));
    }
}
