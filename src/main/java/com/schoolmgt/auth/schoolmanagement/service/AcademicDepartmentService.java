package com.schoolmgt.auth.schoolmanagement.service;

import com.schoolmgt.auth.base.service.BaseService;
import com.schoolmgt.auth.schoolmanagement.dto.AcademicDepartmentRequestDTO;
import com.schoolmgt.auth.schoolmanagement.dto.AcademicDepartmentResponseDTO;
import com.schoolmgt.auth.schoolmanagement.entity.AcademicDepartment;

public interface AcademicDepartmentService extends BaseService<AcademicDepartment>{
    AcademicDepartmentResponseDTO createAcademicDepartment(AcademicDepartmentRequestDTO academicDepartmentRequestDTO);
}
