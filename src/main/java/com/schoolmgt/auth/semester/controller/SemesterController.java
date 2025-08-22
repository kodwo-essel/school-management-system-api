package com.schoolmgt.auth.semester.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmgt.auth.base.dto.ApiResponse;
import com.schoolmgt.auth.semester.dto.*;
import com.schoolmgt.auth.semester.service.SemesterService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/semester")
@RequiredArgsConstructor
@Tag(name = "Semester Management", description = "Academic Semester Endpoints")
public class SemesterController {

    private final SemesterService semesterService;

    @PostMapping("/register")
    @Operation(summary = "Register a new semester")
    public ResponseEntity<ApiResponse<SemesterResponseDTO>> registerAcademicSemester(
        @Valid @RequestBody SemesterRequestDTO dto) {
            SemesterResponseDTO semester = semesterService.addSemester(dto);
            return ResponseEntity.ok(ApiResponse.success(semester, "Semester registered successfully"));
    }

    @GetMapping("/{semesterId}")
    @Operation(summary = "Get semester by ID")
    public ResponseEntity<ApiResponse<SemesterResponseDTO>> getSemesterById(@PathVariable String semesterId) {
        SemesterResponseDTO semester = semesterService.getSemesterById(semesterId);
        return ResponseEntity.ok(ApiResponse.success(semester, "Semester retrieved successfully"));
    }



    @GetMapping("/school/{schoolId}")
    @Operation(summary = "Get semesters by school")
    public ResponseEntity<ApiResponse<List<SemesterResponseDTO>>> getSemestersBySchool(@PathVariable String schoolId) {
        List<SemesterResponseDTO> semesters = semesterService.getSemestersBySchool(schoolId);
        return ResponseEntity.ok(ApiResponse.success(semesters, "School semesters retrieved successfully"));
    }



    @GetMapping("/academic-year/{academicYear}")
    @Operation(summary = "Get semesters by academic year")
    public ResponseEntity<ApiResponse<List<SemesterResponseDTO>>> getSemestersByAcademicYear(@PathVariable String academicYear) {
        List<SemesterResponseDTO> semesters = semesterService.getSemestersByAcademicYear(academicYear);
        return ResponseEntity.ok(ApiResponse.success(semesters, "Academic year semesters retrieved successfully"));
    }

    @GetMapping("/school/{schoolId}/academic-year/{academicYear}")
    @Operation(summary = "Get semesters by school and academic year")
    public ResponseEntity<ApiResponse<List<SemesterResponseDTO>>> getSemestersBySchoolAndAcademicYear(
            @PathVariable String schoolId,
            @PathVariable String academicYear) {
        List<SemesterResponseDTO> semesters = semesterService.getSemestersBySchoolAndAcademicYear(schoolId, academicYear);
        return ResponseEntity.ok(ApiResponse.success(semesters, "School academic year semesters retrieved successfully"));
    }



    @GetMapping("/school/{schoolId}/current")
    @Operation(summary = "Get current semester for school")
    public ResponseEntity<ApiResponse<SemesterResponseDTO>> getCurrentSemester(@PathVariable String schoolId) {
        SemesterResponseDTO semester = semesterService.getCurrentSemester(schoolId);
        return ResponseEntity.ok(ApiResponse.success(semester, "Current semester retrieved successfully"));
    }





    @PutMapping("/{semesterId}")
    @Operation(summary = "Update semester")
    public ResponseEntity<ApiResponse<SemesterResponseDTO>> updateSemester(
            @PathVariable String semesterId,
            @Valid @RequestBody SemesterUpdateRequestDTO updateRequest) {
        SemesterResponseDTO semester = semesterService.updateSemester(semesterId, updateRequest);
        return ResponseEntity.ok(ApiResponse.success(semester, "Semester updated successfully"));
    }



    @PutMapping("/{semesterId}/set-current")
    @Operation(summary = "Set as current semester")
    public ResponseEntity<ApiResponse<SemesterResponseDTO>> setCurrentSemester(@PathVariable String semesterId) {
        SemesterResponseDTO semester = semesterService.setCurrentSemester(semesterId);
        return ResponseEntity.ok(ApiResponse.success(semester, "Current semester set successfully"));
    }



    @DeleteMapping("/{semesterId}")
    @Operation(summary = "Delete semester")
    public ResponseEntity<ApiResponse<Void>> deleteSemester(@PathVariable String semesterId) {
        semesterService.deleteSemester(semesterId);
        return ResponseEntity.ok(ApiResponse.success(null, "Semester deleted successfully"));
    }



    @GetMapping("/check-name")
    @Operation(summary = "Check if semester name is available")
    public ResponseEntity<ApiResponse<Boolean>> isSemesterNameAvailable(
            @RequestParam String name,
            @RequestParam String schoolId,
            @RequestParam String academicYear) {
        boolean available = semesterService.isSemesterNameAvailable(name, schoolId, academicYear);
        return ResponseEntity.ok(ApiResponse.success(available, "Semester name availability checked successfully"));
    }


}
