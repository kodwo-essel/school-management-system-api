package com.schoolmgt.auth.usermanagement.repository;

import java.util.Optional;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.usermanagement.entity.Admin;

public interface AdminRepository extends BaseRepository<Admin>{
    Optional<Admin> findByEmail(String email);
}
