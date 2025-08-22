package com.schoolmgt.auth.fees.service;

import java.util.List;

import com.schoolmgt.auth.fees.dto.FeeStructureRequestDTO;
import com.schoolmgt.auth.fees.dto.FeeStructureResponseDTO;

public interface FeeStructureService {
    FeeStructureResponseDTO createFeeStructure(FeeStructureRequestDTO requestDTO);
    FeeStructureResponseDTO getFeeStructureById(String feeStructureId);
    List<FeeStructureResponseDTO> getFeeStructuresByClass(String classId, String academicYear);
    FeeStructureResponseDTO getActiveFeeStructureForClass(String classId, String academicYear);
    List<FeeStructureResponseDTO> getAllActiveFeeStructures();
    FeeStructureResponseDTO updateFeeStructure(String feeStructureId, FeeStructureRequestDTO requestDTO);
    void deactivateFeeStructure(String feeStructureId);
}
