package com.schoolmgt.auth.subjectmanagement.service;

import java.util.List;

import com.schoolmgt.auth.base.service.BaseService;
import com.schoolmgt.auth.subjectmanagement.dto.*;
import com.schoolmgt.auth.subjectmanagement.entity.Subject;

public interface SubjectService extends BaseService<Subject>{

    SubjectResponseDTO createSubject(SubjectRequestDTO request);

    SubjectResponseDTO getSubjectById(String subjectId);

    List<SubjectResponseDTO> getSubjectsBySchool(String schoolId);

    List<SubjectResponseDTO> getSubjectsByDepartment(String departmentId);

    SubjectResponseDTO updateSubject(String subjectId, SubjectUpdateRequestDTO updateRequest);

    void deleteSubject(String subjectId);

    boolean isSubjectNameAvailable(String name, String schoolId);
}
