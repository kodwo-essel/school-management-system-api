package com.schoolmgt.auth.schoolmanagement.service.impl;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.schoolmanagement.dto.SchoolClassRequestDTO;
import com.schoolmgt.auth.schoolmanagement.dto.SchoolClassResponseDTO;
import com.schoolmgt.auth.schoolmanagement.entity.SchoolClass;
import com.schoolmgt.auth.schoolmanagement.repository.SchoolClassRepository;
import com.schoolmgt.auth.schoolmanagement.service.SchoolClassService;
import com.schoolmgt.auth.utils.IdGenerators;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class SchoolClassServiceImpl extends BaseServiceImpl<SchoolClass, SchoolClassRepository> implements SchoolClassService {

    private SchoolClassRepository schoolClassRepository;
    
    public SchoolClassServiceImpl(SchoolClassRepository repository, SchoolClassRepository schoolClassRepository) {
        super(repository);
        this.schoolClassRepository = schoolClassRepository;
    }

    @Override
    public SchoolClassResponseDTO addSchoolClass(SchoolClassRequestDTO schoolClassRequestDTO) {
        SchoolClass schoolClass = new SchoolClass();

        schoolClass.setName(schoolClassRequestDTO.getName());
        schoolClass.setSchoolId(schoolClassRequestDTO.getSchoolId());
        schoolClass.setAcademicYear(schoolClassRequestDTO.getAcademicYear());
        
        SchoolClass savedSchoolClass = schoolClassRepository.save(schoolClass);

        if (savedSchoolClass.getClassId() == null) {
            String classId = IdGenerators.generateClassId(schoolClass.getId(), schoolClassRequestDTO.getSchoolId());
            savedSchoolClass.setClassId(classId);
            schoolClassRepository.save(savedSchoolClass);
        }

        return convertToResponseDTO(savedSchoolClass);
    }

    private SchoolClassResponseDTO convertToResponseDTO(SchoolClass savedSchoolClass) {
        SchoolClassResponseDTO schoolClassResponseDTO = new SchoolClassResponseDTO();
        schoolClassResponseDTO.setName(savedSchoolClass.getName());
        schoolClassResponseDTO.setSchoolId(savedSchoolClass.getSchoolId());
        schoolClassResponseDTO.setClassId(savedSchoolClass.getClassId());
        schoolClassResponseDTO.setAcademicYear(savedSchoolClass.getAcademicYear());
        schoolClassResponseDTO.setCreatedAt(savedSchoolClass.getCreatedAt());
        schoolClassResponseDTO.setUpdatedAt(savedSchoolClass.getUpdatedAt());
        return schoolClassResponseDTO;
    }
}
