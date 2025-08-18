package com.schoolmgt.auth.subjectmanagement.service;

import com.schoolmgt.auth.base.service.BaseService;
import com.schoolmgt.auth.subjectmanagement.dto.SubjectRequestDTO;
import com.schoolmgt.auth.subjectmanagement.dto.SubjectResponseDTO;
import com.schoolmgt.auth.subjectmanagement.entity.Subject;

public interface SubjectService extends BaseService<Subject>{
    SubjectResponseDTO createSubject(SubjectRequestDTO request);

}
