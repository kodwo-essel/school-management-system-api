package com.schoolmgt.auth.usermanagement.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmgt.auth.base.dto.ApiResponse;
import com.schoolmgt.auth.usermanagement.dto.AdminRegistrationDTO;
import com.schoolmgt.auth.usermanagement.dto.AdminResponseDTO;
import com.schoolmgt.auth.usermanagement.dto.SuperAdminRegistrationDTO;
import com.schoolmgt.auth.usermanagement.dto.SuperAdminResponseDTO;
import com.schoolmgt.auth.usermanagement.entity.Admin;
import com.schoolmgt.auth.usermanagement.entity.User;
import com.schoolmgt.auth.usermanagement.service.SuperAdminService;
import com.schoolmgt.auth.usermanagement.service.UserService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/superadmins")
@Tag(name = "Super Admin", description = "APIs for super admin registration")
public class SuperAdminController {

    private final UserService userService;
    private final SuperAdminService superAdminService;

    @Value("${admin.secret-key}")
    private String mySecret;

    public SuperAdminController(UserService userService, SuperAdminService superAdminService) {
        this.userService = userService;
        this.superAdminService = superAdminService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SuperAdminResponseDTO>> registerAdmin(
        @Parameter(in = ParameterIn.HEADER, description = "SecretKey key for the admin registration", required = true)
        @RequestHeader("Secret-Key") String secretKey,    
        @Valid @RequestBody SuperAdminRegistrationDTO dto) {

        if (!mySecret.equals(secretKey)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error("Unauthorized"));
        }

        try {
            User admin = userService.registerSuperAdmin(dto);

            // Convert User entity to AdminResponseDTO (you need to implement this mapping)
            SuperAdminResponseDTO responseDTO = new SuperAdminResponseDTO();
            responseDTO.setEmail(admin.getEmail());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(responseDTO, "Admin registered successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/register/admins")
    public ResponseEntity<ApiResponse<AdminResponseDTO>> registerAdmin( 
        @Valid @RequestBody AdminRegistrationDTO dto) {
            try {
                Admin admin = superAdminService.registerSchoolAdmin(dto);
                AdminResponseDTO responseDTO = new AdminResponseDTO();

                responseDTO.setEmail(admin.getEmail());

                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ApiResponse.success(responseDTO, "Admin registered successfully"));
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error(e.getMessage()));
            }
    }
}
