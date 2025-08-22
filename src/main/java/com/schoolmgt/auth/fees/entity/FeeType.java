package com.schoolmgt.auth.fees.entity;

import jakarta.persistence.*;
import lombok.*;
import com.schoolmgt.auth.base.entity.BaseEntity;

@Entity
@Table(name = "fee_types", uniqueConstraints = {
        @UniqueConstraint(columnNames = "feeTypeId")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class FeeType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    @EqualsAndHashCode.Include
    private String feeTypeId; // e.g., "TUITION", "LAB_FEE"

    @Column(nullable = false, length = 100)
    private String name; // e.g., "Tuition Fee"

    @Column(nullable = false, length = 50)
    private String category; // e.g., "ACADEMIC", "ADMINISTRATIVE"

    @Column(length = 255)
    private String description; // e.g., "Main academic fee for instruction"

    @Column(nullable = false, length = 20)
    private String status; // e.g., "ACTIVE", "INACTIVE"
}
