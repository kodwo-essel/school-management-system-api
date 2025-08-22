package com.schoolmgt.auth.schoolmanagement.entity;

import com.schoolmgt.auth.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "student_class_enrollments",
    uniqueConstraints = @UniqueConstraint(columnNames = {"studentId", "classId", "academicYear"})
)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentClassEnrollment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The student being enrolled
    @Column(nullable = false)
    private String studentId;   // Or a FK to Student entity if you have it

    // The class the student belongs to
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classId", nullable = false, referencedColumnName = "classId")
    private SchoolClass schoolClass;

    @Column(nullable = false)
    private String academicYear;

    private LocalDateTime enrollmentDate;

    private String status; // e.g., ACTIVE, COMPLETED, DROPPED

    @PrePersist
    public void prePersist() {
        this.enrollmentDate = LocalDateTime.now();
    }
}
