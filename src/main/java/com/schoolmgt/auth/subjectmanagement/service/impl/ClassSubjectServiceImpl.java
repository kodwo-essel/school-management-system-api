package com.schoolmgt.auth.subjectmanagement.service.impl;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.subjectmanagement.dto.ClassSubjectRequestDTO;
import com.schoolmgt.auth.subjectmanagement.dto.ClassSubjectResponseDTO;
import com.schoolmgt.auth.subjectmanagement.entity.ClassSubject;
import com.schoolmgt.auth.subjectmanagement.repository.ClassSubjectRepository;
import com.schoolmgt.auth.subjectmanagement.service.ClassSubjectService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClassSubjectServiceImpl extends BaseServiceImpl<ClassSubject, ClassSubjectRepository> implements ClassSubjectService{

    private final ClassSubjectRepository classSubjectRepository;
    
    public ClassSubjectServiceImpl(ClassSubjectRepository repository, ClassSubjectRepository classSubjectRepository) {
        super(repository);
        this.classSubjectRepository = classSubjectRepository;
    }

    @Override
    public ClassSubjectResponseDTO assignSubjectToClass(ClassSubjectRequestDTO classSubjectRequestDTO) {
        ClassSubject classSubject = new ClassSubject();
        classSubject.setClassId(classSubjectRequestDTO.getClassId());
        classSubject.setSubjectId(classSubjectRequestDTO.getSubjectId());
        classSubject.setSemesterId(classSubjectRequestDTO.getSemesterId());
        classSubject = classSubjectRepository.save(classSubject);

        return convertToResponseDTO(classSubject);
    }

    @Override
    public ClassSubjectResponseDTO assignTeacherToClassSubject(Long classSubjectId, String teacherId) {
        ClassSubject classSubject = classSubjectRepository
        .findById(classSubjectId)
        .orElseThrow(() -> new RuntimeException("ClassSubject not found"));


        classSubject.setTeacherId(teacherId);
        classSubject = classSubjectRepository.save(classSubject);

        return convertToResponseDTO(classSubject);
    }

    private ClassSubjectResponseDTO convertToResponseDTO(ClassSubject classSubject) {
        ClassSubjectResponseDTO responseDTO = new ClassSubjectResponseDTO();
        responseDTO.setId(classSubject.getId());
        responseDTO.setClassId(classSubject.getClassId());
        responseDTO.setSubjectId(classSubject.getSubjectId());
        responseDTO.setTeacherId(classSubject.getTeacherId());
        responseDTO.setSemesterId(classSubject.getSemesterId());
        
        return responseDTO;
    }
}
