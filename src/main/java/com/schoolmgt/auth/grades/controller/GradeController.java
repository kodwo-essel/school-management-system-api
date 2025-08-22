package com.schoolmgt.auth.grades.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmgt.auth.base.dto.ApiResponse;
import com.schoolmgt.auth.grades.dto.*;
import com.schoolmgt.auth.grades.service.GradeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/grade")
@RequiredArgsConstructor
@Tag(name = "Grade Management", description = "Student Grades Endpoints")
public class GradeController {
    
    private final GradeService gradeService;

    @PostMapping("/record")
    @Operation(summary = "Record a single grade")
    public ResponseEntity<ApiResponse<GradeResponseDTO>> recordGrade(
        @Valid @RequestBody GradeRequestDTO dto) {
            GradeResponseDTO grade = gradeService.saveGrade(dto);
            return ResponseEntity.ok(ApiResponse.success(grade, "Grade recorded successfully"));
    }

    @PostMapping("/record-bulk")
    @Operation(summary = "Record bulk grades for a class subject")
    public ResponseEntity<ApiResponse<List<GradeResponseDTO>>> recordBulkGrades(
            @Valid @RequestBody BulkGradeRequestDTO dto) {
        List<GradeResponseDTO> grades = gradeService.saveBulkGrades(dto);
        return ResponseEntity.ok(ApiResponse.success(grades, "Bulk grades recorded successfully"));
    }

