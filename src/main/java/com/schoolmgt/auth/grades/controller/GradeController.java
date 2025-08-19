package com.schoolmgt.auth.grades.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmgt.auth.base.dto.ApiResponse;
import com.schoolmgt.auth.grades.dto.GradeRequestDTO;
import com.schoolmgt.auth.grades.dto.GradeResponseDTO;
import com.schoolmgt.auth.grades.service.GradeService;

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
    public ResponseEntity<ApiResponse<GradeResponseDTO>> recordStudentAttendance( 
        @Valid @RequestBody GradeRequestDTO dto) {
            GradeResponseDTO grade = gradeService.saveGrade(dto);
            return ResponseEntity.ok(ApiResponse.success(grade, "Grade recorded successfully"));
    }
}
