package com.schoolmgt.auth.subjectmanagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmgt.auth.base.dto.ApiResponse;
import com.schoolmgt.auth.subjectmanagement.dto.*;
import com.schoolmgt.auth.subjectmanagement.service.ClassSubjectService;
import com.schoolmgt.auth.subjectmanagement.service.SubjectService;

import io.swagger.v3.oas.annotations.Operation;
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
    
    // ========== SUBJECT MANAGEMENT ENDPOINTS ==========

    @PostMapping("/add")
    @Operation(summary = "Create a new subject")
    public ResponseEntity<ApiResponse<SubjectResponseDTO>> createSubject(
        @Valid @RequestBody SubjectRequestDTO dto) {
            SubjectResponseDTO subject = subjectService.createSubject(dto);
            return ResponseEntity.ok(ApiResponse.success(subject, "Subject created successfully"));
    }

    @GetMapping("/{subjectId}")
    @Operation(summary = "Get subject by ID")
    public ResponseEntity<ApiResponse<SubjectResponseDTO>> getSubjectById(@PathVariable String subjectId) {
        SubjectResponseDTO subject = subjectService.getSubjectById(subjectId);
        return ResponseEntity.ok(ApiResponse.success(subject, "Subject retrieved successfully"));
    }



    @GetMapping("/school/{schoolId}")
    @Operation(summary = "Get subjects by school")
    public ResponseEntity<ApiResponse<List<SubjectResponseDTO>>> getSubjectsBySchool(@PathVariable String schoolId) {
        List<SubjectResponseDTO> subjects = subjectService.getSubjectsBySchool(schoolId);
        return ResponseEntity.ok(ApiResponse.success(subjects, "School subjects retrieved successfully"));
    }



    @GetMapping("/department/{departmentId}")
    @Operation(summary = "Get subjects by department")
    public ResponseEntity<ApiResponse<List<SubjectResponseDTO>>> getSubjectsByDepartment(@PathVariable String departmentId) {
        List<SubjectResponseDTO> subjects = subjectService.getSubjectsByDepartment(departmentId);
        return ResponseEntity.ok(ApiResponse.success(subjects, "Department subjects retrieved successfully"));
    }





    @PutMapping("/{subjectId}")
    @Operation(summary = "Update subject")
    public ResponseEntity<ApiResponse<SubjectResponseDTO>> updateSubject(
            @PathVariable String subjectId,
            @Valid @RequestBody SubjectUpdateRequestDTO updateRequest) {
        SubjectResponseDTO subject = subjectService.updateSubject(subjectId, updateRequest);
        return ResponseEntity.ok(ApiResponse.success(subject, "Subject updated successfully"));
    }



    @DeleteMapping("/{subjectId}")
    @Operation(summary = "Delete subject")
    public ResponseEntity<ApiResponse<Void>> deleteSubject(@PathVariable String subjectId) {
        subjectService.deleteSubject(subjectId);
        return ResponseEntity.ok(ApiResponse.success(null, "Subject deleted successfully"));
    }



    @GetMapping("/check-name")
    @Operation(summary = "Check if subject name is available")
    public ResponseEntity<ApiResponse<Boolean>> isSubjectNameAvailable(
            @RequestParam String name,
            @RequestParam String schoolId) {
        boolean available = subjectService.isSubjectNameAvailable(name, schoolId);
        return ResponseEntity.ok(ApiResponse.success(available, "Subject name availability checked successfully"));
    }



    // ========== CLASS-SUBJECT MANAGEMENT ENDPOINTS ==========

    @PostMapping("/assign-to-class")
    @Operation(summary = "Assign subject to class")
    public ResponseEntity<ApiResponse<ClassSubjectResponseDTO>> assignSubjectToClass(
        @Valid @RequestBody ClassSubjectRequestDTO dto) {
            ClassSubjectResponseDTO classSubject = classSubjectService.assignSubjectToClass(dto);
            return ResponseEntity.ok(ApiResponse.success(classSubject, "Subject assigned to class successfully"));
    }

    @GetMapping("/class-subject/{classSubjectId}")
    @Operation(summary = "Get class-subject by ID")
    public ResponseEntity<ApiResponse<ClassSubjectResponseDTO>> getClassSubjectById(@PathVariable String classSubjectId) {
        ClassSubjectResponseDTO classSubject = classSubjectService.getClassSubjectById(classSubjectId);
        return ResponseEntity.ok(ApiResponse.success(classSubject, "Class-subject retrieved successfully"));
    }

    @GetMapping("/class/{classId}/subjects")
    @Operation(summary = "Get subjects by class")
    public ResponseEntity<ApiResponse<List<ClassSubjectResponseDTO>>> getClassSubjectsByClass(@PathVariable String classId) {
        List<ClassSubjectResponseDTO> classSubjects = classSubjectService.getClassSubjectsByClass(classId);
        return ResponseEntity.ok(ApiResponse.success(classSubjects, "Class subjects retrieved successfully"));
    }

    @GetMapping("/{subjectId}/classes")
    @Operation(summary = "Get classes by subject")
    public ResponseEntity<ApiResponse<List<ClassSubjectResponseDTO>>> getClassSubjectsBySubject(@PathVariable String subjectId) {
        List<ClassSubjectResponseDTO> classSubjects = classSubjectService.getClassSubjectsBySubject(subjectId);
        return ResponseEntity.ok(ApiResponse.success(classSubjects, "Subject classes retrieved successfully"));
    }

    @GetMapping("/teacher/{teacherId}/subjects")
    @Operation(summary = "Get subjects by teacher")
    public ResponseEntity<ApiResponse<List<ClassSubjectResponseDTO>>> getClassSubjectsByTeacher(@PathVariable String teacherId) {
        List<ClassSubjectResponseDTO> classSubjects = classSubjectService.getClassSubjectsByTeacher(teacherId);
        return ResponseEntity.ok(ApiResponse.success(classSubjects, "Teacher subjects retrieved successfully"));
    }

    @GetMapping("/semester/{semesterId}/subjects")
    @Operation(summary = "Get subjects by semester")
    public ResponseEntity<ApiResponse<List<ClassSubjectResponseDTO>>> getClassSubjectsBySemester(@PathVariable String semesterId) {
        List<ClassSubjectResponseDTO> classSubjects = classSubjectService.getClassSubjectsBySemester(semesterId);
        return ResponseEntity.ok(ApiResponse.success(classSubjects, "Semester subjects retrieved successfully"));
    }

    @GetMapping("/class/{classId}/semester/{semesterId}")
    @Operation(summary = "Get class schedule for semester")
    public ResponseEntity<ApiResponse<List<ClassSubjectResponseDTO>>> getClassSchedule(
            @PathVariable String classId,
            @PathVariable String semesterId) {
        List<ClassSubjectResponseDTO> schedule = classSubjectService.getClassSchedule(classId, semesterId);
        return ResponseEntity.ok(ApiResponse.success(schedule, "Class schedule retrieved successfully"));
    }

    @GetMapping("/teacher/{teacherId}/workload")
    @Operation(summary = "Get teacher workload")
    public ResponseEntity<ApiResponse<List<ClassSubjectResponseDTO>>> getTeacherWorkload(
            @PathVariable String teacherId,
            @RequestParam String academicYear) {
        List<ClassSubjectResponseDTO> workload = classSubjectService.getTeacherWorkload(teacherId, academicYear);
        return ResponseEntity.ok(ApiResponse.success(workload, "Teacher workload retrieved successfully"));
    }

    @PutMapping("/assign-subject-teacher")
    @Operation(summary = "Assign teacher to class-subject")
    public ResponseEntity<ApiResponse<ClassSubjectResponseDTO>> assignSubjectTeacher(
        @RequestParam Long classSubjectId,
        @RequestParam String teacherId) {
            ClassSubjectResponseDTO classSubject = classSubjectService.assignTeacherToClassSubject(classSubjectId, teacherId);
            return ResponseEntity.ok(ApiResponse.success(classSubject, "Teacher assigned to subject successfully"));
    }

    @PutMapping("/class-subject/{classSubjectId}")
    @Operation(summary = "Update class-subject")
    public ResponseEntity<ApiResponse<ClassSubjectResponseDTO>> updateClassSubject(
            @PathVariable String classSubjectId,
            @Valid @RequestBody ClassSubjectUpdateRequestDTO updateRequest) {
        ClassSubjectResponseDTO classSubject = classSubjectService.updateClassSubject(classSubjectId, updateRequest);
        return ResponseEntity.ok(ApiResponse.success(classSubject, "Class-subject updated successfully"));
    }

    @PutMapping("/class-subject/{classSubjectId}/status")
    @Operation(summary = "Update class-subject status")
    public ResponseEntity<ApiResponse<ClassSubjectResponseDTO>> updateClassSubjectStatus(
            @PathVariable String classSubjectId,
            @RequestParam String status) {
        ClassSubjectResponseDTO classSubject = classSubjectService.updateClassSubjectStatus(classSubjectId, status);
        return ResponseEntity.ok(ApiResponse.success(classSubject, "Class-subject status updated successfully"));
    }

    @PutMapping("/class-subject/{classSubjectId}/student-count")
    @Operation(summary = "Update student count")
    public ResponseEntity<ApiResponse<ClassSubjectResponseDTO>> updateStudentCount(
            @PathVariable String classSubjectId,
            @RequestParam Integer studentCount) {
        ClassSubjectResponseDTO classSubject = classSubjectService.updateStudentCount(classSubjectId, studentCount);
        return ResponseEntity.ok(ApiResponse.success(classSubject, "Student count updated successfully"));
    }

    @PutMapping("/class-subject/{classSubjectId}/syllabus-coverage")
    @Operation(summary = "Update syllabus coverage")
    public ResponseEntity<ApiResponse<ClassSubjectResponseDTO>> updateSyllabusCoverage(
            @PathVariable String classSubjectId,
            @RequestParam String coverage) {
        ClassSubjectResponseDTO classSubject = classSubjectService.updateSyllabusCoverage(classSubjectId, coverage);
        return ResponseEntity.ok(ApiResponse.success(classSubject, "Syllabus coverage updated successfully"));
    }

    @PutMapping("/class-subject/{classSubjectId}/average-grade")
    @Operation(summary = "Update average grade")
    public ResponseEntity<ApiResponse<ClassSubjectResponseDTO>> updateAverageGrade(
            @PathVariable String classSubjectId,
            @RequestParam Double averageGrade) {
        ClassSubjectResponseDTO classSubject = classSubjectService.updateAverageGrade(classSubjectId, averageGrade);
        return ResponseEntity.ok(ApiResponse.success(classSubject, "Average grade updated successfully"));
    }

    @DeleteMapping("/class-subject/{classSubjectId}")
    @Operation(summary = "Remove subject from class")
    public ResponseEntity<ApiResponse<Void>> removeSubjectFromClass(@PathVariable String classSubjectId) {
        classSubjectService.removeSubjectFromClass(classSubjectId);
        return ResponseEntity.ok(ApiResponse.success(null, "Subject removed from class successfully"));
    }

    @PutMapping("/transfer-teacher")
    @Operation(summary = "Transfer teacher between class-subjects")
    public ResponseEntity<ApiResponse<Void>> transferTeacher(
            @RequestParam String fromClassSubjectId,
            @RequestParam String toClassSubjectId,
            @RequestParam String teacherId) {
        classSubjectService.transferTeacher(fromClassSubjectId, toClassSubjectId, teacherId);
        return ResponseEntity.ok(ApiResponse.success(null, "Teacher transferred successfully"));
    }

    @GetMapping("/class/{classId}/subject-count")
    @Operation(summary = "Get active subject count by class")
    public ResponseEntity<ApiResponse<Long>> getActiveSubjectCountByClass(@PathVariable String classId) {
        long count = classSubjectService.getActiveSubjectCountByClass(classId);
        return ResponseEntity.ok(ApiResponse.success(count, "Active subject count by class retrieved successfully"));
    }

    @GetMapping("/teacher/{teacherId}/class-subject-count")
    @Operation(summary = "Get active class-subject count by teacher")
    public ResponseEntity<ApiResponse<Long>> getActiveClassSubjectCountByTeacher(@PathVariable String teacherId) {
        long count = classSubjectService.getActiveClassSubjectCountByTeacher(teacherId);
        return ResponseEntity.ok(ApiResponse.success(count, "Active class-subject count by teacher retrieved successfully"));
    }

    @GetMapping("/check-assignment")
    @Operation(summary = "Check if subject is assigned to class")
    public ResponseEntity<ApiResponse<Boolean>> isSubjectAssignedToClass(
            @RequestParam String classId,
            @RequestParam String subjectId,
            @RequestParam String semesterId) {
        boolean assigned = classSubjectService.isSubjectAssignedToClass(classId, subjectId, semesterId);
        return ResponseEntity.ok(ApiResponse.success(assigned, "Subject assignment status checked successfully"));
    }
}
