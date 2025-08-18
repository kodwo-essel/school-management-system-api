package com.schoolmgt.auth.usermanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {

    @NotBlank(message = "Phone number is required")
    @Column(name = "phone", nullable = false, unique = true)
    private String phoneNumber;

    @NotBlank(message = "Access key is required")
    @Column(name = "access_key", nullable = false, unique = true)
    private String schoolId;

}
