package com.schoolmgt.auth.schoolmanagement.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.schoolmanagement.dto.*;
import com.schoolmgt.auth.schoolmanagement.entity.School;
import com.schoolmgt.auth.schoolmanagement.repository.SchoolRepository;
import com.schoolmgt.auth.schoolmanagement.service.SchoolService;
import com.schoolmgt.auth.utils.IdGenerators;

import jakarta.persistence.EntityNotFoundException;
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
        // Check if school name already exists
        if (schoolRepository.existsByName(schoolRegistrationDTO.getName())) {
            throw new IllegalArgumentException("School with name '" + schoolRegistrationDTO.getName() + "' already exists");
        }

        School school = School.builder()
                .name(schoolRegistrationDTO.getName())
                .address(schoolRegistrationDTO.getAddress())
                .city(schoolRegistrationDTO.getCity())
                .region(schoolRegistrationDTO.getRegion())
                .country(schoolRegistrationDTO.getCountry())
                .contactEmail(schoolRegistrationDTO.getContactEmail())
                .contactPhone(schoolRegistrationDTO.getContactPhone())
                .website(schoolRegistrationDTO.getWebsite())
                .motto(schoolRegistrationDTO.getMotto())
                .logoUrl(schoolRegistrationDTO.getLogoUrl())
                .build();

        School savedSchool = schoolRepository.save(school);

        if (savedSchool.getSchoolId() == null) {
            savedSchool.setSchoolId(IdGenerators.generateSchoolId(savedSchool.getId()));
            savedSchool = schoolRepository.save(savedSchool);
        }

        return convertToResponseDTO(savedSchool);
    }

    @Override
    public SchoolResponseDTO getSchoolById(String schoolId) {
        School school = schoolRepository.findBySchoolId(schoolId)
                .orElseThrow(() -> new EntityNotFoundException("School not found with ID: " + schoolId));
        return convertToResponseDTO(school);
    }






    @Override
    public SchoolResponseDTO updateSchool(String schoolId, SchoolUpdateRequestDTO updateRequest) {
        School school = schoolRepository.findBySchoolId(schoolId)
                .orElseThrow(() -> new EntityNotFoundException("School not found with ID: " + schoolId));

        // Update fields if provided
        if (updateRequest.getName() != null) {
            // Check if new name conflicts with existing schools
            if (schoolRepository.existsByNameAndSchoolIdNot(updateRequest.getName(), schoolId)) {
                throw new IllegalArgumentException("School with name '" + updateRequest.getName() + "' already exists");
            }
            school.setName(updateRequest.getName());
        }
        if (updateRequest.getAddress() != null) school.setAddress(updateRequest.getAddress());
        if (updateRequest.getCity() != null) school.setCity(updateRequest.getCity());
        if (updateRequest.getRegion() != null) school.setRegion(updateRequest.getRegion());
        if (updateRequest.getCountry() != null) school.setCountry(updateRequest.getCountry());
        if (updateRequest.getContactEmail() != null) school.setContactEmail(updateRequest.getContactEmail());
        if (updateRequest.getContactPhone() != null) school.setContactPhone(updateRequest.getContactPhone());
        if (updateRequest.getWebsite() != null) school.setWebsite(updateRequest.getWebsite());
        if (updateRequest.getMotto() != null) school.setMotto(updateRequest.getMotto());
        if (updateRequest.getLogoUrl() != null) school.setLogoUrl(updateRequest.getLogoUrl());

        School updatedSchool = schoolRepository.save(school);
        return convertToResponseDTO(updatedSchool);
    }



    @Override
    public void deleteSchool(String schoolId) {
        School school = schoolRepository.findBySchoolId(schoolId)
                .orElseThrow(() -> new EntityNotFoundException("School not found with ID: " + schoolId));

        // Check if school has active data before deletion
        // This would typically check with student/teacher services

        schoolRepository.delete(school);
    }





    @Override
    public boolean isSchoolNameAvailable(String name) {
        return !schoolRepository.existsByName(name);
    }





    private SchoolResponseDTO convertToResponseDTO(School school) {
        return SchoolResponseDTO.builder()
                .schoolId(school.getSchoolId())
                .name(school.getName())
                .address(school.getAddress())
                .city(school.getCity())
                .region(school.getRegion())
                .country(school.getCountry())
                .contactEmail(school.getContactEmail())
                .contactPhone(school.getContactPhone())
                .website(school.getWebsite())
                .motto(school.getMotto())
                .logoUrl(school.getLogoUrl())
                .createdAt(school.getCreatedAt())
                .updatedAt(school.getUpdatedAt())
                .build();
    }
}
