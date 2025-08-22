package com.schoolmgt.auth.fees.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.fees.dto.FeeTypeRequestDTO;
import com.schoolmgt.auth.fees.dto.FeeTypeResponseDTO;
import com.schoolmgt.auth.fees.entity.FeeType;
import com.schoolmgt.auth.fees.repository.FeeTypeRepository;
import com.schoolmgt.auth.fees.service.FeeTypeService;
import com.schoolmgt.auth.utils.IdGenerators;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class FeeTypeServiceImpl extends BaseServiceImpl<FeeType, FeeTypeRepository> implements FeeTypeService {

    private final FeeTypeRepository feeTypeRepository;

    public FeeTypeServiceImpl(FeeTypeRepository feeTypeRepository) {
        super(feeTypeRepository);
        this.feeTypeRepository = feeTypeRepository;
    }

    @Override
    public FeeTypeResponseDTO createFeeType(FeeTypeRequestDTO requestDTO) {
        // Check if fee type with same name and category already exists
        if (feeTypeRepository.findByNameAndCategory(requestDTO.getName(), requestDTO.getCategory()).isPresent()) {
            throw new IllegalArgumentException("Fee type with name '" + requestDTO.getName() + 
                "' and category '" + requestDTO.getCategory() + "' already exists");
        }

        FeeType feeType = FeeType.builder()
                .name(requestDTO.getName())
                .category(requestDTO.getCategory())
                .description(requestDTO.getDescription())
                .status(requestDTO.getStatus())
                .build();

        FeeType savedFeeType = feeTypeRepository.save(feeType);

        // Generate fee type ID if not set
        if (savedFeeType.getFeeTypeId() == null) {
            String feeTypeId = IdGenerators.generateFeeTypeId(savedFeeType.getId(), "SCH-0001"); // Default school for now
            savedFeeType.setFeeTypeId(feeTypeId);
            savedFeeType = feeTypeRepository.save(savedFeeType);
        }

        return convertToResponseDTO(savedFeeType);
    }

    @Override
    public FeeTypeResponseDTO getFeeTypeById(String feeTypeId) {
        FeeType feeType = feeTypeRepository.findByFeeTypeId(feeTypeId)
                .orElseThrow(() -> new EntityNotFoundException("Fee type not found with ID: " + feeTypeId));
        return convertToResponseDTO(feeType);
    }

    @Override
    public List<FeeTypeResponseDTO> getAllFeeTypes() {
        return feeTypeRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeeTypeResponseDTO> getFeeTypesByCategory(String category) {
        return feeTypeRepository.findByCategory(category).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeeTypeResponseDTO> getActiveFeeTypes() {
        return feeTypeRepository.findByStatus("ACTIVE").stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FeeTypeResponseDTO updateFeeType(String feeTypeId, FeeTypeRequestDTO requestDTO) {
        FeeType feeType = feeTypeRepository.findByFeeTypeId(feeTypeId)
                .orElseThrow(() -> new EntityNotFoundException("Fee type not found with ID: " + feeTypeId));

        feeType.setName(requestDTO.getName());
        feeType.setCategory(requestDTO.getCategory());
        feeType.setDescription(requestDTO.getDescription());
        feeType.setStatus(requestDTO.getStatus());

        FeeType updatedFeeType = feeTypeRepository.save(feeType);
        return convertToResponseDTO(updatedFeeType);
    }

    @Override
    public void deleteFeeType(String feeTypeId) {
        FeeType feeType = feeTypeRepository.findByFeeTypeId(feeTypeId)
                .orElseThrow(() -> new EntityNotFoundException("Fee type not found with ID: " + feeTypeId));
        feeTypeRepository.delete(feeType);
    }

    private FeeTypeResponseDTO convertToResponseDTO(FeeType feeType) {
        return FeeTypeResponseDTO.builder()
                .feeTypeId(feeType.getFeeTypeId())
                .name(feeType.getName())
                .category(feeType.getCategory())
                .description(feeType.getDescription())
                .status(feeType.getStatus())
                .build();
    }
}
