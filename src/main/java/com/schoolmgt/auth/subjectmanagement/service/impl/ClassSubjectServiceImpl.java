package com.schoolmgt.auth.subjectmanagement.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.subjectmanagement.dto.*;
import com.schoolmgt.auth.subjectmanagement.entity.ClassSubject;
import com.schoolmgt.auth.subjectmanagement.repository.ClassSubjectRepository;
import com.schoolmgt.auth.subjectmanagement.service.ClassSubjectService;


import jakarta.persistence.EntityNotFoundException;
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
        // Check if subject is already assigned to this class for this semester
        if (classSubjectRepository.existsByClassIdAndSubjectIdAndSemesterId(
                classSubjectRequestDTO.getClassId(),
                classSubjectRequestDTO.getSubjectId(),
                classSubjectRequestDTO.getSemesterId())) {
            throw new IllegalArgumentException("Subject is already assigned to this class for this semester");
        }

        ClassSubject classSubject = ClassSubject.builder()
                .classId(classSubjectRequestDTO.getClassId())
                .subjectId(classSubjectRequestDTO.getSubjectId())
                .teacherId(classSubjectRequestDTO.getTeacherId())
                .semesterId(classSubjectRequestDTO.getSemesterId())
                .academicYear(classSubjectRequestDTO.getAcademicYear())
                .status(classSubjectRequestDTO.getStatus())
                .schedule(classSubjectRequestDTO.getSchedule())
                .classroom(classSubjectRequestDTO.getClassroom())
                .maxStudents(classSubjectRequestDTO.getMaxStudents())
                .currentStudents(0)
                .assessmentSchedule(classSubjectRequestDTO.getAssessmentSchedule())
                .notes(classSubjectRequestDTO.getNotes())
                .build();

        ClassSubject savedClassSubject = classSubjectRepository.save(classSubject);

        // Generate classSubjectId if not present
        if (savedClassSubject.getClassSubjectId() == null) {
            String classSubjectId = "CS-" + savedClassSubject.getId() + "-" +
                savedClassSubject.getClassId() + "-" + savedClassSubject.getSubjectId();
            savedClassSubject.setClassSubjectId(classSubjectId);
            savedClassSubject = classSubjectRepository.save(savedClassSubject);
        }

        return convertToResponseDTO(savedClassSubject);
    }

    @Override
    public ClassSubjectResponseDTO assignTeacherToClassSubject(Long classSubjectId, String teacherId) {
        ClassSubject classSubject = classSubjectRepository
                .findById(classSubjectId)
                .orElseThrow(() -> new EntityNotFoundException("ClassSubject not found with ID: " + classSubjectId));

        classSubject.setTeacherId(teacherId);
        ClassSubject updatedClassSubject = classSubjectRepository.save(classSubject);

        return convertToResponseDTO(updatedClassSubject);
    }

    @Override
    public ClassSubjectResponseDTO getClassSubjectById(String classSubjectId) {
        ClassSubject classSubject = classSubjectRepository.findByClassSubjectId(classSubjectId)
                .orElseThrow(() -> new EntityNotFoundException("ClassSubject not found with ID: " + classSubjectId));
        return convertToResponseDTO(classSubject);
    }

    @Override
    public List<ClassSubjectResponseDTO> getClassSubjectsByClass(String classId) {
        return classSubjectRepository.findByClassId(classId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassSubjectResponseDTO> getClassSubjectsBySubject(String subjectId) {
        return classSubjectRepository.findBySubjectId(subjectId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassSubjectResponseDTO> getClassSubjectsByTeacher(String teacherId) {
        return classSubjectRepository.findByTeacherId(teacherId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassSubjectResponseDTO> getClassSubjectsBySemester(String semesterId) {
        return classSubjectRepository.findBySemesterId(semesterId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassSubjectResponseDTO> getClassSubjectsByStatus(String status) {
        return classSubjectRepository.findByStatus(status).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassSubjectResponseDTO> getClassSubjectsByClassAndSemester(String classId, String semesterId) {
        return classSubjectRepository.findByClassIdAndSemesterId(classId, semesterId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassSubjectResponseDTO> getClassSubjectsByTeacherAndSemester(String teacherId, String semesterId) {
        return classSubjectRepository.findByTeacherIdAndSemesterId(teacherId, semesterId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassSubjectResponseDTO> getClassSubjectsByAcademicYear(String academicYear) {
        return classSubjectRepository.findByClassIdAndAcademicYear("", academicYear).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClassSubjectResponseDTO updateClassSubject(String classSubjectId, ClassSubjectUpdateRequestDTO updateRequest) {
        ClassSubject classSubject = classSubjectRepository.findByClassSubjectId(classSubjectId)
                .orElseThrow(() -> new EntityNotFoundException("ClassSubject not found with ID: " + classSubjectId));

        // Update fields if provided
        if (updateRequest.getTeacherId() != null) {
            classSubject.setTeacherId(updateRequest.getTeacherId());
        }
        if (updateRequest.getStatus() != null) {
            classSubject.setStatus(updateRequest.getStatus());
        }
        if (updateRequest.getSchedule() != null) {
            classSubject.setSchedule(updateRequest.getSchedule());
        }
        if (updateRequest.getClassroom() != null) {
            classSubject.setClassroom(updateRequest.getClassroom());
        }
        if (updateRequest.getMaxStudents() != null) {
            classSubject.setMaxStudents(updateRequest.getMaxStudents());
        }
        if (updateRequest.getAssessmentSchedule() != null) {
            classSubject.setAssessmentSchedule(updateRequest.getAssessmentSchedule());
        }
        if (updateRequest.getSyllabusCoverage() != null) {
            classSubject.setSyllabusCoverage(updateRequest.getSyllabusCoverage());
        }
        if (updateRequest.getNotes() != null) {
            classSubject.setNotes(updateRequest.getNotes());
        }
        if (updateRequest.getPerformanceRating() != null) {
            classSubject.setPerformanceRating(updateRequest.getPerformanceRating());
        }

        ClassSubject updatedClassSubject = classSubjectRepository.save(classSubject);
        return convertToResponseDTO(updatedClassSubject);
    }

    @Override
    public ClassSubjectResponseDTO updateClassSubjectStatus(String classSubjectId, String status) {
        ClassSubject classSubject = classSubjectRepository.findByClassSubjectId(classSubjectId)
                .orElseThrow(() -> new EntityNotFoundException("ClassSubject not found with ID: " + classSubjectId));

        classSubject.setStatus(status);
        ClassSubject updatedClassSubject = classSubjectRepository.save(classSubject);
        return convertToResponseDTO(updatedClassSubject);
    }

    @Override
    public ClassSubjectResponseDTO updateStudentCount(String classSubjectId, Integer studentCount) {
        ClassSubject classSubject = classSubjectRepository.findByClassSubjectId(classSubjectId)
                .orElseThrow(() -> new EntityNotFoundException("ClassSubject not found with ID: " + classSubjectId));

        classSubject.setCurrentStudents(studentCount);
        ClassSubject updatedClassSubject = classSubjectRepository.save(classSubject);
        return convertToResponseDTO(updatedClassSubject);
    }

    @Override
    public ClassSubjectResponseDTO updateSyllabusCoverage(String classSubjectId, String coverage) {
        ClassSubject classSubject = classSubjectRepository.findByClassSubjectId(classSubjectId)
                .orElseThrow(() -> new EntityNotFoundException("ClassSubject not found with ID: " + classSubjectId));

        classSubject.setSyllabusCoverage(coverage);
        ClassSubject updatedClassSubject = classSubjectRepository.save(classSubject);
        return convertToResponseDTO(updatedClassSubject);
    }

    @Override
    public ClassSubjectResponseDTO updateAverageGrade(String classSubjectId, Double averageGrade) {
        ClassSubject classSubject = classSubjectRepository.findByClassSubjectId(classSubjectId)
                .orElseThrow(() -> new EntityNotFoundException("ClassSubject not found with ID: " + classSubjectId));

        classSubject.setAverageGrade(averageGrade);
        ClassSubject updatedClassSubject = classSubjectRepository.save(classSubject);
        return convertToResponseDTO(updatedClassSubject);
    }

    @Override
    public void removeSubjectFromClass(String classSubjectId) {
        ClassSubject classSubject = classSubjectRepository.findByClassSubjectId(classSubjectId)
                .orElseThrow(() -> new EntityNotFoundException("ClassSubject not found with ID: " + classSubjectId));

        // Check if there are active students or grades before deletion
        if (classSubject.getCurrentStudents() != null && classSubject.getCurrentStudents() > 0) {
            throw new IllegalStateException("Cannot remove subject with active students");
        }

        classSubjectRepository.delete(classSubject);
    }

    @Override
    public void transferTeacher(String fromClassSubjectId, String toClassSubjectId, String teacherId) {
        // Remove teacher from source class-subject
        ClassSubject fromClassSubject = classSubjectRepository.findByClassSubjectId(fromClassSubjectId)
                .orElseThrow(() -> new EntityNotFoundException("Source ClassSubject not found with ID: " + fromClassSubjectId));
        fromClassSubject.setTeacherId(null);
        classSubjectRepository.save(fromClassSubject);

        // Assign teacher to target class-subject
        ClassSubject toClassSubject = classSubjectRepository.findByClassSubjectId(toClassSubjectId)
                .orElseThrow(() -> new EntityNotFoundException("Target ClassSubject not found with ID: " + toClassSubjectId));
        toClassSubject.setTeacherId(teacherId);
        classSubjectRepository.save(toClassSubject);
    }

    @Override
    public long getActiveSubjectCountByClass(String classId) {
        return classSubjectRepository.countActiveSubjectsByClass(classId);
    }

    @Override
    public long getActiveClassSubjectCountByTeacher(String teacherId) {
        return classSubjectRepository.countActiveClassSubjectsByTeacher(teacherId);
    }

    @Override
    public boolean isSubjectAssignedToClass(String classId, String subjectId, String semesterId) {
        return classSubjectRepository.existsByClassIdAndSubjectIdAndSemesterId(classId, subjectId, semesterId);
    }

    @Override
    public List<ClassSubjectResponseDTO> getTeacherWorkload(String teacherId, String academicYear) {
        return classSubjectRepository.findByTeacherIdAndAcademicYear(teacherId, academicYear).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassSubjectResponseDTO> getClassSchedule(String classId, String semesterId) {
        return classSubjectRepository.findByClassIdAndSemesterId(classId, semesterId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private ClassSubjectResponseDTO convertToResponseDTO(ClassSubject classSubject) {
        return ClassSubjectResponseDTO.builder()
                .id(classSubject.getId())
                .classSubjectId(classSubject.getClassSubjectId())
                .classId(classSubject.getClassId())
                .className("Class Name") // Would need class service integration
                .subjectId(classSubject.getSubjectId())
                .subjectName("Subject Name") // Would need subject service integration
                .teacherId(classSubject.getTeacherId())
                .teacherName("Teacher Name") // Would need teacher service integration
                .semesterId(classSubject.getSemesterId())
                .semesterName("Semester Name") // Would need semester service integration
                .academicYear(classSubject.getAcademicYear())
                .status(classSubject.getStatus())
                .schedule(classSubject.getSchedule())
                .classroom(classSubject.getClassroom())
                .maxStudents(classSubject.getMaxStudents())
                .currentStudents(classSubject.getCurrentStudents())
                .assessmentSchedule(classSubject.getAssessmentSchedule())
                .syllabusCoverage(classSubject.getSyllabusCoverage())
                .notes(classSubject.getNotes())
                .averageGrade(classSubject.getAverageGrade())
                .performanceRating(classSubject.getPerformanceRating())
                .createdAt(classSubject.getCreatedAt())
                .updatedAt(classSubject.getUpdatedAt())
                .build();
    }
}
