package com.schoolmgt.auth.fees.repository;

import java.util.Optional;
import java.util.List;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.fees.entity.AdditionalCharge;

public interface AdditionalChargeRepository extends BaseRepository<AdditionalCharge> {
    Optional<AdditionalCharge> findByChargeId(String chargeId);
    List<AdditionalCharge> findByStudentId(String studentId);
    List<AdditionalCharge> findByStudentIdAndStatus(String studentId, String status);
    List<AdditionalCharge> findByChargeType(String chargeType);
    List<AdditionalCharge> findByStatus(String status);
    List<AdditionalCharge> findByCreatedBy(String createdBy);
}
