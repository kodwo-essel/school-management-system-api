package com.schoolmgt.auth.schoolmanagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmgt.auth.base.dto.ApiResponse;
import com.schoolmgt.auth.schoolmanagement.dto.SchoolClassRequestDTO;
import com.schoolmgt.auth.schoolmanagement.dto.SchoolClassResponseDTO;
import com.schoolmgt.auth.schoolmanagement.dto.SchoolClassUpdateRequestDTO;
import com.schoolmgt.auth.schoolmanagement.service.SchoolClassService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
@Tag(name = "School Class Management", description = "APIs for managing school classes")
public class SchoolClassController {

    private final SchoolClassService schoolClassService;

    @PostMapping("/add")
    @Operation(summary = "Create a new class")
    public ResponseEntity<ApiResponse<SchoolClassResponseDTO>> addSchoolClass(@Valid @RequestBody SchoolClassRequestDTO request) {
        SchoolClassResponseDTO schoolClass = schoolClassService.addSchoolClass(request);
        return ResponseEntity.ok(ApiResponse.success(schoolClass, "Class created successfully"));
    }

    @GetMapping("/{classId}")
    @Operation(summary = "Get class by ID")
    public ResponseEntity<ApiResponse<SchoolClassResponseDTO>> getClassById(@PathVariable String classId) {
        SchoolClassResponseDTO schoolClass = schoolClassService.getClassById(classId);
        return ResponseEntity.ok(ApiResponse.success(schoolClass, "Class retrieved successfully"));
    }

    @GetMapping("/school/{schoolId}")
    @Operation(summary = "Get all classes by school")
    public ResponseEntity<ApiResponse<List<SchoolClassResponseDTO>>> getClassesBySchool(@PathVariable String schoolId) {
        List<SchoolClassResponseDTO> classes = schoolClassService.getClassesBySchool(schoolId);
        return ResponseEntity.ok(ApiResponse.success(classes, "School classes retrieved successfully"));
    }

    @GetMapping("/department/{departmentId}")
    @Operation(summary = "Get classes by department")
    public ResponseEntity<ApiResponse<List<SchoolClassResponseDTO>>> getClassesByDepartment(@PathVariable String departmentId) {
        List<SchoolClassResponseDTO> classes = schoolClassService.getClassesByDepartment(departmentId);
        return ResponseEntity.ok(ApiResponse.success(classes, "Department classes retrieved successfully"));
    }

    @GetMapping("/academic-year/{academicYear}")
    @Operation(summary = "Get classes by academic year")
    public ResponseEntity<ApiResponse<List<SchoolClassResponseDTO>>> getClassesByAcademicYear(@PathVariable String academicYear) {
        List<SchoolClassResponseDTO> classes = schoolClassService.getClassesByAcademicYear(academicYear);
        return ResponseEntity.ok(ApiResponse.success(classes, "Classes by academic year retrieved successfully"));
    }

    @GetMapping("/school/{schoolId}/academic-year/{academicYear}")
    @Operation(summary = "Get classes by school and academic year")
    public ResponseEntity<ApiResponse<List<SchoolClassResponseDTO>>> getClassesBySchoolAndAcademicYear(
            @PathVariable String schoolId,
            @PathVariable String academicYear) {
        List<SchoolClassResponseDTO> classes = schoolClassService.getClassesBySchoolAndAcademicYear(schoolId, academicYear);
        return ResponseEntity.ok(ApiResponse.success(classes, "Classes by school and academic year retrieved successfully"));
    }

    @GetMapping("/teacher/{teacherId}")
    @Operation(summary = "Get classes by teacher")
    public ResponseEntity<ApiResponse<List<SchoolClassResponseDTO>>> getClassesByTeacher(@PathVariable String teacherId) {
        List<SchoolClassResponseDTO> classes = schoolClassService.getClassesByTeacher(teacherId);
        return ResponseEntity.ok(ApiResponse.success(classes, "Teacher classes retrieved successfully"));
    }

    @PutMapping("/{classId}")
    @Operation(summary = "Update class")
    public ResponseEntity<ApiResponse<SchoolClassResponseDTO>> updateClass(
            @PathVariable String classId,
            @RequestBody SchoolClassUpdateRequestDTO updateRequest) {
        SchoolClassResponseDTO schoolClass = schoolClassService.updateClass(classId, updateRequest);
        return ResponseEntity.ok(ApiResponse.success(schoolClass, "Class updated successfully"));
    }

    @PutMapping("/{classId}/teacher")
    @Operation(summary = "Assign class teacher")
    public ResponseEntity<ApiResponse<SchoolClassResponseDTO>> assignClassTeacher(
            @PathVariable String classId,
            @RequestParam String teacherId) {
        SchoolClassResponseDTO schoolClass = schoolClassService.assignClassTeacher(classId, teacherId);
        return ResponseEntity.ok(ApiResponse.success(schoolClass, "Class teacher assigned successfully"));
    }

    @PutMapping("/{classId}/student-count")
    @Operation(summary = "Update student count")
    public ResponseEntity<ApiResponse<SchoolClassResponseDTO>> updateStudentCount(
            @PathVariable String classId,
            @RequestParam Integer studentCount) {
        SchoolClassResponseDTO schoolClass = schoolClassService.updateStudentCount(classId, studentCount);
        return ResponseEntity.ok(ApiResponse.success(schoolClass, "Student count updated successfully"));
    }

    @DeleteMapping("/{classId}")
    @Operation(summary = "Delete class")
    public ResponseEntity<ApiResponse<Void>> deleteClass(@PathVariable String classId) {
        schoolClassService.deleteClass(classId);
        return ResponseEntity.ok(ApiResponse.success(null, "Class deleted successfully"));
    }

    @GetMapping("/check-name")
    @Operation(summary = "Check if class name is available")
    public ResponseEntity<ApiResponse<Boolean>> isClassNameAvailable(
            @RequestParam String name,
            @RequestParam String academicYear) {
        boolean available = schoolClassService.isClassNameAvailable(name, academicYear);
        return ResponseEntity.ok(ApiResponse.success(available, "Class name availability checked successfully"));
    }
}
