package com.schoolmgt.auth.usermanagement.entity;

import com.schoolmgt.auth.base.entity.BaseEntity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
public class Parent extends BaseEntity {
    private String name;
    private String email;
    private String phoneNumber;
}
