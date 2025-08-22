package com.schoolmgt.auth.fees.repository;

import java.util.List;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.fees.entity.StudentFeeAssessmentItem;

public interface StudentFeeAssessmentItemRepository extends BaseRepository<StudentFeeAssessmentItem> {
    List<StudentFeeAssessmentItem> findByAssessmentId(String assessmentId);
    List<StudentFeeAssessmentItem> findByAssessmentIdAndFeeTypeId(String assessmentId, String feeTypeId);
    void deleteByAssessmentId(String assessmentId);
}
