package com.schoolmgt.auth.semester.service;

import com.schoolmgt.auth.base.service.BaseService;
import com.schoolmgt.auth.semester.dto.SemesterRequestDTO;
import com.schoolmgt.auth.semester.dto.SemesterResponseDTO;
import com.schoolmgt.auth.semester.entity.Semester;

public interface SemesterService extends BaseService<Semester>{
    SemesterResponseDTO addSemester(SemesterRequestDTO semesterRequestDTO);
}
