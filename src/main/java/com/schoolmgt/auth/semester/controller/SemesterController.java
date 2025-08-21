package com.schoolmgt.auth.semester.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmgt.auth.base.dto.ApiResponse;
import com.schoolmgt.auth.semester.dto.SemesterRequestDTO;
import com.schoolmgt.auth.semester.dto.SemesterResponseDTO;
import com.schoolmgt.auth.semester.service.SemesterService;

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
    public ResponseEntity<ApiResponse<SemesterResponseDTO>> registerAcademicSemester( 
        @Valid @RequestBody SemesterRequestDTO dto) {
            SemesterResponseDTO semester = semesterService.addSemester(dto);
            return ResponseEntity.ok(ApiResponse.success(semester, "Semester registered successfully"));
    }
    
}
