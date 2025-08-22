package com.schoolmgt.auth.schoolmanagement.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.schoolmanagement.dto.SchoolClassRequestDTO;
import com.schoolmgt.auth.schoolmanagement.dto.SchoolClassResponseDTO;
import com.schoolmgt.auth.schoolmanagement.dto.SchoolClassUpdateRequestDTO;
import com.schoolmgt.auth.schoolmanagement.entity.SchoolClass;
import com.schoolmgt.auth.schoolmanagement.repository.SchoolClassRepository;
import com.schoolmgt.auth.schoolmanagement.service.SchoolClassService;
import com.schoolmgt.auth.utils.IdGenerators;

import jakarta.persistence.EntityNotFoundException;
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
        SchoolClass schoolClass = SchoolClass.builder()
                .name(schoolClassRequestDTO.getName())
                .schoolId(schoolClassRequestDTO.getSchoolId())
                .departmentId(schoolClassRequestDTO.getDepartmentId())
                .academicYear(schoolClassRequestDTO.getAcademicYear())
                .classTeacherId(schoolClassRequestDTO.getClassTeacherId())
                .capacity(schoolClassRequestDTO.getCapacity() != null ? schoolClassRequestDTO.getCapacity() : 30)
                .currentStudents(0)
                .build();

        SchoolClass savedSchoolClass = schoolClassRepository.save(schoolClass);

        if (savedSchoolClass.getClassId() == null) {
            String classId = IdGenerators.generateClassId(schoolClass.getId(), schoolClassRequestDTO.getDepartmentId());
            savedSchoolClass.setClassId(classId);
            schoolClassRepository.save(savedSchoolClass);
        }

        return convertToResponseDTO(savedSchoolClass);
    }

    @Override
    public SchoolClassResponseDTO getClassById(String classId) {
        SchoolClass schoolClass = schoolClassRepository.findByClassId(classId)
                .orElseThrow(() -> new EntityNotFoundException("Class not found with ID: " + classId));
        return convertToResponseDTO(schoolClass);
    }

    @Override
    public List<SchoolClassResponseDTO> getClassesBySchool(String schoolId) {
        return schoolClassRepository.findAll().stream()
                .filter(schoolClass -> schoolId.equals(schoolClass.getSchoolId()))
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SchoolClassResponseDTO> getClassesByDepartment(String departmentId) {
        return schoolClassRepository.findAll().stream()
                .filter(schoolClass -> departmentId.equals(schoolClass.getDepartmentId()))
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SchoolClassResponseDTO> getClassesByAcademicYear(String academicYear) {
        return schoolClassRepository.findAll().stream()
                .filter(schoolClass -> academicYear.equals(schoolClass.getAcademicYear()))
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SchoolClassResponseDTO> getClassesBySchoolAndAcademicYear(String schoolId, String academicYear) {
        return schoolClassRepository.findAll().stream()
                .filter(schoolClass -> schoolId.equals(schoolClass.getSchoolId()))
                .filter(schoolClass -> academicYear.equals(schoolClass.getAcademicYear()))
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SchoolClassResponseDTO> getClassesByTeacher(String teacherId) {
        return schoolClassRepository.findAll().stream()
                .filter(schoolClass -> teacherId.equals(schoolClass.getClassTeacherId()))
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SchoolClassResponseDTO updateClass(String classId, SchoolClassUpdateRequestDTO updateRequest) {
        SchoolClass schoolClass = schoolClassRepository.findByClassId(classId)
                .orElseThrow(() -> new EntityNotFoundException("Class not found with ID: " + classId));

        // Update fields if provided
        if (updateRequest.getName() != null) {
            schoolClass.setName(updateRequest.getName());
        }
        if (updateRequest.getDepartmentId() != null) {
            schoolClass.setDepartmentId(updateRequest.getDepartmentId());
        }
        if (updateRequest.getClassTeacherId() != null) {
            schoolClass.setClassTeacherId(updateRequest.getClassTeacherId());
        }
        if (updateRequest.getCapacity() != null) {
            schoolClass.setCapacity(updateRequest.getCapacity());
        }

        SchoolClass updatedClass = schoolClassRepository.save(schoolClass);
        return convertToResponseDTO(updatedClass);
    }

    @Override
    public SchoolClassResponseDTO assignClassTeacher(String classId, String teacherId) {
        SchoolClass schoolClass = schoolClassRepository.findByClassId(classId)
                .orElseThrow(() -> new EntityNotFoundException("Class not found with ID: " + classId));

        schoolClass.setClassTeacherId(teacherId);
        SchoolClass updatedClass = schoolClassRepository.save(schoolClass);
        return convertToResponseDTO(updatedClass);
    }

    @Override
    public void deleteClass(String classId) {
        SchoolClass schoolClass = schoolClassRepository.findByClassId(classId)
                .orElseThrow(() -> new EntityNotFoundException("Class not found with ID: " + classId));

        // Check if class has enrolled students before deletion
        if (schoolClass.getCurrentStudents() != null && schoolClass.getCurrentStudents() > 0) {
            throw new IllegalStateException("Cannot delete class with enrolled students");
        }

        schoolClassRepository.delete(schoolClass);
    }

    @Override
    public boolean isClassNameAvailable(String name, String academicYear) {
        return schoolClassRepository.findByNameAndAcademicYear(name, academicYear).isEmpty();
    }

    @Override
    public SchoolClassResponseDTO updateStudentCount(String classId, Integer studentCount) {
        SchoolClass schoolClass = schoolClassRepository.findByClassId(classId)
                .orElseThrow(() -> new EntityNotFoundException("Class not found with ID: " + classId));

        schoolClass.setCurrentStudents(studentCount);
        SchoolClass updatedClass = schoolClassRepository.save(schoolClass);
        return convertToResponseDTO(updatedClass);
    }

    private SchoolClassResponseDTO convertToResponseDTO(SchoolClass schoolClass) {
        Double utilizationRate = null;
        if (schoolClass.getCapacity() != null && schoolClass.getCapacity() > 0 && schoolClass.getCurrentStudents() != null) {
            utilizationRate = (schoolClass.getCurrentStudents().doubleValue() / schoolClass.getCapacity()) * 100;
        }

        return SchoolClassResponseDTO.builder()
                .classId(schoolClass.getClassId())
                .name(schoolClass.getName())
                .schoolId(schoolClass.getSchoolId())
                .departmentId(schoolClass.getDepartmentId())
                .departmentName("Department Name") // Would need department service integration
                .academicYear(schoolClass.getAcademicYear())
                .classTeacherId(schoolClass.getClassTeacherId())
                .classTeacherName("Teacher Name") // Would need teacher service integration
                .capacity(schoolClass.getCapacity())
                .currentStudents(schoolClass.getCurrentStudents())
                .utilizationRate(utilizationRate)
                .createdAt(schoolClass.getCreatedAt())
                .updatedAt(schoolClass.getUpdatedAt())
                .build();
    }
}
