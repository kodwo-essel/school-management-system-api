package com.schoolmgt.auth.semester.service.impl;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.semester.dto.SemesterRequestDTO;
import com.schoolmgt.auth.semester.dto.SemesterResponseDTO;
import com.schoolmgt.auth.semester.entity.Semester;
import com.schoolmgt.auth.semester.repository.SemesterRepository;
import com.schoolmgt.auth.semester.service.SemesterService;
import com.schoolmgt.auth.utils.IdGenerators;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SemesterServiceImpl extends BaseServiceImpl<Semester, SemesterRepository> implements SemesterService{

    private final SemesterRepository semesterRepository;
    
    public SemesterServiceImpl(SemesterRepository semesterRepository) {
        super(semesterRepository);
        this.semesterRepository = semesterRepository;
    }

    @Override
    public SemesterResponseDTO addSemester(SemesterRequestDTO semesterRequestDTO) {
        Semester semester = new Semester();
        semester.setName(semesterRequestDTO.getName());
        semester.setSchoolId(semesterRequestDTO.getSchoolId());

        Semester sem = semesterRepository.save(semester);

        if (sem.getSemesterId() == null) {
            String semId = IdGenerators.generateSemesterId(sem.getId(), sem.getSchoolId());
            sem.setSemesterId(semId);
            semesterRepository.save(sem);
        }

        return convertToResponseDTO(sem);
    }


    private SemesterResponseDTO convertToResponseDTO(Semester semester) {
        SemesterResponseDTO responseDTO = new SemesterResponseDTO();
        responseDTO.setSemesterId(semester.getSemesterId());
        responseDTO.setName(semester.getName());
        responseDTO.setSchoolId(semester.getSchoolId());
        return responseDTO;
    }
}
