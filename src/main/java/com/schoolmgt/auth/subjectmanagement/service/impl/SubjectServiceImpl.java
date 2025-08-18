package com.schoolmgt.auth.subjectmanagement.service.impl;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.subjectmanagement.dto.SubjectRequestDTO;
import com.schoolmgt.auth.subjectmanagement.dto.SubjectResponseDTO;
import com.schoolmgt.auth.subjectmanagement.entity.Subject;
import com.schoolmgt.auth.subjectmanagement.repository.SubjectRepository;
import com.schoolmgt.auth.subjectmanagement.service.SubjectService;
import com.schoolmgt.auth.utils.IdGenerators;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class SubjectServiceImpl extends BaseServiceImpl<Subject, SubjectRepository> implements SubjectService {

    private final SubjectRepository subjectRepository;
    
    public SubjectServiceImpl(SubjectRepository repository, SubjectRepository subjectRepository) {
        super(repository);
        this.subjectRepository = subjectRepository;
    }

    @Override
    public SubjectResponseDTO createSubject(SubjectRequestDTO requestDto) {
        Subject subject = new Subject();
        subject.setName(requestDto.getName());
        subject.setSchoolId(requestDto.getSchoolId());

        Subject savedSubject = subjectRepository.save(subject);

        if (savedSubject.getSubjectId() == null) {
            String subjectId = IdGenerators.generateSubjectId(savedSubject.getId(), savedSubject.getSchoolId());
            savedSubject.setSubjectId(subjectId);
            savedSubject = subjectRepository.save(savedSubject);
        }
        return convertToResponseDto(savedSubject);
    
    }

    
    private SubjectResponseDTO convertToResponseDto(Subject subject) {
        SubjectResponseDTO responseDto = new SubjectResponseDTO();
        responseDto.setSubjectId(subject.getSubjectId());
        responseDto.setName(subject.getName());
        responseDto.setSchoolId(subject.getSchoolId());
        return responseDto;
    }
}
