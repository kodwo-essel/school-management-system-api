package com.schoolmgt.auth.fees.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmgt.auth.base.dto.ApiResponse;
import com.schoolmgt.auth.fees.dto.FeeStructureRequestDTO;
import com.schoolmgt.auth.fees.dto.FeeStructureResponseDTO;
import com.schoolmgt.auth.fees.service.FeeStructureService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fees/structures")
@RequiredArgsConstructor
@Tag(name = "Fee Structure Management", description = "Fee Structure Management Endpoints")
public class FeeStructureController {

    private final FeeStructureService feeStructureService;

    @PostMapping("/create")
    @Operation(summary = "Create a new fee structure")
    public ResponseEntity<ApiResponse<FeeStructureResponseDTO>> createFeeStructure(
            @Valid @RequestBody FeeStructureRequestDTO requestDTO) {
        FeeStructureResponseDTO feeStructure = feeStructureService.createFeeStructure(requestDTO);
        return ResponseEntity.ok(ApiResponse.success(feeStructure, "Fee structure created successfully"));
    }

    @GetMapping("/{feeStructureId}")
    @Operation(summary = "Get fee structure by ID")
    public ResponseEntity<ApiResponse<FeeStructureResponseDTO>> getFeeStructureById(
            @PathVariable String feeStructureId) {
        FeeStructureResponseDTO feeStructure = feeStructureService.getFeeStructureById(feeStructureId);
        return ResponseEntity.ok(ApiResponse.success(feeStructure, "Fee structure retrieved successfully"));
    }

    @GetMapping("/class/{classId}/year/{academicYear}")
    @Operation(summary = "Get fee structures by class and academic year")
    public ResponseEntity<ApiResponse<List<FeeStructureResponseDTO>>> getFeeStructuresByClass(
            @PathVariable String classId,
            @PathVariable String academicYear) {
        List<FeeStructureResponseDTO> feeStructures = feeStructureService.getFeeStructuresByClass(classId, academicYear);
        return ResponseEntity.ok(ApiResponse.success(feeStructures, "Fee structures retrieved successfully"));
    }

    @GetMapping("/class/{classId}/year/{academicYear}/active")
    @Operation(summary = "Get active fee structure for class and academic year")
    public ResponseEntity<ApiResponse<FeeStructureResponseDTO>> getActiveFeeStructureForClass(
            @PathVariable String classId,
            @PathVariable String academicYear) {
        FeeStructureResponseDTO feeStructure = feeStructureService.getActiveFeeStructureForClass(classId, academicYear);
        return ResponseEntity.ok(ApiResponse.success(feeStructure, "Active fee structure retrieved successfully"));
    }

    @GetMapping("/active")
    @Operation(summary = "Get all active fee structures")
    public ResponseEntity<ApiResponse<List<FeeStructureResponseDTO>>> getAllActiveFeeStructures() {
        List<FeeStructureResponseDTO> feeStructures = feeStructureService.getAllActiveFeeStructures();
        return ResponseEntity.ok(ApiResponse.success(feeStructures, "Active fee structures retrieved successfully"));
    }

    @PutMapping("/{feeStructureId}")
    @Operation(summary = "Update fee structure")
    public ResponseEntity<ApiResponse<FeeStructureResponseDTO>> updateFeeStructure(
            @PathVariable String feeStructureId,
            @Valid @RequestBody FeeStructureRequestDTO requestDTO) {
        FeeStructureResponseDTO feeStructure = feeStructureService.updateFeeStructure(feeStructureId, requestDTO);
        return ResponseEntity.ok(ApiResponse.success(feeStructure, "Fee structure updated successfully"));
    }

    @PutMapping("/{feeStructureId}/deactivate")
    @Operation(summary = "Deactivate fee structure")
    public ResponseEntity<ApiResponse<Void>> deactivateFeeStructure(@PathVariable String feeStructureId) {
        feeStructureService.deactivateFeeStructure(feeStructureId);
        return ResponseEntity.ok(ApiResponse.success(null, "Fee structure deactivated successfully"));
    }
}
