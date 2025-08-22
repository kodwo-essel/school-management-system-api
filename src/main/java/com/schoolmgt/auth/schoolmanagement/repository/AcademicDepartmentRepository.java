package com.schoolmgt.auth.schoolmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.schoolmanagement.entity.AcademicDepartment;

public interface AcademicDepartmentRepository extends BaseRepository<AcademicDepartment>{

    Optional<AcademicDepartment> findByNameAndSchoolId(String name, String schoolId);

    Optional<AcademicDepartment> findByDepartmentId(String departmentId);

    List<AcademicDepartment> findBySchoolId(String schoolId);

    List<AcademicDepartment> findByHeadOfDepartment(String headOfDepartment);

    @Query("SELECT ad FROM AcademicDepartment ad WHERE ad.name LIKE %:name%")
    List<AcademicDepartment> findByNameContaining(@Param("name") String name);

    @Query("SELECT ad FROM AcademicDepartment ad WHERE ad.schoolId = :schoolId AND ad.name LIKE %:name%")
    List<AcademicDepartment> findBySchoolIdAndNameContaining(@Param("schoolId") String schoolId, @Param("name") String name);

    @Query("SELECT COUNT(ad) FROM AcademicDepartment ad WHERE ad.schoolId = :schoolId")
    long countActiveDepartmentsBySchool(@Param("schoolId") String schoolId);

    boolean existsByNameAndSchoolId(String name, String schoolId);
}
