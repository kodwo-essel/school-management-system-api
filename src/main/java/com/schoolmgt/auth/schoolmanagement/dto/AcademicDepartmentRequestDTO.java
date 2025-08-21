package com.schoolmgt.auth.schoolmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcademicDepartmentRequestDTO {
    private String departmentId;
    private String name;
    private String schoolId;
    private String description;
}
