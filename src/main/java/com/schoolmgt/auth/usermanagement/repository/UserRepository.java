package com.schoolmgt.auth.usermanagement.repository;

import java.util.Optional;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.usermanagement.entity.User;

public interface UserRepository extends BaseRepository<User> {

    // Find user by email (unique)
    Optional<User> findByEmail(String email);
}