package com.schoolmgt.auth.fees.entity;

import jakarta.persistence.*;
import lombok.*;
import com.schoolmgt.auth.base.entity.BaseEntity;
import java.math.BigDecimal;

@Entity
@Table(name = "student_fee_assessment_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class StudentFeeAssessmentItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 100)
    private String assessmentId; // Reference to StudentFeeAssessment

    @Column(nullable = false, length = 50)
    private String feeTypeId; // Reference to FeeType

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal originalAmount; // Amount before any discounts

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal discountAmount; // Scholarship/discount applied

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal finalAmount; // originalAmount - discountAmount

    @Column(length = 255)
    private String description; // Additional details
}

