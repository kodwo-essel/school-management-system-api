package com.schoolmgt.auth.schoolmanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmgt.auth.base.dto.ApiResponse;
import com.schoolmgt.auth.schoolmanagement.dto.SchoolClassRequestDTO;
import com.schoolmgt.auth.schoolmanagement.dto.SchoolClassResponseDTO;
import com.schoolmgt.auth.schoolmanagement.service.SchoolClassService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/school-class")
@RequiredArgsConstructor
@Tag(name = "Class Management", description = "School Class Endpoints")
public class SchoolClassController {

    private final SchoolClassService schoolClassService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<SchoolClassResponseDTO>> registerSchoolClass( 
        @Valid @RequestBody SchoolClassRequestDTO dto) {
            SchoolClassResponseDTO school = schoolClassService.addSchoolClass(dto);
            return ResponseEntity.ok(ApiResponse.success(school, "Class registered successfully"));
    }
    
}
