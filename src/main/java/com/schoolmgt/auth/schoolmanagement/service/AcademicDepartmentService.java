package com.schoolmgt.auth.schoolmanagement.service;

import java.util.List;

import com.schoolmgt.auth.base.service.BaseService;
import com.schoolmgt.auth.schoolmanagement.dto.*;
import com.schoolmgt.auth.schoolmanagement.entity.AcademicDepartment;

public interface AcademicDepartmentService extends BaseService<AcademicDepartment>{

    AcademicDepartmentResponseDTO createAcademicDepartment(AcademicDepartmentRequestDTO academicDepartmentRequestDTO);

    AcademicDepartmentResponseDTO getDepartmentById(String departmentId);

    List<AcademicDepartmentResponseDTO> getAllDepartments();

    List<AcademicDepartmentResponseDTO> getDepartmentsBySchool(String schoolId);

    List<AcademicDepartmentResponseDTO> searchDepartmentsByName(String name);

    List<AcademicDepartmentResponseDTO> getDepartmentsByHead(String headOfDepartment);

    AcademicDepartmentResponseDTO updateDepartment(String departmentId, DepartmentUpdateRequestDTO updateRequest);

    AcademicDepartmentResponseDTO assignHeadOfDepartment(String departmentId, String teacherId);

    void deleteDepartment(String departmentId);

    DepartmentStatisticsDTO getDepartmentStatistics(String departmentId);

    boolean isDepartmentNameAvailable(String name, String schoolId);
}
