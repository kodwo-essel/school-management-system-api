package com.schoolmgt.auth.usermanagement.controller;

import com.schoolmgt.auth.base.dto.ApiResponse;
import com.schoolmgt.auth.usermanagement.entity.User;
import com.schoolmgt.auth.usermanagement.service.UserService;
import com.schoolmgt.auth.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication endpoints for testing")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @Operation(summary = "Authenticate user and generate JWT token")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(
            @RequestParam String email,
            @RequestParam String password) {

        Optional<User> userOpt = userService.findByEmail(email);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401)
                    .body(ApiResponse.error("Invalid email or password"));
        }

        User user = userOpt.get();

        // Validate password (assuming passwordEncoder bean available)
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401)
                    .body(ApiResponse.error("Invalid email or password"));
        }

        // Extract role (assuming single role)
        String role = user.getRole() != null ? user.getRole().name() : null;

        if (role == null) {
            return ResponseEntity.status(401)
                    .body(ApiResponse.error("User role is not assigned"));
        }

        String token = jwtUtil.generateToken(email, role);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("email", email);
        response.put("role", role);

        return ResponseEntity.ok(ApiResponse.success(response, "User authenticated  successfully!"));
    }


    @GetMapping("/test")
    @Operation(summary = "Test authentication", description = "Test endpoint to verify authentication is working")
    public ResponseEntity<ApiResponse<String>> testAuth() {
        return ResponseEntity.ok(ApiResponse.success("You are authenticated", "Authentication working!"));
    }

    @GetMapping("/test/admin")
    @Operation(summary = "Test admin access", description = "Test endpoint that requires ADMIN role")
    public ResponseEntity<ApiResponse<String>> testAdminAuth() {
        return ResponseEntity.ok(ApiResponse.success("You have ADMIN privileges", "Admin access granted!"));
    }

    @GetMapping("/test/organizer")
    @Operation(summary = "Test organizer access", description = "Test endpoint that requires ORGANIZER role")
    public ResponseEntity<ApiResponse<String>> testOrganizerAuth() {
        return ResponseEntity.ok(ApiResponse.success("You have ORGANIZER privileges", "Organizer access granted!"));
    }

    @GetMapping("/test/user")
    @Operation(summary = "Test user access", description = "Test endpoint that requires USER role")
    public ResponseEntity<ApiResponse<String>> testUserAuth() {
        return ResponseEntity.ok(ApiResponse.success("You have USER privileges", "User access granted!"));
    }

} 