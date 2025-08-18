package com.schoolmgt.auth.usermanagement.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

import com.schoolmgt.auth.base.service.BaseService;
import com.schoolmgt.auth.usermanagement.dto.SuperAdminRegistrationDTO;
import com.schoolmgt.auth.usermanagement.entity.User;

public interface UserService extends BaseService<User>{
    Optional <User> findByEmail(String email);

    void upDatePassword(String email, String password);

    User registerSuperAdmin(SuperAdminRegistrationDTO dto) throws RuntimeException;

    UserDetails loadUserByUsername(String email);
}