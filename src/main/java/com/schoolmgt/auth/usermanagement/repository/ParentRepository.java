package com.schoolmgt.auth.usermanagement.repository;

import java.util.Optional;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.usermanagement.entity.Parent;

public interface ParentRepository extends BaseRepository<Parent>{
    
    Optional<Parent> findByEmail(String email);
}
