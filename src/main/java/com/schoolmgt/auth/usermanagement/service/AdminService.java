package com.schoolmgt.auth.usermanagement.service;

import com.schoolmgt.auth.base.service.BaseService;
import com.schoolmgt.auth.usermanagement.dto.StudentRegistrationDTO;
import com.schoolmgt.auth.usermanagement.dto.StudentResponseDTO;
import com.schoolmgt.auth.usermanagement.dto.TeacherRegistrationDTO;
import com.schoolmgt.auth.usermanagement.dto.TeacherResponseDTO;
import com.schoolmgt.auth.usermanagement.entity.Admin;

public interface AdminService extends BaseService<Admin>{
    TeacherResponseDTO registerTeacher(TeacherRegistrationDTO teacherRegistrationDTO);
    StudentResponseDTO registerStudent(StudentRegistrationDTO studentRegistrationDTO);
}
