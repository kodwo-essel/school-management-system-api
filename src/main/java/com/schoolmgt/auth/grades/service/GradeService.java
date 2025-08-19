package com.schoolmgt.auth.grades.service;

import com.schoolmgt.auth.base.service.BaseService;
import com.schoolmgt.auth.grades.dto.GradeRequestDTO;
import com.schoolmgt.auth.grades.dto.GradeResponseDTO;
import com.schoolmgt.auth.grades.entity.Grade;

public interface GradeService extends BaseService<Grade>{
    GradeResponseDTO saveGrade(GradeRequestDTO gradeRequestDTO);
}
