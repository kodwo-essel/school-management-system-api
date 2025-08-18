package com.schoolmgt.auth.usermanagement.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.usermanagement.dto.AdminRegistrationDTO;
import com.schoolmgt.auth.usermanagement.entity.Admin;
import com.schoolmgt.auth.usermanagement.entity.Role;
import com.schoolmgt.auth.usermanagement.entity.SuperAdmin;
import com.schoolmgt.auth.usermanagement.repository.AdminRepository;
import com.schoolmgt.auth.usermanagement.repository.SuperAdminRepository;
import com.schoolmgt.auth.usermanagement.service.SuperAdminService;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class SuperAdminServiceImpl extends BaseServiceImpl<SuperAdmin, SuperAdminRepository> implements SuperAdminService{

    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;

    public SuperAdminServiceImpl(SuperAdminRepository superAdminRepository, PasswordEncoder passwordEncoder, AdminRepository adminRepository) {
        super(superAdminRepository);
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin registerSchoolAdmin(AdminRegistrationDTO dto) {
        Admin admin = new Admin();
        admin.setSchoolId(dto.getSchoolId());
        admin.setRole(Role.ADMIN);
        admin.setEmail(dto.getEmail());
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));

        return adminRepository.save(admin);
    }
    
}
