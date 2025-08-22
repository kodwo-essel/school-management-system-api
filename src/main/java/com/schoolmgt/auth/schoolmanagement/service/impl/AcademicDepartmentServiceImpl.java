package com.schoolmgt.auth.schoolmanagement.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.schoolmanagement.dto.*;
import com.schoolmgt.auth.schoolmanagement.entity.AcademicDepartment;
import com.schoolmgt.auth.schoolmanagement.repository.AcademicDepartmentRepository;
import com.schoolmgt.auth.schoolmanagement.service.AcademicDepartmentService;
import com.schoolmgt.auth.utils.IdGenerators;

import jakarta.persistence.EntityNotFoundException;
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
        // Check if department name already exists in the school
        if (academicDepartmentRepository.existsByNameAndSchoolId(
                academicDepartmentRequestDTO.getName(), academicDepartmentRequestDTO.getSchoolId())) {
            throw new IllegalArgumentException("Department with name '" + academicDepartmentRequestDTO.getName() +
                "' already exists in this school");
        }

        AcademicDepartment academicDepartment = AcademicDepartment.builder()
                .name(academicDepartmentRequestDTO.getName())
                .schoolId(academicDepartmentRequestDTO.getSchoolId())
                .description(academicDepartmentRequestDTO.getDescription())
                .headOfDepartment(academicDepartmentRequestDTO.getHeadOfDepartment())
                .build();

        AcademicDepartment dept = academicDepartmentRepository.save(academicDepartment);

        if (dept.getDepartmentId() == null){
            String deptId = IdGenerators.generateDepartmentId(dept.getId(), dept.getSchoolId());
            dept.setDepartmentId(deptId);
            academicDepartmentRepository.save(dept);
        }

        return convertToResponseDTO(dept);
    }

    @Override
    public AcademicDepartmentResponseDTO getDepartmentById(String departmentId) {
        AcademicDepartment department = academicDepartmentRepository.findByDepartmentId(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + departmentId));
        return convertToResponseDTO(department);
    }

    @Override
    public List<AcademicDepartmentResponseDTO> getAllDepartments() {
        return academicDepartmentRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AcademicDepartmentResponseDTO> getDepartmentsBySchool(String schoolId) {
        return academicDepartmentRepository.findBySchoolId(schoolId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }



    @Override
    public List<AcademicDepartmentResponseDTO> searchDepartmentsByName(String name) {
        return academicDepartmentRepository.findByNameContaining(name).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AcademicDepartmentResponseDTO> getDepartmentsByHead(String headOfDepartment) {
        return academicDepartmentRepository.findByHeadOfDepartment(headOfDepartment).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AcademicDepartmentResponseDTO updateDepartment(String departmentId, DepartmentUpdateRequestDTO updateRequest) {
        AcademicDepartment department = academicDepartmentRepository.findByDepartmentId(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + departmentId));

        // Update fields if provided
        if (updateRequest.getName() != null) {
            department.setName(updateRequest.getName());
        }
        if (updateRequest.getDescription() != null) {
            department.setDescription(updateRequest.getDescription());
        }
        if (updateRequest.getHeadOfDepartment() != null) {
            department.setHeadOfDepartment(updateRequest.getHeadOfDepartment());
        }

        AcademicDepartment updatedDepartment = academicDepartmentRepository.save(department);
        return convertToResponseDTO(updatedDepartment);
    }



    @Override
    public AcademicDepartmentResponseDTO assignHeadOfDepartment(String departmentId, String teacherId) {
        AcademicDepartment department = academicDepartmentRepository.findByDepartmentId(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + departmentId));

        department.setHeadOfDepartment(teacherId);
        AcademicDepartment updatedDepartment = academicDepartmentRepository.save(department);
        return convertToResponseDTO(updatedDepartment);
    }

    @Override
    public void deleteDepartment(String departmentId) {
        AcademicDepartment department = academicDepartmentRepository.findByDepartmentId(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + departmentId));

        // Check if department has active classes before deletion
        // This would typically check with class service

        academicDepartmentRepository.delete(department);
    }


    @Override
    public DepartmentStatisticsDTO getDepartmentStatistics(String departmentId) {
        AcademicDepartment department = academicDepartmentRepository.findByDepartmentId(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + departmentId));

        // These would typically be calculated from related entities
        // For now, returning basic statistics
        return DepartmentStatisticsDTO.builder()
                .departmentId(departmentId)
                .departmentName(department.getName())
                .totalStudents(0) // Would need enrollment service integration
                .totalTeachers(0) // Would need teacher service integration
                .totalClasses(0) // Would need class service integration
                .averageClassSize(0.0)
                .performanceRating("AVERAGE")
                .averageGrade(0.0)
                .build();
    }





    @Override
    public boolean isDepartmentNameAvailable(String name, String schoolId) {
        return !academicDepartmentRepository.existsByNameAndSchoolId(name, schoolId);
    }



    private AcademicDepartmentResponseDTO convertToResponseDTO(AcademicDepartment academicDepartment) {
        return AcademicDepartmentResponseDTO.builder()
                .departmentId(academicDepartment.getDepartmentId())
                .name(academicDepartment.getName())
                .schoolId(academicDepartment.getSchoolId())
                .description(academicDepartment.getDescription())
                .headOfDepartment(academicDepartment.getHeadOfDepartment())
                .headOfDepartmentName("Head Name") // Would need teacher service integration
                .createdAt(academicDepartment.getCreatedAt())
                .updatedAt(academicDepartment.getUpdatedAt())
                .build();
    }
}
