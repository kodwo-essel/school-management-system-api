package com.schoolmgt.auth.semester.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SemesterSearchDTO {
    private String name;
    private String schoolId;
    private String academicYear;
    private String status;
    private LocalDate startDateFrom;
    private LocalDate startDateTo;
    private LocalDate endDateFrom;
    private LocalDate endDateTo;
    private Integer minWeeks;
    private Integer maxWeeks;
    private Boolean isCurrentSemester;
}
