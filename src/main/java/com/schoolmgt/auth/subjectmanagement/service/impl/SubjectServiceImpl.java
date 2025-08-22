package com.schoolmgt.auth.subjectmanagement.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.subjectmanagement.dto.*;
import com.schoolmgt.auth.subjectmanagement.entity.Subject;
import com.schoolmgt.auth.subjectmanagement.repository.SubjectRepository;
import com.schoolmgt.auth.subjectmanagement.service.SubjectService;
import com.schoolmgt.auth.utils.IdGenerators;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class SubjectServiceImpl extends BaseServiceImpl<Subject, SubjectRepository> implements SubjectService {

    private final SubjectRepository subjectRepository;
    
    public SubjectServiceImpl(SubjectRepository repository, SubjectRepository subjectRepository) {
        super(repository);
        this.subjectRepository = subjectRepository;
    }

    @Override
    public SubjectResponseDTO createSubject(SubjectRequestDTO requestDto) {
        // Check if subject name already exists in the school
        if (subjectRepository.existsByNameAndSchoolId(requestDto.getName(), requestDto.getSchoolId())) {
            throw new IllegalArgumentException("Subject with name '" + requestDto.getName() +
                "' already exists in this school");
        }

        // Basic validation is handled by the DTO annotations

        Subject subject = Subject.builder()
                .name(requestDto.getName())
                .schoolId(requestDto.getSchoolId())
                .departmentId(requestDto.getDepartmentId())
                .description(requestDto.getDescription())
                .build();

        Subject savedSubject = subjectRepository.save(subject);

        if (savedSubject.getSubjectId() == null) {
            String subjectId = IdGenerators.generateSubjectId(savedSubject.getId(), savedSubject.getSchoolId());
            savedSubject.setSubjectId(subjectId);
            savedSubject = subjectRepository.save(savedSubject);
        }
        return convertToResponseDto(savedSubject);
    }

    @Override
    public SubjectResponseDTO getSubjectById(String subjectId) {
        Subject subject = subjectRepository.findBySubjectId(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found with ID: " + subjectId));
        return convertToResponseDto(subject);
    }



    @Override
    public List<SubjectResponseDTO> getSubjectsBySchool(String schoolId) {
        return subjectRepository.findBySchoolId(schoolId).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }



    @Override
    public List<SubjectResponseDTO> getSubjectsByDepartment(String departmentId) {
        return subjectRepository.findByDepartmentId(departmentId).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }





    @Override
    public SubjectResponseDTO updateSubject(String subjectId, SubjectUpdateRequestDTO updateRequest) {
        Subject subject = subjectRepository.findBySubjectId(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found with ID: " + subjectId));

        // Update fields if provided
        if (updateRequest.getName() != null) {
            subject.setName(updateRequest.getName());
        }
        if (updateRequest.getDepartmentId() != null) {
            subject.setDepartmentId(updateRequest.getDepartmentId());
        }
        if (updateRequest.getDescription() != null) {
            subject.setDescription(updateRequest.getDescription());
        }

        Subject updatedSubject = subjectRepository.save(subject);
        return convertToResponseDto(updatedSubject);
    }



    @Override
    public void deleteSubject(String subjectId) {
        Subject subject = subjectRepository.findBySubjectId(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found with ID: " + subjectId));

        // Check if subject is assigned to any classes before deletion
        // This would require integration with ClassSubject service

        subjectRepository.delete(subject);
    }



    @Override
    public boolean isSubjectNameAvailable(String name, String schoolId) {
        return !subjectRepository.existsByNameAndSchoolId(name, schoolId);
    }



    private SubjectResponseDTO convertToResponseDto(Subject subject) {
        return SubjectResponseDTO.builder()
                .subjectId(subject.getSubjectId())
                .name(subject.getName())
                .schoolId(subject.getSchoolId())
                .departmentId(subject.getDepartmentId())
                .departmentName("Department Name") // Would need department service integration
                .description(subject.getDescription())
                .createdAt(subject.getCreatedAt())
                .updatedAt(subject.getUpdatedAt())
                .build();
    }
}
