package com.schoolmgt.auth.semester.entity;

import com.schoolmgt.auth.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "semesters")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Semester extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String semesterId;

    @Column(nullable = false)
    private String name; // "Term 1", "Term 2", "Term 3"

    @Column(nullable = false)
    private String academicYear; // "2024-2025"

    @Column(nullable = false)
    private String schoolId;

    private String description; // Simple term description

    private java.time.LocalDate startDate; // Term start date

    private java.time.LocalDate endDate; // Term end date

    private Boolean isCurrentSemester; // Flag to indicate current active term

}
