package com.schoolmgt.auth.subjectmanagement.service;

import com.schoolmgt.auth.base.service.BaseService;
import com.schoolmgt.auth.subjectmanagement.dto.ClassSubjectRequestDTO;
import com.schoolmgt.auth.subjectmanagement.dto.ClassSubjectResponseDTO;
import com.schoolmgt.auth.subjectmanagement.entity.ClassSubject;

public interface ClassSubjectService extends BaseService<ClassSubject>{
    ClassSubjectResponseDTO assignSubjectToClass(ClassSubjectRequestDTO request);

    ClassSubjectResponseDTO assignTeacherToClassSubject(Long classSubjectId, String teacherId);

}
