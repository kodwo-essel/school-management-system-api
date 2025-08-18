package com.schoolmgt.auth.usermanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmgt.auth.base.dto.ApiResponse;
import com.schoolmgt.auth.usermanagement.dto.StudentRegistrationDTO;
import com.schoolmgt.auth.usermanagement.dto.StudentResponseDTO;
import com.schoolmgt.auth.usermanagement.dto.TeacherRegistrationDTO;
import com.schoolmgt.auth.usermanagement.dto.TeacherResponseDTO;
import com.schoolmgt.auth.usermanagement.service.AdminService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin Endpoints")
public class AdminController{
    private final AdminService adminService;

    
    @PostMapping("/register/teacher")
    public ResponseEntity<ApiResponse<TeacherResponseDTO>> registerTeacher( 
        @Valid @RequestBody TeacherRegistrationDTO dto) {
            TeacherResponseDTO teacher = adminService.registerTeacher(dto);
            return ResponseEntity.ok(ApiResponse.success(teacher, "Teacher registered successfully"));
    }

    @PostMapping("/register/student")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> registerStudent( 
        @Valid @RequestBody StudentRegistrationDTO dto) {
            StudentResponseDTO teacher = adminService.registerStudent(dto);
            return ResponseEntity.ok(ApiResponse.success(teacher, "Student registered successfully"));
    }
}