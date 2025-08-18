package com.schoolmgt.auth.config;


import com.schoolmgt.auth.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Allow preflight requests to pass through without JWT validation
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("=== JWT FILTER DEBUG ===");
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Content-Type: " + request.getContentType());
        System.out.println("Method: " + request.getMethod());

        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String jwt = authHeader.substring(7);
                System.out.println("JWT Token extracted: " + jwt.substring(0, Math.min(jwt.length(), 20)) + "...");

                String email = jwtUtil.extractEmail(jwt);
                System.out.println("Extracted email: " + email);

                String role = jwtUtil.extractRole(jwt);
                System.out.println("Extracted role: " + role);

                boolean isTokenValid = jwtUtil.validateToken(jwt, email);
                System.out.println("Token validation result: " + isTokenValid);

                if (email != null && isTokenValid) {
                    UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                            email,
                            "",
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
                    );

                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println("Authentication set successfully for: " + email);
                } else {
                    System.out.println("Authentication failed - email null or token invalid");
                }

            } catch (Exception e) {
                System.out.println("=== JWT PROCESSING EXCEPTION ===");
                System.out.println("Exception type: " + e.getClass().getSimpleName());
                System.out.println("Exception message: " + e.getMessage());
                System.out.println("Stack trace:");
                e.printStackTrace();
                System.out.println("================================");

                // Continue without authentication - let Spring Security handle unauthorized access
                SecurityContextHolder.clearContext();
            }
        } else {
            System.out.println("No Bearer token found in Authorization header");
        }

        System.out.println("Authentication before continuing: " +
                (SecurityContextHolder.getContext().getAuthentication() != null ?
                        SecurityContextHolder.getContext().getAuthentication().getName() : "null"));
        System.out.println("=========================");

        filterChain.doFilter(request, response);
    }
}