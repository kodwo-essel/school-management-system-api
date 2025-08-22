package com.schoolmgt.auth.schoolmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.schoolmgt.auth.base.repository.BaseRepository;
import com.schoolmgt.auth.schoolmanagement.entity.School;

public interface SchoolRepository extends BaseRepository<School>{

    Optional<School> findBySchoolId(String schoolId);

    Optional<School> findByName(String name);

    List<School> findByCity(String city);

    List<School> findByRegion(String region);

    List<School> findByCountry(String country);

    @Query("SELECT s FROM School s WHERE s.name LIKE %:name%")
    List<School> findByNameContaining(@Param("name") String name);

    boolean existsByName(String name);

    boolean existsByNameAndSchoolIdNot(String name, String schoolId);
}
