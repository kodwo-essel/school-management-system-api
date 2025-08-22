package com.schoolmgt.auth.grades.entity;

import com.schoolmgt.auth.base.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "grades",
    uniqueConstraints = @UniqueConstraint(columnNames = {"studentId", "classSubjectId"})
)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Grade extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentId;
    private String classSubjectId;

    private Double sbaScore;
    private Double examScore;

    private Double sbaPercentage;
    private Double examPercentage;

    private Double totalScore;

    private String gradeLetter;

    private String semesterId; // Reference to semester

    private String academicYear; // e.g., "2024-2025"

    private String term; // e.g., "FIRST_TERM", "SECOND_TERM"

    private String teacherId; // Teacher who recorded the grade

    private String remarks; // Optional teacher remarks
}
