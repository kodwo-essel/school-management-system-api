package com.schoolmgt.auth.fees.entity;

import jakarta.persistence.*;
import lombok.*;
import com.schoolmgt.auth.base.entity.BaseEntity;
import java.math.BigDecimal;

@Entity
@Table(name = "payment_allocations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PaymentAllocation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 100)
    private String paymentId; // Reference to PaymentRecord

    @Column(nullable = false, length = 100)
    private String feeTypeId; // Reference to FeeType

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal allocatedAmount; // Amount allocated from payment

    @Column(length = 255)
    private String description; // Notes or allocation details
}
