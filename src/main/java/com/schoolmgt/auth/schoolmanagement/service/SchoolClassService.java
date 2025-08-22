package com.schoolmgt.auth.schoolmanagement.service;

import java.util.List;

import com.schoolmgt.auth.base.service.BaseService;
import com.schoolmgt.auth.schoolmanagement.dto.SchoolClassRequestDTO;
import com.schoolmgt.auth.schoolmanagement.dto.SchoolClassResponseDTO;
import com.schoolmgt.auth.schoolmanagement.dto.SchoolClassUpdateRequestDTO;
import com.schoolmgt.auth.schoolmanagement.entity.SchoolClass;

public interface SchoolClassService extends BaseService<SchoolClass>{

    SchoolClassResponseDTO addSchoolClass(SchoolClassRequestDTO schoolClassRequestDTO);

    SchoolClassResponseDTO getClassById(String classId);

    List<SchoolClassResponseDTO> getClassesBySchool(String schoolId);

    List<SchoolClassResponseDTO> getClassesByDepartment(String departmentId);

    List<SchoolClassResponseDTO> getClassesByAcademicYear(String academicYear);

    List<SchoolClassResponseDTO> getClassesBySchoolAndAcademicYear(String schoolId, String academicYear);

    List<SchoolClassResponseDTO> getClassesByTeacher(String teacherId);

    SchoolClassResponseDTO updateClass(String classId, SchoolClassUpdateRequestDTO updateRequest);

    SchoolClassResponseDTO assignClassTeacher(String classId, String teacherId);

    void deleteClass(String classId);

    boolean isClassNameAvailable(String name, String academicYear);

    SchoolClassResponseDTO updateStudentCount(String classId, Integer studentCount);
}
