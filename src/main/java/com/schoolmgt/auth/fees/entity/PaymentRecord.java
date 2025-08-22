package com.schoolmgt.auth.fees.entity;

import jakarta.persistence.*;
import lombok.*;
import com.schoolmgt.auth.base.entity.BaseEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_records", uniqueConstraints = {
        @UniqueConstraint(columnNames = "paymentId")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PaymentRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @EqualsAndHashCode.Include
    private String paymentId; // Unique identifier

    @Column(nullable = false, length = 50)
    private String studentId;

    @Column(nullable = false, length = 100)
    private String assessmentId; // Reference to StudentFeeAssessment

    @Column(length = 100)
    private String additionalChargeId; // Optional reference to AdditionalCharge

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 20)
    private String paymentMethod; // CASH, CARD, BANK_TRANSFER

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    @Column(length = 100)
    private String referenceNumber; // e.g., transaction reference

    @Column(nullable = false, length = 20)
    private String status; // PENDING, COMPLETED, FAILED, REFUNDED

    @Column(nullable = false, length = 50)
    private String processedBy; // User who processed the payment
}
