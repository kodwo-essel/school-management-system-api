package com.schoolmgt.auth.schoolmanagement.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.schoolmanagement.dto.BulkStudentEnrollmentRequestDTO;
import com.schoolmgt.auth.schoolmanagement.dto.StudentClassEnrollmentRequestDTO;
import com.schoolmgt.auth.schoolmanagement.dto.StudentClassEnrollmentResponseDTO;
import com.schoolmgt.auth.schoolmanagement.entity.SchoolClass;
import com.schoolmgt.auth.schoolmanagement.entity.StudentClassEnrollment;
import com.schoolmgt.auth.schoolmanagement.repository.SchoolClassRepository;
import com.schoolmgt.auth.schoolmanagement.repository.StudentClassEnrollmentRepository;
import com.schoolmgt.auth.schoolmanagement.service.StudentClassEnrollmentService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class StudentClassEnrollmentServiceImpl extends BaseServiceImpl<StudentClassEnrollment, StudentClassEnrollmentRepository> 
        implements StudentClassEnrollmentService {

    private final StudentClassEnrollmentRepository enrollmentRepository;
    private final SchoolClassRepository schoolClassRepository;

    public StudentClassEnrollmentServiceImpl(StudentClassEnrollmentRepository enrollmentRepository,
                                           SchoolClassRepository schoolClassRepository) {
        super(enrollmentRepository);
        this.enrollmentRepository = enrollmentRepository;
        this.schoolClassRepository = schoolClassRepository;
    }

    @Override
    public StudentClassEnrollmentResponseDTO enrollStudent(StudentClassEnrollmentRequestDTO requestDTO) {
        // Check if student is already enrolled in any class for this academic year
        if (enrollmentRepository.existsByStudentIdAndAcademicYearAndStatus(
                requestDTO.getStudentId(), requestDTO.getAcademicYear(), "ACTIVE")) {
            throw new IllegalArgumentException("Student is already enrolled in a class for academic year: " + requestDTO.getAcademicYear());
        }

        // Get the school class
        SchoolClass schoolClass = schoolClassRepository.findByClassId(requestDTO.getClassId())
                .orElseThrow(() -> new EntityNotFoundException("School class not found with ID: " + requestDTO.getClassId()));

        // Check if enrollment already exists (including inactive ones)
        enrollmentRepository.findByStudentIdAndSchoolClass_ClassIdAndAcademicYear(
                requestDTO.getStudentId(), requestDTO.getClassId(), requestDTO.getAcademicYear())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Enrollment record already exists for this student, class, and academic year");
                });

        StudentClassEnrollment enrollment = StudentClassEnrollment.builder()
                .studentId(requestDTO.getStudentId())
                .schoolClass(schoolClass)
                .academicYear(requestDTO.getAcademicYear())
                .status(requestDTO.getStatus())
                .build();

        StudentClassEnrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return convertToResponseDTO(savedEnrollment);
    }

    @Override
    public List<StudentClassEnrollmentResponseDTO> enrollStudentsBulk(BulkStudentEnrollmentRequestDTO requestDTO) {
        // Get the school class
        SchoolClass schoolClass = schoolClassRepository.findByClassId(requestDTO.getClassId())
                .orElseThrow(() -> new EntityNotFoundException("School class not found with ID: " + requestDTO.getClassId()));

        List<StudentClassEnrollment> enrollments = requestDTO.getStudentIds().stream()
                .filter(studentId -> !enrollmentRepository.existsByStudentIdAndAcademicYearAndStatus(
                        studentId, requestDTO.getAcademicYear(), "ACTIVE"))
                .map(studentId -> StudentClassEnrollment.builder()
                        .studentId(studentId)
                        .schoolClass(schoolClass)
                        .academicYear(requestDTO.getAcademicYear())
                        .status(requestDTO.getStatus())
                        .build())
                .collect(Collectors.toList());

        List<StudentClassEnrollment> savedEnrollments = enrollmentRepository.saveAll(enrollments);
        return savedEnrollments.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentClassEnrollmentResponseDTO getStudentEnrollment(String studentId, String academicYear) {
        StudentClassEnrollment enrollment = enrollmentRepository.findByStudentIdAndAcademicYear(studentId, academicYear)
                .orElseThrow(() -> new EntityNotFoundException(
                    "No enrollment found for student: " + studentId + " in academic year: " + academicYear));
        return convertToResponseDTO(enrollment);
    }

    @Override
    public List<StudentClassEnrollmentResponseDTO> getStudentEnrollmentHistory(String studentId) {
        return enrollmentRepository.findByStudentId(studentId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentClassEnrollmentResponseDTO> getClassEnrollments(String classId, String academicYear) {
        return enrollmentRepository.findBySchoolClass_ClassIdAndAcademicYear(classId, academicYear).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentClassEnrollmentResponseDTO> getActiveClassEnrollments(String classId, String academicYear) {
        return enrollmentRepository.findBySchoolClass_ClassIdAndAcademicYearAndStatus(classId, academicYear, "ACTIVE").stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentClassEnrollmentResponseDTO updateEnrollmentStatus(String studentId, String classId, 
                                                                   String academicYear, String status) {
        StudentClassEnrollment enrollment = enrollmentRepository
                .findByStudentIdAndSchoolClass_ClassIdAndAcademicYear(studentId, classId, academicYear)
                .orElseThrow(() -> new EntityNotFoundException(
                    "No enrollment found for student: " + studentId + " in class: " + classId + " for year: " + academicYear));
        
        enrollment.setStatus(status);
        StudentClassEnrollment updatedEnrollment = enrollmentRepository.save(enrollment);
        return convertToResponseDTO(updatedEnrollment);
    }

    @Override
    public void transferStudent(String studentId, String fromClassId, String toClassId, String academicYear) {
        // Deactivate current enrollment
        updateEnrollmentStatus(studentId, fromClassId, academicYear, "TRANSFERRED");
        
        // Create new enrollment
        StudentClassEnrollmentRequestDTO newEnrollmentRequest = StudentClassEnrollmentRequestDTO.builder()
                .studentId(studentId)
                .classId(toClassId)
                .academicYear(academicYear)
                .status("ACTIVE")
                .build();
        
        enrollStudent(newEnrollmentRequest);
    }

    @Override
    public void withdrawStudent(String studentId, String classId, String academicYear) {
        updateEnrollmentStatus(studentId, classId, academicYear, "WITHDRAWN");
    }

    @Override
    public long getClassEnrollmentCount(String classId, String academicYear) {
        return enrollmentRepository.findBySchoolClass_ClassIdAndAcademicYearAndStatus(classId, academicYear, "ACTIVE").size();
    }

    @Override
    public boolean isStudentEnrolledInClass(String studentId, String classId, String academicYear) {
        return enrollmentRepository.findByStudentIdAndSchoolClass_ClassIdAndAcademicYear(studentId, classId, academicYear)
                .map(enrollment -> "ACTIVE".equals(enrollment.getStatus()))
                .orElse(false);
    }

    private StudentClassEnrollmentResponseDTO convertToResponseDTO(StudentClassEnrollment enrollment) {
        return StudentClassEnrollmentResponseDTO.builder()
                .id(enrollment.getId())
                .studentId(enrollment.getStudentId())
                .classId(enrollment.getSchoolClass().getClassId())
                .className(enrollment.getSchoolClass().getName())
                .academicYear(enrollment.getAcademicYear())
                .enrollmentDate(enrollment.getEnrollmentDate())
                .status(enrollment.getStatus())
                .build();
    }
}
