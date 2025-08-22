package com.schoolmgt.auth.fees.service;

import java.util.List;

import com.schoolmgt.auth.fees.dto.AdditionalChargeRequestDTO;
import com.schoolmgt.auth.fees.dto.AdditionalChargeResponseDTO;

public interface AdditionalChargeService {
    AdditionalChargeResponseDTO createAdditionalCharge(AdditionalChargeRequestDTO requestDTO);
    AdditionalChargeResponseDTO getAdditionalChargeById(String chargeId);
    List<AdditionalChargeResponseDTO> getAdditionalChargesByStudent(String studentId);
    List<AdditionalChargeResponseDTO> getPendingChargesByStudent(String studentId);
    List<AdditionalChargeResponseDTO> getChargesByType(String chargeType);
    List<AdditionalChargeResponseDTO> getPendingCharges();
    AdditionalChargeResponseDTO updateChargeStatus(String chargeId, String status);
    void deleteAdditionalCharge(String chargeId);
}
