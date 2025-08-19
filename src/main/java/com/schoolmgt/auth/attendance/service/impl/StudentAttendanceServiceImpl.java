package com.schoolmgt.auth.attendance.service.impl;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.attendance.dto.StudentAttendanceRequestDTO;
import com.schoolmgt.auth.attendance.dto.StudentAttendanceResponseDTO;
import com.schoolmgt.auth.attendance.entity.StudentAttendance;
import com.schoolmgt.auth.attendance.repository.StudentAttendanceRepository;
import com.schoolmgt.auth.attendance.service.StudentAttendanceService;
import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class StudentAttendanceServiceImpl extends BaseServiceImpl<StudentAttendance, StudentAttendanceRepository> implements StudentAttendanceService {

    private final StudentAttendanceRepository studentAttendanceRepository;

    public StudentAttendanceServiceImpl(StudentAttendanceRepository repository, StudentAttendanceRepository studentAttendanceRepository) {
        super(repository);
        this.studentAttendanceRepository = studentAttendanceRepository;
    }

    @Override
    public StudentAttendanceResponseDTO recordAttendance(StudentAttendanceRequestDTO studentAttendanceRequestDTO) {
        StudentAttendance studentAttendance = new StudentAttendance();
        studentAttendance.setStudentId(studentAttendanceRequestDTO.getStudentId());
        studentAttendance.setDate(studentAttendanceRequestDTO.getDate());
        studentAttendance.setPresent(studentAttendanceRequestDTO.isPresent());
        studentAttendance.setTeacherId(studentAttendanceRequestDTO.getTeacherId());
        studentAttendanceRepository.save(studentAttendance);

        return convertToResponseDTO(studentAttendance);
    }
    

    private StudentAttendanceResponseDTO convertToResponseDTO(StudentAttendance studentAttendance) {
        StudentAttendanceResponseDTO responseDTO = new StudentAttendanceResponseDTO();
        responseDTO.setStudentId(studentAttendance.getStudentId());
        responseDTO.setDate(studentAttendance.getDate());
        responseDTO.setPresent(studentAttendance.isPresent());
        responseDTO.setTeacherId(studentAttendance.getTeacherId());
        
        return responseDTO;
    }
}
