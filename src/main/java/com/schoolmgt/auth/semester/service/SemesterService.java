package com.schoolmgt.auth.semester.service;

import java.util.List;

import com.schoolmgt.auth.base.service.BaseService;
import com.schoolmgt.auth.semester.dto.*;
import com.schoolmgt.auth.semester.entity.Semester;

public interface SemesterService extends BaseService<Semester>{

    SemesterResponseDTO addSemester(SemesterRequestDTO semesterRequestDTO);

    SemesterResponseDTO getSemesterById(String semesterId);

    List<SemesterResponseDTO> getSemestersBySchool(String schoolId);

    List<SemesterResponseDTO> getSemestersByAcademicYear(String academicYear);

    List<SemesterResponseDTO> getSemestersBySchoolAndAcademicYear(String schoolId, String academicYear);

    SemesterResponseDTO getCurrentSemester(String schoolId);

    SemesterResponseDTO updateSemester(String semesterId, SemesterUpdateRequestDTO updateRequest);

    SemesterResponseDTO setCurrentSemester(String semesterId);

    void deleteSemester(String semesterId);

    boolean isSemesterNameAvailable(String name, String schoolId, String academicYear);


}
