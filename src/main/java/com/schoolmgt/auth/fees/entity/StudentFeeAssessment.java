package com.schoolmgt.auth.fees.entity;

import jakarta.persistence.*;
import lombok.*;
import com.schoolmgt.auth.base.entity.BaseEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_fee_assessments", uniqueConstraints = {
        @UniqueConstraint(columnNames = "assessmentId")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class StudentFeeAssessment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @EqualsAndHashCode.Include
    private String assessmentId; // e.g., "ASSESS-2024-STU001-001"

    @Column(nullable = false, length = 50)
    private String studentId; // Reference to Student

    @Column(nullable = false, length = 50)
    private String classId; // Student's class at assessment time

    @Column(nullable = false, length = 20)
    private String academicYear; // e.g., "2024-2025"

    @Column(nullable = false, length = 20)
    private String term; // e.g., "SEMESTER_1", "SEMESTER_2", "ANNUAL"

    @Column(nullable = false, length = 100)
    private String feeStructureId; // Fee structure used for calculation

    @Column(nullable = false)
    private LocalDateTime assessmentDate;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount; // Sum of all fee items

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal discountAmount; // Total scholarships/discounts

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal finalAmount; // totalAmount - discountAmount

    @Column(nullable = false, length = 20)
    private String status; // e.g., "PENDING", "PARTIALLY_PAID", "FULLY_PAID", "OVERDUE"
}
