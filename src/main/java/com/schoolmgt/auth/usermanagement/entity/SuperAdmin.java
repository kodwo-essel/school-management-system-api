package com.schoolmgt.auth.usermanagement.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class SuperAdmin extends User{
    
}
