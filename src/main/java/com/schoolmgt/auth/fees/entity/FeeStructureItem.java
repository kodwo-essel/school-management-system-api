package com.schoolmgt.auth.fees.entity;

import jakarta.persistence.*;
import lombok.*;
import com.schoolmgt.auth.base.entity.BaseEntity;
import java.math.BigDecimal;

@Entity
@Table(name = "fee_structure_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class FeeStructureItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 100)
    private String feeStructureId; // Reference to FeeStructure

    @Column(nullable = false, length = 50)
    private String feeTypeId; // Reference to FeeType

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount; // e.g., 1500.00

    @Column(nullable = false, length = 10)
    private String currency; // e.g., "USD", "GHS"

    @Column(nullable = false)
    private Boolean mandatory; // true = required, false = optional

    @Column(length = 255)
    private String description; // Additional details
}

