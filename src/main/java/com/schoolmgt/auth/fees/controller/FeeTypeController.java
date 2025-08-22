package com.schoolmgt.auth.fees.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmgt.auth.base.dto.ApiResponse;
import com.schoolmgt.auth.fees.dto.FeeTypeRequestDTO;
import com.schoolmgt.auth.fees.dto.FeeTypeResponseDTO;
import com.schoolmgt.auth.fees.service.FeeTypeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fees/types")
@RequiredArgsConstructor
@Tag(name = "Fee Type Management", description = "Fee Type Management Endpoints")
public class FeeTypeController {

    private final FeeTypeService feeTypeService;

    @PostMapping("/create")
    @Operation(summary = "Create a new fee type")
    public ResponseEntity<ApiResponse<FeeTypeResponseDTO>> createFeeType(
            @Valid @RequestBody FeeTypeRequestDTO requestDTO) {
        FeeTypeResponseDTO feeType = feeTypeService.createFeeType(requestDTO);
        return ResponseEntity.ok(ApiResponse.success(feeType, "Fee type created successfully"));
    }

    @GetMapping("/{feeTypeId}")
    @Operation(summary = "Get fee type by ID")
    public ResponseEntity<ApiResponse<FeeTypeResponseDTO>> getFeeTypeById(
            @PathVariable String feeTypeId) {
        FeeTypeResponseDTO feeType = feeTypeService.getFeeTypeById(feeTypeId);
        return ResponseEntity.ok(ApiResponse.success(feeType, "Fee type retrieved successfully"));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all fee types")
    public ResponseEntity<ApiResponse<List<FeeTypeResponseDTO>>> getAllFeeTypes() {
        List<FeeTypeResponseDTO> feeTypes = feeTypeService.getAllFeeTypes();
        return ResponseEntity.ok(ApiResponse.success(feeTypes, "Fee types retrieved successfully"));
    }

    @GetMapping("/active")
    @Operation(summary = "Get all active fee types")
    public ResponseEntity<ApiResponse<List<FeeTypeResponseDTO>>> getActiveFeeTypes() {
        List<FeeTypeResponseDTO> feeTypes = feeTypeService.getActiveFeeTypes();
        return ResponseEntity.ok(ApiResponse.success(feeTypes, "Active fee types retrieved successfully"));
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get fee types by category")
    public ResponseEntity<ApiResponse<List<FeeTypeResponseDTO>>> getFeeTypesByCategory(
            @PathVariable String category) {
        List<FeeTypeResponseDTO> feeTypes = feeTypeService.getFeeTypesByCategory(category);
        return ResponseEntity.ok(ApiResponse.success(feeTypes, "Fee types retrieved successfully"));
    }

    @PutMapping("/{feeTypeId}")
    @Operation(summary = "Update fee type")
    public ResponseEntity<ApiResponse<FeeTypeResponseDTO>> updateFeeType(
            @PathVariable String feeTypeId,
            @Valid @RequestBody FeeTypeRequestDTO requestDTO) {
        FeeTypeResponseDTO feeType = feeTypeService.updateFeeType(feeTypeId, requestDTO);
        return ResponseEntity.ok(ApiResponse.success(feeType, "Fee type updated successfully"));
    }

    @DeleteMapping("/{feeTypeId}")
    @Operation(summary = "Delete fee type")
    public ResponseEntity<ApiResponse<Void>> deleteFeeType(@PathVariable String feeTypeId) {
        feeTypeService.deleteFeeType(feeTypeId);
        return ResponseEntity.ok(ApiResponse.success(null, "Fee type deleted successfully"));
    }
}
