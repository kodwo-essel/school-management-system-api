package com.schoolmgt.auth.usermanagement.dto;

import java.time.LocalDate;

import com.schoolmgt.auth.usermanagement.entity.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRegistrationDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String otherNames;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String nationality;
    private String teacherId;
    private String schoolId;
    private String phoneNumber;
    private String password;
}
