package com.schoolmgt.auth.usermanagement.entity;


import java.time.LocalDate;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Teacher extends User{
    private String firstName;
    private String lastName;
    private String otherNames;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String nationality;
    private String teacherId;
    private String schoolId;
    private String phoneNumber;
}
