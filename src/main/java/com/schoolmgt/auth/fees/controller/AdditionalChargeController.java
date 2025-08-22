package com.schoolmgt.auth.fees.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmgt.auth.base.dto.ApiResponse;
import com.schoolmgt.auth.fees.dto.AdditionalChargeRequestDTO;
import com.schoolmgt.auth.fees.dto.AdditionalChargeResponseDTO;
import com.schoolmgt.auth.fees.service.AdditionalChargeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fees/additional-charges")
@RequiredArgsConstructor
@Tag(name = "Additional Charge Management", description = "Additional Charge Management Endpoints")
public class AdditionalChargeController {

    private final AdditionalChargeService additionalChargeService;

    @PostMapping("/create")
    @Operation(summary = "Create a new additional charge")
    public ResponseEntity<ApiResponse<AdditionalChargeResponseDTO>> createAdditionalCharge(
            @Valid @RequestBody AdditionalChargeRequestDTO requestDTO) {
        AdditionalChargeResponseDTO charge = additionalChargeService.createAdditionalCharge(requestDTO);
        return ResponseEntity.ok(ApiResponse.success(charge, "Additional charge created successfully"));
    }

    @GetMapping("/{chargeId}")
    @Operation(summary = "Get additional charge by ID")
    public ResponseEntity<ApiResponse<AdditionalChargeResponseDTO>> getAdditionalChargeById(
            @PathVariable String chargeId) {
        AdditionalChargeResponseDTO charge = additionalChargeService.getAdditionalChargeById(chargeId);
        return ResponseEntity.ok(ApiResponse.success(charge, "Additional charge retrieved successfully"));
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get additional charges by student ID")
    public ResponseEntity<ApiResponse<List<AdditionalChargeResponseDTO>>> getAdditionalChargesByStudent(
            @PathVariable String studentId) {
        List<AdditionalChargeResponseDTO> charges = additionalChargeService.getAdditionalChargesByStudent(studentId);
        return ResponseEntity.ok(ApiResponse.success(charges, "Student additional charges retrieved successfully"));
    }

    @GetMapping("/student/{studentId}/pending")
    @Operation(summary = "Get pending additional charges by student ID")
    public ResponseEntity<ApiResponse<List<AdditionalChargeResponseDTO>>> getPendingChargesByStudent(
            @PathVariable String studentId) {
        List<AdditionalChargeResponseDTO> charges = additionalChargeService.getPendingChargesByStudent(studentId);
        return ResponseEntity.ok(ApiResponse.success(charges, "Pending student charges retrieved successfully"));
    }

    @GetMapping("/type/{chargeType}")
    @Operation(summary = "Get additional charges by type")
    public ResponseEntity<ApiResponse<List<AdditionalChargeResponseDTO>>> getChargesByType(
            @PathVariable String chargeType) {
        List<AdditionalChargeResponseDTO> charges = additionalChargeService.getChargesByType(chargeType);
        return ResponseEntity.ok(ApiResponse.success(charges, "Charges by type retrieved successfully"));
    }

    @GetMapping("/pending")
    @Operation(summary = "Get all pending additional charges")
    public ResponseEntity<ApiResponse<List<AdditionalChargeResponseDTO>>> getPendingCharges() {
        List<AdditionalChargeResponseDTO> charges = additionalChargeService.getPendingCharges();
        return ResponseEntity.ok(ApiResponse.success(charges, "Pending charges retrieved successfully"));
    }

    @PutMapping("/{chargeId}/status")
    @Operation(summary = "Update additional charge status")
    public ResponseEntity<ApiResponse<AdditionalChargeResponseDTO>> updateChargeStatus(
            @PathVariable String chargeId,
            @RequestParam String status) {
        AdditionalChargeResponseDTO charge = additionalChargeService.updateChargeStatus(chargeId, status);
        return ResponseEntity.ok(ApiResponse.success(charge, "Charge status updated successfully"));
    }

    @DeleteMapping("/{chargeId}")
    @Operation(summary = "Delete additional charge")
    public ResponseEntity<ApiResponse<Void>> deleteAdditionalCharge(@PathVariable String chargeId) {
        additionalChargeService.deleteAdditionalCharge(chargeId);
        return ResponseEntity.ok(ApiResponse.success(null, "Additional charge deleted successfully"));
    }
}
