package com.schoolmgt.auth.fees.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.fees.dto.AdditionalChargeRequestDTO;
import com.schoolmgt.auth.fees.dto.AdditionalChargeResponseDTO;
import com.schoolmgt.auth.fees.entity.AdditionalCharge;
import com.schoolmgt.auth.fees.repository.AdditionalChargeRepository;
import com.schoolmgt.auth.fees.service.AdditionalChargeService;
import com.schoolmgt.auth.utils.IdGenerators;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdditionalChargeServiceImpl extends BaseServiceImpl<AdditionalCharge, AdditionalChargeRepository> 
        implements AdditionalChargeService {

    private final AdditionalChargeRepository additionalChargeRepository;

    public AdditionalChargeServiceImpl(AdditionalChargeRepository additionalChargeRepository) {
        super(additionalChargeRepository);
        this.additionalChargeRepository = additionalChargeRepository;
    }

    @Override
    public AdditionalChargeResponseDTO createAdditionalCharge(AdditionalChargeRequestDTO requestDTO) {
        AdditionalCharge additionalCharge = AdditionalCharge.builder()
                .studentId(requestDTO.getStudentId())
                .chargeType(requestDTO.getChargeType())
                .feeTypeId(requestDTO.getFeeTypeId())
                .amount(requestDTO.getAmount())
                .description(requestDTO.getDescription())
                .chargeDate(LocalDateTime.now())
                .dueDate(requestDTO.getDueDate())
                .status("PENDING")
                .referenceId(requestDTO.getReferenceId())
                .createdBy(requestDTO.getCreatedBy())
                .build();

        AdditionalCharge savedCharge = additionalChargeRepository.save(additionalCharge);

        // Generate charge ID
        if (savedCharge.getChargeId() == null) {
            String chargeId = IdGenerators.generateAdditionalChargeId(savedCharge.getId(), requestDTO.getStudentId());
            savedCharge.setChargeId(chargeId);
            savedCharge = additionalChargeRepository.save(savedCharge);
        }

        return convertToResponseDTO(savedCharge);
    }

    @Override
    public AdditionalChargeResponseDTO getAdditionalChargeById(String chargeId) {
        AdditionalCharge charge = additionalChargeRepository.findByChargeId(chargeId)
                .orElseThrow(() -> new EntityNotFoundException("Additional charge not found with ID: " + chargeId));
        return convertToResponseDTO(charge);
    }

    @Override
    public List<AdditionalChargeResponseDTO> getAdditionalChargesByStudent(String studentId) {
        return additionalChargeRepository.findByStudentId(studentId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdditionalChargeResponseDTO> getPendingChargesByStudent(String studentId) {
        return additionalChargeRepository.findByStudentIdAndStatus(studentId, "PENDING").stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdditionalChargeResponseDTO> getChargesByType(String chargeType) {
        return additionalChargeRepository.findByChargeType(chargeType).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdditionalChargeResponseDTO> getPendingCharges() {
        return additionalChargeRepository.findByStatus("PENDING").stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AdditionalChargeResponseDTO updateChargeStatus(String chargeId, String status) {
        AdditionalCharge charge = additionalChargeRepository.findByChargeId(chargeId)
                .orElseThrow(() -> new EntityNotFoundException("Additional charge not found with ID: " + chargeId));
        
        charge.setStatus(status);
        AdditionalCharge updatedCharge = additionalChargeRepository.save(charge);
        return convertToResponseDTO(updatedCharge);
    }

    @Override
    public void deleteAdditionalCharge(String chargeId) {
        AdditionalCharge charge = additionalChargeRepository.findByChargeId(chargeId)
                .orElseThrow(() -> new EntityNotFoundException("Additional charge not found with ID: " + chargeId));
        additionalChargeRepository.delete(charge);
    }

    private AdditionalChargeResponseDTO convertToResponseDTO(AdditionalCharge charge) {
        return AdditionalChargeResponseDTO.builder()
                .chargeId(charge.getChargeId())
                .studentId(charge.getStudentId())
                .chargeType(charge.getChargeType())
                .feeTypeId(charge.getFeeTypeId())
                .amount(charge.getAmount())
                .description(charge.getDescription())
                .chargeDate(charge.getChargeDate())
                .dueDate(charge.getDueDate())
                .status(charge.getStatus())
                .referenceId(charge.getReferenceId())
                .createdBy(charge.getCreatedBy())
                .build();
    }
}
