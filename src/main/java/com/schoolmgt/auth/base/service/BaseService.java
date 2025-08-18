package com.schoolmgt.auth.base.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.schoolmgt.auth.base.entity.BaseEntity;

public interface BaseService<T extends BaseEntity> {
    
    T save(T entity);
    
    List<T> saveAll(List<T> entities);
    
    Optional<T> findById(Long id);
    
    T findByIdOrThrow(Long id);
    
    List<T> findAll();
    
    Page<T> findAll(Pageable pageable);
    
    boolean existsById(Long id);
    
    long count();
    
    void deleteById(Long id);
    
    void delete(T entity);
    
    void deleteAll(List<T> entities);
} 