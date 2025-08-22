package com.schoolmgt.auth.fees.repository;

import java.util.Optional;
import java.util.List;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.fees.entity.FeeType;

public interface FeeTypeRepository extends BaseRepository<FeeType> {
    Optional<FeeType> findByFeeTypeId(String feeTypeId);
    List<FeeType> findByStatus(String status);
    List<FeeType> findByCategory(String category);
    Optional<FeeType> findByNameAndCategory(String name, String category);
}
