package com.schoolmgt.auth.subjectmanagement.entity;

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
    name = "class_subjects",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"classId", "subjectId", "semester"})
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassSubject extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String classSubjectId; // Unique identifier for this class-subject combination

    private String classId;
    private String subjectId;
    private String teacherId;
    private String semesterId;

    private String academicYear; // e.g., "2024-2025"

    @Builder.Default
    private String status = "ACTIVE"; // "ACTIVE", "INACTIVE", "COMPLETED", "CANCELLED"

    private String schedule; // JSON object with day/time schedule

    private String classroom; // Room/location where subject is taught

    private Integer maxStudents; // Maximum students for this class-subject

    private Integer currentStudents; // Current enrolled students

    private String assessmentSchedule; // JSON object with assessment dates

    private String syllabusCoverage; // Percentage of syllabus covered

    private String notes; // Additional notes for this class-subject assignment

    private Double averageGrade; // Current average grade for this class-subject

    private String performanceRating; // "EXCELLENT", "GOOD", "AVERAGE", "POOR"
}
