package com.schoolmgt.auth.schoolmanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmgt.auth.base.dto.ApiResponse;
import com.schoolmgt.auth.schoolmanagement.dto.*;
import com.schoolmgt.auth.schoolmanagement.service.SchoolService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/school")
@RequiredArgsConstructor
@Tag(name = "School Management", description = "School Endpoints")
public class SchoolController {

    private final SchoolService schoolService;

    @PostMapping("/register")
    @Operation(summary = "Register a new school")
    public ResponseEntity<ApiResponse<SchoolResponseDTO>> registerSchool(
        @Valid @RequestBody SchoolRegistrationDTO dto) {
            SchoolResponseDTO school = schoolService.registerSchool(dto);
            return ResponseEntity.ok(ApiResponse.success(school, "School registered successfully"));
    }

    @GetMapping("/{schoolId}")
    @Operation(summary = "Get school by ID")
    public ResponseEntity<ApiResponse<SchoolResponseDTO>> getSchoolById(@PathVariable String schoolId) {
        SchoolResponseDTO school = schoolService.getSchoolById(schoolId);
        return ResponseEntity.ok(ApiResponse.success(school, "School retrieved successfully"));
    }





    @PutMapping("/{schoolId}")
    @Operation(summary = "Update school")
    public ResponseEntity<ApiResponse<SchoolResponseDTO>> updateSchool(
            @PathVariable String schoolId,
            @Valid @RequestBody SchoolUpdateRequestDTO updateRequest) {
        SchoolResponseDTO school = schoolService.updateSchool(schoolId, updateRequest);
        return ResponseEntity.ok(ApiResponse.success(school, "School updated successfully"));
    }



    @DeleteMapping("/{schoolId}")
    @Operation(summary = "Delete school")
    public ResponseEntity<ApiResponse<Void>> deleteSchool(@PathVariable String schoolId) {
        schoolService.deleteSchool(schoolId);
        return ResponseEntity.ok(ApiResponse.success(null, "School deleted successfully"));
    }



    @GetMapping("/check-name")
    @Operation(summary = "Check if school name is available")
    public ResponseEntity<ApiResponse<Boolean>> isSchoolNameAvailable(@RequestParam String name) {
        boolean available = schoolService.isSchoolNameAvailable(name);
        return ResponseEntity.ok(ApiResponse.success(available, "School name availability checked successfully"));
    }


}
