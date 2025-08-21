package com.schoolmgt.auth.schoolmanagement.service.impl;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.schoolmanagement.dto.AcademicDepartmentRequestDTO;
import com.schoolmgt.auth.schoolmanagement.dto.AcademicDepartmentResponseDTO;
import com.schoolmgt.auth.schoolmanagement.entity.AcademicDepartment;
import com.schoolmgt.auth.schoolmanagement.repository.AcademicDepartmentRepository;
import com.schoolmgt.auth.schoolmanagement.service.AcademicDepartmentService;
import com.schoolmgt.auth.utils.IdGenerators;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AcademicDepartmentServiceImpl extends BaseServiceImpl<AcademicDepartment, AcademicDepartmentRepository> implements AcademicDepartmentService{
    
    private AcademicDepartmentRepository academicDepartmentRepository;

    public AcademicDepartmentServiceImpl(AcademicDepartmentRepository academicDepartmentRepository) {
        super(academicDepartmentRepository);
        this.academicDepartmentRepository = academicDepartmentRepository;
    }

    @Override
    public AcademicDepartmentResponseDTO createAcademicDepartment(AcademicDepartmentRequestDTO academicDepartmentRequestDTO){
        AcademicDepartment academicDepartment = new AcademicDepartment();
        academicDepartment.setName(academicDepartmentRequestDTO.getName());
        academicDepartment.setSchoolId(academicDepartmentRequestDTO.getSchoolId());
        academicDepartment.setDescription(academicDepartmentRequestDTO.getDescription());

        AcademicDepartment dept = academicDepartmentRepository.save(academicDepartment);

        if (dept.getDepartmentId() == null){
            String deptId = IdGenerators.generateDepartmentId(dept.getId(), dept.getSchoolId());
            dept.setDepartmentId(deptId);
            academicDepartmentRepository.save(dept);
        }


        return convertToResponseDTO(dept);
    }


    private AcademicDepartmentResponseDTO convertToResponseDTO(AcademicDepartment academicDepartment) {
        AcademicDepartmentResponseDTO responseDTO = new AcademicDepartmentResponseDTO();
        responseDTO.setDepartmentId(academicDepartment.getDepartmentId());
        responseDTO.setName(academicDepartment.getName());
        responseDTO.setSchoolId(academicDepartment.getSchoolId());
        responseDTO.setDescription(academicDepartment.getDescription());
        return responseDTO;
    }
}
