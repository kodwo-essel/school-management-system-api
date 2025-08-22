package com.schoolmgt.auth.schoolmanagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmgt.auth.base.dto.ApiResponse;
import com.schoolmgt.auth.schoolmanagement.dto.*;
import com.schoolmgt.auth.schoolmanagement.service.AcademicDepartmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Tag(name = "Department Management", description = "Academic Department Endpoints")
public class AcademicDepartmentController {

    private final AcademicDepartmentService departmentService;

    @PostMapping("/add")
    @Operation(summary = "Create a new academic department")
    public ResponseEntity<ApiResponse<AcademicDepartmentResponseDTO>> createAcademicDepartment(
        @Valid @RequestBody AcademicDepartmentRequestDTO dto) {
            AcademicDepartmentResponseDTO dept = departmentService.createAcademicDepartment(dto);
            return ResponseEntity.ok(ApiResponse.success(dept, "Department created successfully"));
    }

    @GetMapping("/{departmentId}")
    @Operation(summary = "Get department by ID")
    public ResponseEntity<ApiResponse<AcademicDepartmentResponseDTO>> getDepartmentById(
            @PathVariable String departmentId) {
        AcademicDepartmentResponseDTO department = departmentService.getDepartmentById(departmentId);
        return ResponseEntity.ok(ApiResponse.success(department, "Department retrieved successfully"));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all departments")
    public ResponseEntity<ApiResponse<List<AcademicDepartmentResponseDTO>>> getAllDepartments() {
        List<AcademicDepartmentResponseDTO> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(ApiResponse.success(departments, "Departments retrieved successfully"));
    }

    @GetMapping("/school/{schoolId}")
    @Operation(summary = "Get departments by school")
    public ResponseEntity<ApiResponse<List<AcademicDepartmentResponseDTO>>> getDepartmentsBySchool(
            @PathVariable String schoolId) {
        List<AcademicDepartmentResponseDTO> departments = departmentService.getDepartmentsBySchool(schoolId);
        return ResponseEntity.ok(ApiResponse.success(departments, "School departments retrieved successfully"));
    }



    @GetMapping("/search")
    @Operation(summary = "Search departments by name")
    public ResponseEntity<ApiResponse<List<AcademicDepartmentResponseDTO>>> searchDepartmentsByName(
            @RequestParam String name) {
        List<AcademicDepartmentResponseDTO> departments = departmentService.searchDepartmentsByName(name);
        return ResponseEntity.ok(ApiResponse.success(departments, "Department search results retrieved successfully"));
    }

    @GetMapping("/head/{headOfDepartment}")
    @Operation(summary = "Get departments by head of department")
    public ResponseEntity<ApiResponse<List<AcademicDepartmentResponseDTO>>> getDepartmentsByHead(
            @PathVariable String headOfDepartment) {
        List<AcademicDepartmentResponseDTO> departments = departmentService.getDepartmentsByHead(headOfDepartment);
        return ResponseEntity.ok(ApiResponse.success(departments, "Departments by head retrieved successfully"));
    }

    @PutMapping("/{departmentId}")
    @Operation(summary = "Update department")
    public ResponseEntity<ApiResponse<AcademicDepartmentResponseDTO>> updateDepartment(
            @PathVariable String departmentId,
            @Valid @RequestBody DepartmentUpdateRequestDTO updateRequest) {
        AcademicDepartmentResponseDTO department = departmentService.updateDepartment(departmentId, updateRequest);
        return ResponseEntity.ok(ApiResponse.success(department, "Department updated successfully"));
    }



    @PutMapping("/{departmentId}/head")
    @Operation(summary = "Assign head of department")
    public ResponseEntity<ApiResponse<AcademicDepartmentResponseDTO>> assignHeadOfDepartment(
            @PathVariable String departmentId,
            @RequestParam String teacherId) {
        AcademicDepartmentResponseDTO department = departmentService.assignHeadOfDepartment(departmentId, teacherId);
        return ResponseEntity.ok(ApiResponse.success(department, "Head of department assigned successfully"));
    }

    @DeleteMapping("/{departmentId}")
    @Operation(summary = "Delete department")
    public ResponseEntity<ApiResponse<Void>> deleteDepartment(@PathVariable String departmentId) {
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.ok(ApiResponse.success(null, "Department deleted successfully"));
    }

    @GetMapping("/{departmentId}/statistics")
    @Operation(summary = "Get department statistics")
    public ResponseEntity<ApiResponse<DepartmentStatisticsDTO>> getDepartmentStatistics(
            @PathVariable String departmentId) {
        DepartmentStatisticsDTO statistics = departmentService.getDepartmentStatistics(departmentId);
        return ResponseEntity.ok(ApiResponse.success(statistics, "Department statistics retrieved successfully"));
    }



    @GetMapping("/check-name")
    @Operation(summary = "Check if department name is available")
    public ResponseEntity<ApiResponse<Boolean>> isDepartmentNameAvailable(
            @RequestParam String name,
            @RequestParam String schoolId) {
        boolean available = departmentService.isDepartmentNameAvailable(name, schoolId);
        return ResponseEntity.ok(ApiResponse.success(available, "Department name availability checked successfully"));
    }


}
