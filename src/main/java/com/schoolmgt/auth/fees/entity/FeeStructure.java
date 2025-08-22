package com.schoolmgt.auth.fees.entity;

import jakarta.persistence.*;
import lombok.*;
import com.schoolmgt.auth.base.entity.BaseEntity;
import java.time.LocalDateTime;

@Entity
@Table(name = "fee_structures", uniqueConstraints = {
        @UniqueConstraint(columnNames = "feeStructureId")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class FeeStructure extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @EqualsAndHashCode.Include
    private String feeStructureId; // e.g., "FEE-STR-2024-GRD10-SCI-A-V1"

    @Column(nullable = false, length = 50)
    private String classId; // Reference to SchoolClass (e.g., "CLS-GRD10-SCI-A")

    @Column(nullable = false, length = 20)
    private String academicYear; // e.g., "2024-2025"

    @Column(nullable = false)
    private Integer version; // e.g., 1, 2, 3

    @Column(nullable = false)
    private LocalDateTime effectiveDate; // e.g., 2024-08-01T00:00:00

    private LocalDateTime expiryDate; // Nullable if still active

    @Column(nullable = false, length = 20)
    private String status; // e.g., "ACTIVE", "INACTIVE", "SUPERSEDED"
}
