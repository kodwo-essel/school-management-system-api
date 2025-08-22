package com.schoolmgt.auth.fees.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.fees.dto.FeeStructureRequestDTO;
import com.schoolmgt.auth.fees.dto.FeeStructureResponseDTO;
import com.schoolmgt.auth.fees.dto.FeeStructureItemResponseDTO;
import com.schoolmgt.auth.fees.entity.FeeStructure;
import com.schoolmgt.auth.fees.entity.FeeStructureItem;
import com.schoolmgt.auth.fees.repository.FeeStructureRepository;
import com.schoolmgt.auth.fees.repository.FeeStructureItemRepository;
import com.schoolmgt.auth.fees.service.FeeStructureService;
import com.schoolmgt.auth.utils.IdGenerators;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class FeeStructureServiceImpl extends BaseServiceImpl<FeeStructure, FeeStructureRepository> implements FeeStructureService {

    private final FeeStructureRepository feeStructureRepository;
    private final FeeStructureItemRepository feeStructureItemRepository;

    public FeeStructureServiceImpl(FeeStructureRepository feeStructureRepository, 
                                   FeeStructureItemRepository feeStructureItemRepository) {
        super(feeStructureRepository);
        this.feeStructureRepository = feeStructureRepository;
        this.feeStructureItemRepository = feeStructureItemRepository;
    }

    @Override
    public FeeStructureResponseDTO createFeeStructure(FeeStructureRequestDTO requestDTO) {
        // Get the next version number for this class and academic year
        Integer nextVersion = getNextVersionNumber(requestDTO.getClassId(), requestDTO.getAcademicYear());

        FeeStructure feeStructure = FeeStructure.builder()
                .classId(requestDTO.getClassId())
                .academicYear(requestDTO.getAcademicYear())
                .version(nextVersion)
                .effectiveDate(requestDTO.getEffectiveDate())
                .expiryDate(requestDTO.getExpiryDate())
                .status(requestDTO.getStatus())
                .build();

        FeeStructure savedFeeStructure = feeStructureRepository.save(feeStructure);

        // Generate fee structure ID
        if (savedFeeStructure.getFeeStructureId() == null) {
            String feeStructureId = IdGenerators.generateFeeStructureId(
                savedFeeStructure.getId(), 
                requestDTO.getClassId(), 
                nextVersion
            );
            savedFeeStructure.setFeeStructureId(feeStructureId);
            feeStructureRepository.save(savedFeeStructure);
        }

        // Create fee structure items
        List<FeeStructureItem> feeItems = requestDTO.getFeeItems().stream()
                .map(itemDTO -> FeeStructureItem.builder()
                        .feeStructureId(savedFeeStructure.getFeeStructureId())
                        .feeTypeId(itemDTO.getFeeTypeId())
                        .amount(itemDTO.getAmount())
                        .currency(itemDTO.getCurrency())
                        .mandatory(itemDTO.getMandatory())
                        .description(itemDTO.getDescription())
                        .build())
                .collect(Collectors.toList());

        feeStructureItemRepository.saveAll(feeItems);

        return convertToResponseDTO(savedFeeStructure);
    }

    @Override
    public FeeStructureResponseDTO getFeeStructureById(String feeStructureId) {
        FeeStructure feeStructure = feeStructureRepository.findByFeeStructureId(feeStructureId)
                .orElseThrow(() -> new EntityNotFoundException("Fee structure not found with ID: " + feeStructureId));
        return convertToResponseDTO(feeStructure);
    }

    @Override
    public List<FeeStructureResponseDTO> getFeeStructuresByClass(String classId, String academicYear) {
        return feeStructureRepository.findByClassIdAndAcademicYear(classId, academicYear).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FeeStructureResponseDTO getActiveFeeStructureForClass(String classId, String academicYear) {
        FeeStructure feeStructure = feeStructureRepository
                .findByClassIdAndAcademicYearAndStatusOrderByVersionDesc(classId, academicYear, "ACTIVE")
                .orElseThrow(() -> new EntityNotFoundException(
                    "No active fee structure found for class: " + classId + " and year: " + academicYear));
        return convertToResponseDTO(feeStructure);
    }

    @Override
    public List<FeeStructureResponseDTO> getAllActiveFeeStructures() {
        return feeStructureRepository.findByStatus("ACTIVE").stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FeeStructureResponseDTO updateFeeStructure(String feeStructureId, FeeStructureRequestDTO requestDTO) {
        FeeStructure feeStructure = feeStructureRepository.findByFeeStructureId(feeStructureId)
                .orElseThrow(() -> new EntityNotFoundException("Fee structure not found with ID: " + feeStructureId));

        feeStructure.setEffectiveDate(requestDTO.getEffectiveDate());
        feeStructure.setExpiryDate(requestDTO.getExpiryDate());
        feeStructure.setStatus(requestDTO.getStatus());

        FeeStructure updatedFeeStructure = feeStructureRepository.save(feeStructure);

        // Update fee structure items
        feeStructureItemRepository.deleteByFeeStructureId(feeStructureId);
        
        List<FeeStructureItem> updatedItems = requestDTO.getFeeItems().stream()
                .map(itemDTO -> FeeStructureItem.builder()
                        .feeStructureId(feeStructureId)
                        .feeTypeId(itemDTO.getFeeTypeId())
                        .amount(itemDTO.getAmount())
                        .currency(itemDTO.getCurrency())
                        .mandatory(itemDTO.getMandatory())
                        .description(itemDTO.getDescription())
                        .build())
                .collect(Collectors.toList());

        feeStructureItemRepository.saveAll(updatedItems);

        return convertToResponseDTO(updatedFeeStructure);
    }

    @Override
    public void deactivateFeeStructure(String feeStructureId) {
        FeeStructure feeStructure = feeStructureRepository.findByFeeStructureId(feeStructureId)
                .orElseThrow(() -> new EntityNotFoundException("Fee structure not found with ID: " + feeStructureId));
        
        feeStructure.setStatus("INACTIVE");
        feeStructureRepository.save(feeStructure);
    }

    private Integer getNextVersionNumber(String classId, String academicYear) {
        List<FeeStructure> existingStructures = feeStructureRepository
                .findByClassIdAndAcademicYear(classId, academicYear);
        
        return existingStructures.stream()
                .mapToInt(FeeStructure::getVersion)
                .max()
                .orElse(0) + 1;
    }

    private FeeStructureResponseDTO convertToResponseDTO(FeeStructure feeStructure) {
        List<FeeStructureItem> items = feeStructureItemRepository
                .findByFeeStructureId(feeStructure.getFeeStructureId());
        
        List<FeeStructureItemResponseDTO> itemDTOs = items.stream()
                .map(item -> FeeStructureItemResponseDTO.builder()
                        .id(item.getId())
                        .feeTypeId(item.getFeeTypeId())
                        .amount(item.getAmount())
                        .currency(item.getCurrency())
                        .mandatory(item.getMandatory())
                        .description(item.getDescription())
                        .build())
                .collect(Collectors.toList());

        return FeeStructureResponseDTO.builder()
                .feeStructureId(feeStructure.getFeeStructureId())
                .classId(feeStructure.getClassId())
                .academicYear(feeStructure.getAcademicYear())
                .version(feeStructure.getVersion())
                .effectiveDate(feeStructure.getEffectiveDate())
                .expiryDate(feeStructure.getExpiryDate())
                .status(feeStructure.getStatus())
                .feeItems(itemDTOs)
                .build();
    }
}
