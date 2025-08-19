package com.schoolmgt.auth.grades.service.impl;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.grades.dto.GradeRequestDTO;
import com.schoolmgt.auth.grades.dto.GradeResponseDTO;
import com.schoolmgt.auth.grades.entity.Grade;
import com.schoolmgt.auth.grades.repository.GradeRepository;
import com.schoolmgt.auth.grades.service.GradeService;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class GradeServiceImpl extends BaseServiceImpl<Grade, GradeRepository> implements GradeService{
    
    private GradeRepository gradeRepository;

    public GradeServiceImpl(GradeRepository repository, GradeRepository gradeRepository) {
        super(repository);
        this.gradeRepository = gradeRepository;
    }

    @Override
    public GradeResponseDTO saveGrade(GradeRequestDTO gradeRequestDTO) {
        Grade grade = new Grade();

        grade.setClassSubjectId(gradeRequestDTO.getClassSubjectId());
        grade.setStudentId(gradeRequestDTO.getStudentId());
        grade.setSbaScore(gradeRequestDTO.getSbaScore());
        grade.setSbaPercentage(gradeRequestDTO.getSbaPercentage());
        grade.setExamScore(gradeRequestDTO.getExamScore());
        grade.setExamPercentage(gradeRequestDTO.getExamPercentage());
        
        Grade savedGrade = gradeRepository.save(grade);

        return convertToResponseDTO(savedGrade);
        
    }


    private GradeResponseDTO convertToResponseDTO(Grade grade) {
        GradeResponseDTO gradeResponseDTO = new GradeResponseDTO();

        gradeResponseDTO.setClassSubjectId(grade.getClassSubjectId());
        gradeResponseDTO.setStudentId(grade.getStudentId());
        gradeResponseDTO.setSbaScore(grade.getSbaScore());
        gradeResponseDTO.setSbaPercentage(grade.getSbaPercentage());
        gradeResponseDTO.setExamScore(grade.getExamScore());
        gradeResponseDTO.setExamPercentage(grade.getExamPercentage());
        gradeResponseDTO.setTotalScore(grade.getTotalScore());
        gradeResponseDTO.setGradeLetter(grade.getGradeLetter());

        return gradeResponseDTO;
    }
}
