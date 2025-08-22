package com.schoolmgt.auth.fees.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.fees.dto.StudentFeeAssessmentRequestDTO;
import com.schoolmgt.auth.fees.dto.StudentFeeAssessmentResponseDTO;
import com.schoolmgt.auth.fees.dto.StudentFeeAssessmentItemResponseDTO;
import com.schoolmgt.auth.fees.entity.StudentFeeAssessment;
import com.schoolmgt.auth.fees.entity.StudentFeeAssessmentItem;
import com.schoolmgt.auth.fees.entity.FeeStructureItem;
import com.schoolmgt.auth.fees.repository.StudentFeeAssessmentRepository;
import com.schoolmgt.auth.fees.repository.StudentFeeAssessmentItemRepository;
import com.schoolmgt.auth.fees.repository.FeeStructureItemRepository;
import com.schoolmgt.auth.fees.service.StudentFeeAssessmentService;
import com.schoolmgt.auth.utils.IdGenerators;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class StudentFeeAssessmentServiceImpl extends BaseServiceImpl<StudentFeeAssessment, StudentFeeAssessmentRepository> 
        implements StudentFeeAssessmentService {

    private final StudentFeeAssessmentRepository assessmentRepository;
    private final StudentFeeAssessmentItemRepository assessmentItemRepository;
    private final FeeStructureItemRepository feeStructureItemRepository;

    public StudentFeeAssessmentServiceImpl(StudentFeeAssessmentRepository assessmentRepository,
                                           StudentFeeAssessmentItemRepository assessmentItemRepository,
                                           FeeStructureItemRepository feeStructureItemRepository) {
        super(assessmentRepository);
        this.assessmentRepository = assessmentRepository;
        this.assessmentItemRepository = assessmentItemRepository;
        this.feeStructureItemRepository = feeStructureItemRepository;
    }

    @Override
    public StudentFeeAssessmentResponseDTO createAssessment(StudentFeeAssessmentRequestDTO requestDTO) {
        // Check if assessment already exists for this student, fee structure, and term
        if (assessmentRepository.findByStudentIdAndFeeStructureIdAndTerm(
                requestDTO.getStudentId(), requestDTO.getFeeStructureId(), requestDTO.getTerm()).isPresent()) {
            throw new IllegalArgumentException("Assessment already exists for this student, fee structure, and term");
        }

        // Get fee structure items
        List<FeeStructureItem> feeStructureItems = feeStructureItemRepository
                .findByFeeStructureId(requestDTO.getFeeStructureId());

        if (feeStructureItems.isEmpty()) {
            throw new IllegalArgumentException("No fee structure items found for fee structure: " + requestDTO.getFeeStructureId());
        }

        // Calculate total amount
        BigDecimal totalAmount = feeStructureItems.stream()
                .map(FeeStructureItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        StudentFeeAssessment assessment = StudentFeeAssessment.builder()
                .studentId(requestDTO.getStudentId())
                .classId(requestDTO.getClassId())
                .academicYear(requestDTO.getAcademicYear())
                .term(requestDTO.getTerm())
                .feeStructureId(requestDTO.getFeeStructureId())
                .assessmentDate(LocalDateTime.now())
                .dueDate(requestDTO.getDueDate())
                .totalAmount(totalAmount)
                .discountAmount(BigDecimal.ZERO)
                .finalAmount(totalAmount)
                .status("PENDING")
                .build();

        StudentFeeAssessment savedAssessment = assessmentRepository.save(assessment);

        // Generate assessment ID
        if (savedAssessment.getAssessmentId() == null) {
            String assessmentId = IdGenerators.generateAssessmentId(savedAssessment.getId(), requestDTO.getStudentId());
            savedAssessment.setAssessmentId(assessmentId);
           assessmentRepository.save(savedAssessment);
        }

        // Create assessment items
        List<StudentFeeAssessmentItem> assessmentItems = feeStructureItems.stream()
                .map(feeItem -> StudentFeeAssessmentItem.builder()
                        .assessmentId(savedAssessment.getAssessmentId())
                        .feeTypeId(feeItem.getFeeTypeId())
                        .originalAmount(feeItem.getAmount())
                        .discountAmount(BigDecimal.ZERO)
                        .finalAmount(feeItem.getAmount())
                        .description(feeItem.getDescription())
                        .build())
                .collect(Collectors.toList());

        assessmentItemRepository.saveAll(assessmentItems);

        return convertToResponseDTO(savedAssessment);
    }

    @Override
    public StudentFeeAssessmentResponseDTO getAssessmentById(String assessmentId) {
        StudentFeeAssessment assessment = assessmentRepository.findByAssessmentId(assessmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assessment not found with ID: " + assessmentId));
        return convertToResponseDTO(assessment);
    }

    @Override
    public List<StudentFeeAssessmentResponseDTO> getAssessmentsByStudent(String studentId) {
        return assessmentRepository.findByStudentId(studentId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentFeeAssessmentResponseDTO> getAssessmentsByStudentAndYear(String studentId, String academicYear) {
        return assessmentRepository.findByStudentIdAndAcademicYear(studentId, academicYear).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentFeeAssessmentResponseDTO> getAssessmentsByClass(String classId, String academicYear, String term) {
        return assessmentRepository.findByClassIdAndAcademicYearAndTerm(classId, academicYear, term).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentFeeAssessmentResponseDTO> getPendingAssessments() {
        return assessmentRepository.findByStatus("PENDING").stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentFeeAssessmentResponseDTO updateAssessmentStatus(String assessmentId, String status) {
        StudentFeeAssessment assessment = assessmentRepository.findByAssessmentId(assessmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assessment not found with ID: " + assessmentId));
        
        assessment.setStatus(status);
        StudentFeeAssessment updatedAssessment = assessmentRepository.save(assessment);
        return convertToResponseDTO(updatedAssessment);
    }

    @Override
    public void deleteAssessment(String assessmentId) {
        StudentFeeAssessment assessment = assessmentRepository.findByAssessmentId(assessmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assessment not found with ID: " + assessmentId));
        
        // Delete assessment items first
        assessmentItemRepository.deleteByAssessmentId(assessmentId);
        
        // Delete assessment
        assessmentRepository.delete(assessment);
    }

    @Override
    public List<StudentFeeAssessmentResponseDTO> generateBulkAssessments(String classId, String academicYear, 
                                                                         String term, String feeStructureId) {
        // This would typically get all students in the class and create assessments for each
        // For now, returning empty list as we'd need student repository integration
        throw new UnsupportedOperationException("Bulk assessment generation requires student repository integration");
    }

    private StudentFeeAssessmentResponseDTO convertToResponseDTO(StudentFeeAssessment assessment) {
        List<StudentFeeAssessmentItem> items = assessmentItemRepository
                .findByAssessmentId(assessment.getAssessmentId());
        
        List<StudentFeeAssessmentItemResponseDTO> itemDTOs = items.stream()
                .map(item -> StudentFeeAssessmentItemResponseDTO.builder()
                        .id(item.getId())
                        .feeTypeId(item.getFeeTypeId())
                        .originalAmount(item.getOriginalAmount())
                        .discountAmount(item.getDiscountAmount())
                        .finalAmount(item.getFinalAmount())
                        .description(item.getDescription())
                        .build())
                .collect(Collectors.toList());

        return StudentFeeAssessmentResponseDTO.builder()
                .assessmentId(assessment.getAssessmentId())
                .studentId(assessment.getStudentId())
                .classId(assessment.getClassId())
                .academicYear(assessment.getAcademicYear())
                .term(assessment.getTerm())
                .feeStructureId(assessment.getFeeStructureId())
                .assessmentDate(assessment.getAssessmentDate())
                .dueDate(assessment.getDueDate())
                .totalAmount(assessment.getTotalAmount())
                .discountAmount(assessment.getDiscountAmount())
                .finalAmount(assessment.getFinalAmount())
                .status(assessment.getStatus())
                .assessmentItems(itemDTOs)
                .build();
    }
}
