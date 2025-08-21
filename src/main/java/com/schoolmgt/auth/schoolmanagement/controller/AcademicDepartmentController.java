package com.schoolmgt.auth.schoolmanagement.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmgt.auth.base.dto.ApiResponse;
import com.schoolmgt.auth.schoolmanagement.dto.AcademicDepartmentRequestDTO;
import com.schoolmgt.auth.schoolmanagement.dto.AcademicDepartmentResponseDTO;
import com.schoolmgt.auth.schoolmanagement.service.AcademicDepartmentService;

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
    public ResponseEntity<ApiResponse<AcademicDepartmentResponseDTO>> registerAcademicDepartment( 
        @Valid @RequestBody AcademicDepartmentRequestDTO dto) {
            AcademicDepartmentResponseDTO dept = departmentService.createAcademicDepartment(dto);
            return ResponseEntity.ok(ApiResponse.success(dept, "Department added successfully"));
    }
    
}
