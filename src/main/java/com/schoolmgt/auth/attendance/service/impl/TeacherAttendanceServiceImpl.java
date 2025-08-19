package com.schoolmgt.auth.attendance.service.impl;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.attendance.dto.TeacherAttendanceRequestDTO;
import com.schoolmgt.auth.attendance.dto.TeacherAttendanceResponseDTO;
import com.schoolmgt.auth.attendance.entity.TeacherAttendance;
import com.schoolmgt.auth.attendance.repository.TeacherAttendanceRepository;
import com.schoolmgt.auth.attendance.service.TeacherAttendanceService;
import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TeacherAttendanceServiceImpl extends BaseServiceImpl<TeacherAttendance, TeacherAttendanceRepository> implements TeacherAttendanceService {

    private final TeacherAttendanceRepository teacherAttendanceRepository;

    public TeacherAttendanceServiceImpl(TeacherAttendanceRepository repository, TeacherAttendanceRepository teacherAttendanceRepository) {
        super(repository);
        this.teacherAttendanceRepository = teacherAttendanceRepository;
    }

    @Override
    public TeacherAttendanceResponseDTO recordAttendance(TeacherAttendanceRequestDTO teacherAttendanceRequestDTO) {
        TeacherAttendance teacherAttendance = new TeacherAttendance();
        teacherAttendance.setDate(teacherAttendanceRequestDTO.getDate());
        teacherAttendance.setPresent(teacherAttendanceRequestDTO.isPresent());
        teacherAttendance.setTeacherId(teacherAttendanceRequestDTO.getTeacherId());
        teacherAttendanceRepository.save(teacherAttendance);

        return convertToResponseDTO(teacherAttendance);
    }
    

    private TeacherAttendanceResponseDTO convertToResponseDTO(TeacherAttendance teacherAttendance) {
        TeacherAttendanceResponseDTO responseDTO = new TeacherAttendanceResponseDTO();
        responseDTO.setDate(teacherAttendance.getDate());
        responseDTO.setPresent(teacherAttendance.isPresent());
        responseDTO.setTeacherId(teacherAttendance.getTeacherId());
        
        return responseDTO;
    }
}
