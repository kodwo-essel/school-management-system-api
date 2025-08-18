package com.schoolmgt.auth.usermanagement.service.impl;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.usermanagement.dto.SuperAdminRegistrationDTO;
import com.schoolmgt.auth.usermanagement.entity.Role;
import com.schoolmgt.auth.usermanagement.entity.SuperAdmin;
import com.schoolmgt.auth.usermanagement.entity.User;
import com.schoolmgt.auth.usermanagement.repository.SuperAdminRepository;
import com.schoolmgt.auth.usermanagement.repository.UserRepository;
import com.schoolmgt.auth.usermanagement.service.UserService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, UserRepository> implements UserService {
    
    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SuperAdminRepository superAdminRepository;

    public UserServiceImpl(UserRepository userRepository, SuperAdminRepository superAdminRepository, PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.superAdminRepository = superAdminRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void upDatePassword(String email, String password) {
        userRepository.findByEmail(email).ifPresent(user -> {
            user.setPassword(password);
            userRepository.save(user);
        });
    }

    @Override
    public SuperAdmin registerSuperAdmin(SuperAdminRegistrationDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already registered");
        }

        SuperAdmin superAdmin = new SuperAdmin();
        superAdmin.setEmail(dto.getEmail());
        superAdmin.setPassword(passwordEncoder.encode(dto.getPassword()));
        superAdmin.setRole(Role.SUPERADMIN);  // Assign ADMIN role

        // Save admin to DB
        return superAdminRepository.save(superAdmin);
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) {
    Optional<User> userOpt = userRepository.findByEmail(email);
    if (userOpt.isEmpty()) {
        throw new UsernameNotFoundException("User not found");
    }

    User user = userOpt.get();

    // Convert your User entity to a Spring Security UserDetails object:
    return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
    );
}

}
