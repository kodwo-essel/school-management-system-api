package com.schoolmgt.auth.subjectmanagement.entity;

import com.schoolmgt.auth.base.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subjects")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subject extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String subjectId;

    @Column(nullable = false)
    private String name; // "Mathematics", "English", "Science", "Social Studies"

    @Column(nullable = false)
    private String schoolId;

    private String departmentId; // Which department this subject belongs to

    private String description; // Simple subject description
}
