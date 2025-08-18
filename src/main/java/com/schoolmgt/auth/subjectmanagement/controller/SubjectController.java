package com.schoolmgt.auth.subjectmanagement.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmgt.auth.base.dto.ApiResponse;
import com.schoolmgt.auth.subjectmanagement.dto.ClassSubjectRequestDTO;
import com.schoolmgt.auth.subjectmanagement.dto.ClassSubjectResponseDTO;
import com.schoolmgt.auth.subjectmanagement.dto.SubjectRequestDTO;
import com.schoolmgt.auth.subjectmanagement.dto.SubjectResponseDTO;
import com.schoolmgt.auth.subjectmanagement.service.ClassSubjectService;
import com.schoolmgt.auth.subjectmanagement.service.SubjectService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
@Tag(name = "Subject Management", description = "School Subjects Endpoints")
public class SubjectController {

    public final SubjectService subjectService;
    public final ClassSubjectService classSubjectService;
    
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<SubjectResponseDTO>> addSubject( 
        @Valid @RequestBody SubjectRequestDTO dto) {
            SubjectResponseDTO school = subjectService.createSubject(dto);
            return ResponseEntity.ok(ApiResponse.success(school, "Subject added successfully"));
    }

    @PutMapping("/assign-to-class")
    public ResponseEntity<ApiResponse<ClassSubjectResponseDTO>> addSubject( 
        @Valid @RequestBody ClassSubjectRequestDTO dto) {
            ClassSubjectResponseDTO classSubject = classSubjectService.assignSubjectToClass(dto);
            return ResponseEntity.ok(ApiResponse.success(classSubject, "Subject added to class successfully"));
    }

    @PutMapping("/assign-subject-teacher")
    public ResponseEntity<ApiResponse<ClassSubjectResponseDTO>> assignSubjectTeacher( 
        @RequestParam Long classSubjectId,
        @RequestParam String teacherId) {
            ClassSubjectResponseDTO classSubject = classSubjectService.assignTeacherToClassSubject(classSubjectId, teacherId);
            return ResponseEntity.ok(ApiResponse.success(classSubject, "Subject added to class successfully"));
    }
}