    @GetMapping("/student/{studentId}/subject/{classSubjectId}")
    @Operation(summary = "Get grade by student and subject")
    public ResponseEntity<ApiResponse<GradeResponseDTO>> getGradeByStudentAndSubject(
            @PathVariable String studentId,
            @PathVariable String classSubjectId) {
        GradeResponseDTO grade = gradeService.getGradeByStudentAndSubject(studentId, classSubjectId);
        return ResponseEntity.ok(ApiResponse.success(grade, "Grade retrieved successfully"));
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get all grades for a student")
    public ResponseEntity<ApiResponse<List<GradeResponseDTO>>> getGradesByStudent(
            @PathVariable String studentId) {
        List<GradeResponseDTO> grades = gradeService.getGradesByStudent(studentId);
        return ResponseEntity.ok(ApiResponse.success(grades, "Student grades retrieved successfully"));
    }

    @GetMapping("/student/{studentId}/term")
    @Operation(summary = "Get student grades by term")
    public ResponseEntity<ApiResponse<List<GradeResponseDTO>>> getGradesByStudentAndTerm(
            @PathVariable String studentId,
            @RequestParam String academicYear,
            @RequestParam String term) {
        List<GradeResponseDTO> grades = gradeService.getGradesByStudentAndTerm(studentId, academicYear, term);
        return ResponseEntity.ok(ApiResponse.success(grades, "Student term grades retrieved successfully"));
    }

    @GetMapping("/subject/{classSubjectId}")
    @Operation(summary = "Get all grades for a class subject")
    public ResponseEntity<ApiResponse<List<GradeResponseDTO>>> getGradesByClassSubject(
            @PathVariable String classSubjectId) {
        List<GradeResponseDTO> grades = gradeService.getGradesByClassSubject(classSubjectId);
        return ResponseEntity.ok(ApiResponse.success(grades, "Class subject grades retrieved successfully"));
    }

    @GetMapping("/subject/{classSubjectId}/term")
    @Operation(summary = "Get class subject grades by term")
    public ResponseEntity<ApiResponse<List<GradeResponseDTO>>> getGradesByClassSubjectAndTerm(
            @PathVariable String classSubjectId,
            @RequestParam String academicYear,
            @RequestParam String term) {
        List<GradeResponseDTO> grades = gradeService.getGradesByClassSubjectAndTerm(classSubjectId, academicYear, term);
        return ResponseEntity.ok(ApiResponse.success(grades, "Class subject term grades retrieved successfully"));
    }

    @GetMapping("/student/{studentId}/report")
    @Operation(summary = "Get student grade report")
    public ResponseEntity<ApiResponse<StudentGradeReportDTO>> getStudentGradeReport(
            @PathVariable String studentId,
            @RequestParam String academicYear,
            @RequestParam String term) {
        StudentGradeReportDTO report = gradeService.getStudentGradeReport(studentId, academicYear, term);
        return ResponseEntity.ok(ApiResponse.success(report, "Student grade report retrieved successfully"));
    }

    @GetMapping("/subject/{classSubjectId}/class-report")
    @Operation(summary = "Get class grade report")
    public ResponseEntity<ApiResponse<ClassGradeReportDTO>> getClassGradeReport(
            @PathVariable String classSubjectId,
            @RequestParam String academicYear,
            @RequestParam String term) {
        ClassGradeReportDTO report = gradeService.getClassGradeReport(classSubjectId, academicYear, term);
        return ResponseEntity.ok(ApiResponse.success(report, "Class grade report retrieved successfully"));
    }

    @GetMapping("/subject/{classSubjectId}/statistics")
    @Operation(summary = "Get grade statistics")
    public ResponseEntity<ApiResponse<GradeStatisticsDTO>> getGradeStatistics(
            @PathVariable String classSubjectId,
            @RequestParam String academicYear,
            @RequestParam String term) {
        GradeStatisticsDTO statistics = gradeService.getGradeStatistics(classSubjectId, academicYear, term);
        return ResponseEntity.ok(ApiResponse.success(statistics, "Grade statistics retrieved successfully"));
    }

    @GetMapping("/student/{studentId}/transcript")
    @Operation(summary = "Get student transcript")
    public ResponseEntity<ApiResponse<TranscriptDTO>> getStudentTranscript(
            @PathVariable String studentId,
            @RequestParam String academicYear) {
        TranscriptDTO transcript = gradeService.getStudentTranscript(studentId, academicYear);
        return ResponseEntity.ok(ApiResponse.success(transcript, "Student transcript retrieved successfully"));
    }

    @GetMapping("/subject/{classSubjectId}/top-performers")
    @Operation(summary = "Get top performers")
    public ResponseEntity<ApiResponse<List<GradeResponseDTO>>> getTopPerformers(
            @PathVariable String classSubjectId,
            @RequestParam String academicYear,
            @RequestParam String term,
            @RequestParam(defaultValue = "10") int limit) {
        List<GradeResponseDTO> topPerformers = gradeService.getTopPerformers(classSubjectId, academicYear, term, limit);
        return ResponseEntity.ok(ApiResponse.success(topPerformers, "Top performers retrieved successfully"));
    }

    @GetMapping("/range")
    @Operation(summary = "Get grades by score range")
    public ResponseEntity<ApiResponse<List<GradeResponseDTO>>> getGradesByRange(
            @RequestParam Double minScore,
            @RequestParam Double maxScore) {
        List<GradeResponseDTO> grades = gradeService.getGradesByRange(minScore, maxScore);
        return ResponseEntity.ok(ApiResponse.success(grades, "Grades by range retrieved successfully"));
    }

    @GetMapping("/letter/{gradeLetter}")
    @Operation(summary = "Get grades by letter grade")
    public ResponseEntity<ApiResponse<List<GradeResponseDTO>>> getGradesByLetter(
            @PathVariable String gradeLetter) {
        List<GradeResponseDTO> grades = gradeService.getGradesByLetter(gradeLetter);
        return ResponseEntity.ok(ApiResponse.success(grades, "Grades by letter retrieved successfully"));
    }

    @GetMapping("/subject/{classSubjectId}/average")
    @Operation(summary = "Calculate class average")
    public ResponseEntity<ApiResponse<Double>> calculateClassAverage(
            @PathVariable String classSubjectId,
            @RequestParam String academicYear,
            @RequestParam String term) {
        Double average = gradeService.calculateClassAverage(classSubjectId, academicYear, term);
        return ResponseEntity.ok(ApiResponse.success(average, "Class average calculated successfully"));
    }

    @PutMapping("/student/{studentId}/subject/{classSubjectId}")
    @Operation(summary = "Update grade")
    public ResponseEntity<ApiResponse<GradeResponseDTO>> updateGrade(
            @PathVariable String studentId,
            @PathVariable String classSubjectId,
            @Valid @RequestBody GradeRequestDTO dto) {
        GradeResponseDTO grade = gradeService.updateGrade(studentId, classSubjectId, dto);
        return ResponseEntity.ok(ApiResponse.success(grade, "Grade updated successfully"));
    }

    @DeleteMapping("/student/{studentId}/subject/{classSubjectId}")
    @Operation(summary = "Delete grade")
    public ResponseEntity<ApiResponse<Void>> deleteGrade(
            @PathVariable String studentId,
            @PathVariable String classSubjectId) {
        gradeService.deleteGrade(studentId, classSubjectId);
        return ResponseEntity.ok(ApiResponse.success(null, "Grade deleted successfully"));
    }
}
