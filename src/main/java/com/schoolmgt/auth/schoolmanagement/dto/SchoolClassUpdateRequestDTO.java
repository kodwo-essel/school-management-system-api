package com.schoolmgt.auth.schoolmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolClassUpdateRequestDTO {
    private String name; // "P1A", "P2B", "JHS1A", "JHS2B"
    private String departmentId; // Which department (Pre-school, Primary, JHS)
    private String classTeacherId; // Teacher responsible for this class
    private Integer capacity; // Maximum number of students
}
