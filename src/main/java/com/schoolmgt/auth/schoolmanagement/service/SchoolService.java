package com.schoolmgt.auth.schoolmanagement.service;

import com.schoolmgt.auth.base.service.BaseService;
import com.schoolmgt.auth.schoolmanagement.dto.SchoolRegistrationDTO;
import com.schoolmgt.auth.schoolmanagement.dto.SchoolResponseDTO;
import com.schoolmgt.auth.schoolmanagement.entity.School;

public interface SchoolService extends BaseService<School>{
    SchoolResponseDTO registerSchool(SchoolRegistrationDTO dto);

}
