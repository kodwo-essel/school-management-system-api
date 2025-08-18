package com.schoolmgt.auth.schoolmanagement.service.impl;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.schoolmanagement.dto.SchoolRegistrationDTO;
import com.schoolmgt.auth.schoolmanagement.dto.SchoolResponseDTO;
import com.schoolmgt.auth.schoolmanagement.entity.School;
import com.schoolmgt.auth.schoolmanagement.repository.SchoolRepository;
import com.schoolmgt.auth.schoolmanagement.service.SchoolService;
import com.schoolmgt.auth.utils.IdGenerators;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class SchoolServiceImpl extends BaseServiceImpl<School, SchoolRepository> implements SchoolService{

    private final SchoolRepository schoolRepository;

    
    public SchoolServiceImpl(SchoolRepository repository, SchoolRepository schoolRepository) {
        super(repository);
        this.schoolRepository = schoolRepository;
    }

    @Override
    public SchoolResponseDTO registerSchool(SchoolRegistrationDTO schoolRegistrationDTO) {
        School school = new School();
        school.setName(schoolRegistrationDTO.getName());
        school.setCountry(schoolRegistrationDTO.getCountry());
        school.setAddress(schoolRegistrationDTO.getAddress());
        school.setCity(schoolRegistrationDTO.getCity());
        school.setRegion(schoolRegistrationDTO.getRegion());

        School savedSchool = schoolRepository.save(school);
        
        if (savedSchool.getSchoolId() == null) {
            savedSchool.setSchoolId(IdGenerators.generateSchoolId(savedSchool.getId()));
            savedSchool = schoolRepository.save(savedSchool);
        }
        
        return convertToResponseDTO(savedSchool);
    }


    private SchoolResponseDTO convertToResponseDTO(School school) {
        SchoolResponseDTO responseDTO = new SchoolResponseDTO();
        responseDTO.setSchoolId(school.getSchoolId());
        responseDTO.setName(school.getName());
        responseDTO.setCountry(school.getCountry());
        responseDTO.setAddress(school.getAddress());
        responseDTO.setCity(school.getCity());
        responseDTO.setRegion(school.getRegion());
        return responseDTO;
    }
}
