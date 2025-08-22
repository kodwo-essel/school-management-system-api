package com.schoolmgt.auth.schoolmanagement.entity;

import com.schoolmgt.auth.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "academic_departments")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcademicDepartment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String departmentId;

    @Column(nullable = false)
    private String name; // "Pre-school", "Lower Primary", "Upper Primary", "JHS"

    @Column(nullable = false)
    private String schoolId;

    private String description;

    private String headOfDepartment; // Teacher ID who heads the department

}
