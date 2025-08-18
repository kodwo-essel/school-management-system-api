package com.schoolmgt.auth.schoolmanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmgt.auth.base.dto.ApiResponse;
import com.schoolmgt.auth.schoolmanagement.dto.SchoolRegistrationDTO;
import com.schoolmgt.auth.schoolmanagement.dto.SchoolResponseDTO;
import com.schoolmgt.auth.schoolmanagement.service.SchoolService;

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
    public ResponseEntity<ApiResponse<SchoolResponseDTO>> registerSchool( 
        @Valid @RequestBody SchoolRegistrationDTO dto) {
            SchoolResponseDTO school = schoolService.registerSchool(dto);
            return ResponseEntity.ok(ApiResponse.success(school, "School registered successfully"));
    }

}
