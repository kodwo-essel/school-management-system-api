package com.schoolmgt.auth.semester.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.semester.dto.*;
import com.schoolmgt.auth.semester.entity.Semester;
import com.schoolmgt.auth.semester.repository.SemesterRepository;
import com.schoolmgt.auth.semester.service.SemesterService;
import com.schoolmgt.auth.utils.IdGenerators;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SemesterServiceImpl extends BaseServiceImpl<Semester, SemesterRepository> implements SemesterService{

    private final SemesterRepository semesterRepository;
    
    public SemesterServiceImpl(SemesterRepository semesterRepository) {
        super(semesterRepository);
        this.semesterRepository = semesterRepository;
    }

    @Override
    public SemesterResponseDTO addSemester(SemesterRequestDTO semesterRequestDTO) {
        // Check if semester already exists
        if (semesterRepository.existsByNameAndSchoolIdAndAcademicYear(
                semesterRequestDTO.getName(),
                semesterRequestDTO.getSchoolId(),
                semesterRequestDTO.getAcademicYear())) {
            throw new IllegalArgumentException("Semester with name '" + semesterRequestDTO.getName() +
                "' already exists for academic year " + semesterRequestDTO.getAcademicYear());
        }

        Semester semester = Semester.builder()
                .name(semesterRequestDTO.getName())
                .academicYear(semesterRequestDTO.getAcademicYear())
                .schoolId(semesterRequestDTO.getSchoolId())
                .description(semesterRequestDTO.getDescription())
                .startDate(semesterRequestDTO.getStartDate())
                .endDate(semesterRequestDTO.getEndDate())
                .isCurrentSemester(false)
                .build();

        Semester savedSemester = semesterRepository.save(semester);

        if (savedSemester.getSemesterId() == null) {
            String semId = IdGenerators.generateSemesterId(savedSemester.getId(), savedSemester.getSchoolId());
            savedSemester.setSemesterId(semId);
            savedSemester = semesterRepository.save(savedSemester);
        }

        return convertToResponseDTO(savedSemester);
    }

    @Override
    public SemesterResponseDTO getSemesterById(String semesterId) {
        Semester semester = semesterRepository.findBySemesterId(semesterId)
                .orElseThrow(() -> new EntityNotFoundException("Semester not found with ID: " + semesterId));
        return convertToResponseDTO(semester);
    }



    @Override
    public List<SemesterResponseDTO> getSemestersBySchool(String schoolId) {
        return semesterRepository.findBySchoolId(schoolId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SemesterResponseDTO> getSemestersByAcademicYear(String academicYear) {
        return semesterRepository.findByAcademicYear(academicYear).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SemesterResponseDTO> getSemestersBySchoolAndAcademicYear(String schoolId, String academicYear) {
        return semesterRepository.findBySchoolIdAndAcademicYear(schoolId, academicYear).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }





    @Override
    public SemesterResponseDTO getCurrentSemester(String schoolId) {
        Semester semester = semesterRepository.findCurrentSemesterBySchool(schoolId, LocalDate.now())
                .orElseThrow(() -> new EntityNotFoundException("No current semester found for school: " + schoolId));
        return convertToResponseDTO(semester);
    }




    @Override
    public SemesterResponseDTO updateSemester(String semesterId, SemesterUpdateRequestDTO updateRequest) {
        Semester semester = semesterRepository.findBySemesterId(semesterId)
                .orElseThrow(() -> new EntityNotFoundException("Semester not found with ID: " + semesterId));

        // Update fields if provided
        if (updateRequest.getName() != null) {
            semester.setName(updateRequest.getName());
        }
        if (updateRequest.getDescription() != null) {
            semester.setDescription(updateRequest.getDescription());
        }
        if (updateRequest.getStartDate() != null) {
            semester.setStartDate(updateRequest.getStartDate());
        }
        if (updateRequest.getEndDate() != null) {
            semester.setEndDate(updateRequest.getEndDate());
        }

        Semester updatedSemester = semesterRepository.save(semester);
        return convertToResponseDTO(updatedSemester);
    }



    @Override
    public SemesterResponseDTO setCurrentSemester(String semesterId) {
        Semester semester = semesterRepository.findBySemesterId(semesterId)
                .orElseThrow(() -> new EntityNotFoundException("Semester not found with ID: " + semesterId));

        // First, unset all current semesters for this school
        List<Semester> currentSemesters = semesterRepository.findBySchoolId(semester.getSchoolId()).stream()
                .filter(s -> Boolean.TRUE.equals(s.getIsCurrentSemester()))
                .collect(Collectors.toList());

        currentSemesters.forEach(s -> s.setIsCurrentSemester(false));
        semesterRepository.saveAll(currentSemesters);

        // Set this semester as current
        semester.setIsCurrentSemester(true);
        Semester updatedSemester = semesterRepository.save(semester);
        return convertToResponseDTO(updatedSemester);
    }

    @Override
    public void deleteSemester(String semesterId) {
        Semester semester = semesterRepository.findBySemesterId(semesterId)
                .orElseThrow(() -> new EntityNotFoundException("Semester not found with ID: " + semesterId));

        // Check if semester is current before deletion
        if (Boolean.TRUE.equals(semester.getIsCurrentSemester())) {
            throw new IllegalStateException("Cannot delete current semester");
        }

        semesterRepository.delete(semester);
    }





    @Override
    public boolean isSemesterNameAvailable(String name, String schoolId, String academicYear) {
        return !semesterRepository.existsByNameAndSchoolIdAndAcademicYear(name, schoolId, academicYear);
    }



    private SemesterResponseDTO convertToResponseDTO(Semester semester) {
        return SemesterResponseDTO.builder()
                .semesterId(semester.getSemesterId())
                .name(semester.getName())
                .academicYear(semester.getAcademicYear())
                .schoolId(semester.getSchoolId())
                .description(semester.getDescription())
                .startDate(semester.getStartDate())
                .endDate(semester.getEndDate())
                .isCurrentSemester(semester.getIsCurrentSemester())
                .createdAt(semester.getCreatedAt())
                .updatedAt(semester.getUpdatedAt())
                .build();
    }
}
