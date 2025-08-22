package com.schoolmgt.auth.fees.entity;

import jakarta.persistence.*;
import lombok.*;
import com.schoolmgt.auth.base.entity.BaseEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "additional_charges", uniqueConstraints = {
        @UniqueConstraint(columnNames = "chargeId")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class AdditionalCharge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @EqualsAndHashCode.Include
    private String chargeId; // e.g., "CHARGE-2024-STU001-001"

    @Column(nullable = false, length = 50)
    private String studentId; // Reference to Student

    @Column(nullable = false, length = 50)
    private String chargeType; // e.g., "FINE", "LATE_FEE"

    @Column(length = 50)
    private String feeTypeId; // Optional reference to FeeType

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(length = 255)
    private String description; // e.g., "Broken laboratory equipment"

    @Column(nullable = false)
    private LocalDateTime chargeDate;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    @Column(nullable = false, length = 20)
    private String status; // e.g., "PENDING", "PAID", "WAIVED", "DISPUTED"

    @Column(length = 100)
    private String referenceId; // Optional reference to incident/violation

    @Column(nullable = false, length = 50)
    private String createdBy; // Staff member who created the charge
}
