package com.schoolmgt.auth.fees.entity;

import jakarta.persistence.*;
import lombok.*;
import com.schoolmgt.auth.base.entity.BaseEntity;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_scholarships")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class StudentScholarship extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 50)
    private String studentId; // Reference to Student

    @Column(nullable = false, length = 100)
    private String scholarshipId; // Reference to Scholarship

    @Column(nullable = false, length = 20)
    private String academicYear; // e.g., "2024-2025"

    @Column(nullable = false, length = 20)
    private String term; // e.g., "SEMESTER_1", "SEMESTER_2", "ANNUAL", "ALL"

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false, length = 20)
    private String status; // "ACTIVE", "SUSPENDED", "TERMINATED", "EXPIRED"

    @Column(length = 50)
    private String approvedBy; // Staff member who approved

    private LocalDateTime approvalDate;

    @Column(length = 255)
    private String notes; // Additional notes
}

