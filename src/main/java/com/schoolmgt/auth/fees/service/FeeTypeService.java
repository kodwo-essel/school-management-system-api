package com.schoolmgt.auth.fees.service;

import java.util.List;

import com.schoolmgt.auth.fees.dto.FeeTypeRequestDTO;
import com.schoolmgt.auth.fees.dto.FeeTypeResponseDTO;

public interface FeeTypeService {
    FeeTypeResponseDTO createFeeType(FeeTypeRequestDTO requestDTO);
    FeeTypeResponseDTO getFeeTypeById(String feeTypeId);
    List<FeeTypeResponseDTO> getAllFeeTypes();
    List<FeeTypeResponseDTO> getFeeTypesByCategory(String category);
    List<FeeTypeResponseDTO> getActiveFeeTypes();
    FeeTypeResponseDTO updateFeeType(String feeTypeId, FeeTypeRequestDTO requestDTO);
    void deleteFeeType(String feeTypeId);
}
