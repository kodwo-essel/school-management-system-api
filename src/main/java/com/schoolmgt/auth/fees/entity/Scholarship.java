package com.schoolmgt.auth.fees.entity;

import jakarta.persistence.*;
import lombok.*;
import com.schoolmgt.auth.base.entity.BaseEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "scholarships", uniqueConstraints = {
        @UniqueConstraint(columnNames = "scholarshipId")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Scholarship extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @EqualsAndHashCode.Include
    private String scholarshipId; // e.g., "SCHOL-MERIT-2024"

    @Column(nullable = false, length = 100)
    private String name; // e.g., "Merit Scholarship"

    @Column(nullable = false, length = 20)
    private String type; // "PERCENTAGE" or "FIXED_AMOUNT"

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal value; // 20.00 for 20% or 500.00 for fixed

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(length = 255)
    private String eligibilityCriteria; // e.g., "GPA > 3.5"

    @Column(columnDefinition = "TEXT")
    private String applicableFeeTypes; // JSON array of feeTypeIds

    private Integer maxRecipients; // null = unlimited

    @Column(nullable = false, length = 20)
    private String status; // "ACTIVE", "INACTIVE", "EXPIRED"
}

