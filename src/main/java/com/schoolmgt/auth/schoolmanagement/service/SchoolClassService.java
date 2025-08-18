package com.schoolmgt.auth.schoolmanagement.service;

import com.schoolmgt.auth.base.service.BaseService;
import com.schoolmgt.auth.schoolmanagement.dto.SchoolClassRequestDTO;
import com.schoolmgt.auth.schoolmanagement.dto.SchoolClassResponseDTO;
import com.schoolmgt.auth.schoolmanagement.entity.SchoolClass;

public interface SchoolClassService extends BaseService<SchoolClass>{
    SchoolClassResponseDTO addSchoolClass(SchoolClassRequestDTO schoolClassRequestDTO);
}
