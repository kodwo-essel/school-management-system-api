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
    private String name;

    @Column(nullable = false)
    private String academicYear;

    @Column(nullable = false)
    private String schoolId;

}
