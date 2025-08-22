package com.schoolmgt.auth.schoolmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.schoolmgt.auth.base.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "classes", uniqueConstraints = @UniqueConstraint(columnNames = {"academicYear", "name"}))
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolClass extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Class unique identifier (CLS-0001-0001)
    @Column(unique = true)
    private String classId;

    @Column(nullable = false)
    private String name; // "P1A", "P2B", "JHS1A", "JHS2B"

    @Column(nullable = false)
    private String schoolId; // Which school this class belongs to

    @Column(nullable = false)
    private String departmentId; // Which department (Pre-school, Primary, JHS)

    @Column(nullable = false)
    private String academicYear; // "2024-2025"

    private String classTeacherId; // Teacher responsible for this class

    private Integer capacity; // Maximum number of students

    private Integer currentStudents; // Current number of enrolled students

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
