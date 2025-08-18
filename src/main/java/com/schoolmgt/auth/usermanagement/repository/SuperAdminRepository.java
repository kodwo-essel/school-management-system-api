package com.schoolmgt.auth.usermanagement.repository;

import java.util.Optional;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.usermanagement.entity.SuperAdmin;

public interface SuperAdminRepository extends BaseRepository<SuperAdmin> {
    Optional<SuperAdmin> findByEmail(String email);
}
