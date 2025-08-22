package com.schoolmgt.auth.fees.repository;

import java.util.List;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.fees.entity.FeeStructureItem;

public interface FeeStructureItemRepository extends BaseRepository<FeeStructureItem> {
    List<FeeStructureItem> findByFeeStructureId(String feeStructureId);
    List<FeeStructureItem> findByFeeStructureIdAndMandatory(String feeStructureId, Boolean mandatory);
    void deleteByFeeStructureId(String feeStructureId);
}
