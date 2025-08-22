package com.schoolmgt.auth.schoolmanagement.service;

import java.util.List;

import com.schoolmgt.auth.base.service.BaseService;
import com.schoolmgt.auth.schoolmanagement.dto.*;
import com.schoolmgt.auth.schoolmanagement.entity.School;

public interface SchoolService extends BaseService<School>{

    SchoolResponseDTO registerSchool(SchoolRegistrationDTO dto);

    SchoolResponseDTO getSchoolById(String schoolId);

    SchoolResponseDTO updateSchool(String schoolId, SchoolUpdateRequestDTO updateRequest);

    void deleteSchool(String schoolId);

    boolean isSchoolNameAvailable(String name);
}
