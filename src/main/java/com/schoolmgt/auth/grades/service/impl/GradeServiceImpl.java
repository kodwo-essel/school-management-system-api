package com.schoolmgt.auth.grades.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.grades.dto.*;
import com.schoolmgt.auth.grades.entity.Grade;
import com.schoolmgt.auth.grades.repository.GradeRepository;
import com.schoolmgt.auth.grades.service.GradeService;

import jakarta.persistence.EntityNotFoundException;
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
        // Check if grade already exists
        gradeRepository.findByStudentIdAndClassSubjectId(
                gradeRequestDTO.getStudentId(), gradeRequestDTO.getClassSubjectId())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Grade already exists for this student and subject");
                });

        Grade grade = Grade.builder()
                .classSubjectId(gradeRequestDTO.getClassSubjectId())
                .studentId(gradeRequestDTO.getStudentId())
                .sbaScore(gradeRequestDTO.getSbaScore())
                .sbaPercentage(gradeRequestDTO.getSbaPercentage())
                .examScore(gradeRequestDTO.getExamScore())
                .examPercentage(gradeRequestDTO.getExamPercentage())
                .semesterId(gradeRequestDTO.getSemesterId())
                .academicYear(gradeRequestDTO.getAcademicYear())
                .term(gradeRequestDTO.getTerm())
                .teacherId(gradeRequestDTO.getTeacherId())
                .remarks(gradeRequestDTO.getRemarks())
                .build();

        // Calculate total score, grade letter, and grade points
        calculateGradeMetrics(grade);

        Grade savedGrade = gradeRepository.save(grade);
        return convertToResponseDTO(savedGrade);
    }

    @Override
    public List<GradeResponseDTO> saveBulkGrades(BulkGradeRequestDTO bulkGradeRequestDTO) {
        List<Grade> grades = bulkGradeRequestDTO.getGradeRecords().stream()
                .map(record -> {
                    Grade grade = Grade.builder()
                            .studentId(record.getStudentId())
                            .classSubjectId(bulkGradeRequestDTO.getClassSubjectId())
                            .sbaScore(record.getSbaScore())
                            .examScore(record.getExamScore())
                            .sbaPercentage(record.getSbaPercentage())
                            .examPercentage(record.getExamPercentage())
                            .semesterId(bulkGradeRequestDTO.getSemesterId())
                            .academicYear(bulkGradeRequestDTO.getAcademicYear())
                            .term(bulkGradeRequestDTO.getTerm())
                            .teacherId(bulkGradeRequestDTO.getTeacherId())
                            .remarks(record.getRemarks())
                            .build();

                    calculateGradeMetrics(grade);
                    return grade;
                })
                .collect(Collectors.toList());

        List<Grade> savedGrades = gradeRepository.saveAll(grades);
        return savedGrades.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GradeResponseDTO getGradeByStudentAndSubject(String studentId, String classSubjectId) {
        Grade grade = gradeRepository.findByStudentIdAndClassSubjectId(studentId, classSubjectId)
                .orElseThrow(() -> new EntityNotFoundException("Grade not found for student: " + studentId + " and subject: " + classSubjectId));
        return convertToResponseDTO(grade);
    }

    @Override
    public List<GradeResponseDTO> getGradesByStudent(String studentId) {
        return gradeRepository.findByStudentId(studentId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<GradeResponseDTO> getGradesByStudentAndTerm(String studentId, String academicYear, String term) {
        return gradeRepository.findByStudentId(studentId).stream()
                .filter(grade -> academicYear.equals(grade.getAcademicYear()) && term.equals(grade.getTerm()))
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<GradeResponseDTO> getGradesByClassSubject(String classSubjectId) {
        return gradeRepository.findByClassSubjectId(classSubjectId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<GradeResponseDTO> getGradesByClassSubjectAndTerm(String classSubjectId, String academicYear, String term) {
        return gradeRepository.findByClassSubjectId(classSubjectId).stream()
                .filter(grade -> academicYear.equals(grade.getAcademicYear()) && term.equals(grade.getTerm()))
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentGradeReportDTO getStudentGradeReport(String studentId, String academicYear, String term) {
        List<Grade> grades = gradeRepository.findByStudentId(studentId).stream()
                .filter(grade -> academicYear.equals(grade.getAcademicYear()) && term.equals(grade.getTerm()))
                .collect(Collectors.toList());

        if (grades.isEmpty()) {
            throw new EntityNotFoundException("No grades found for student: " + studentId + " in " + academicYear + " " + term);
        }

        List<GradeResponseDTO> gradeResponses = grades.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

        Double overallAverage = grades.stream()
                .mapToDouble(Grade::getTotalScore)
                .average()
                .orElse(0.0);

        String overallGrade = calculateGradeLetter(overallAverage);
        Double gpa = calculateGPA(grades);

        int totalSubjects = grades.size();
        int passedSubjects = (int) grades.stream().filter(g -> g.getTotalScore() >= 50).count();
        int failedSubjects = totalSubjects - passedSubjects;

        String academicStatus = getAcademicStatus(overallAverage);

        return StudentGradeReportDTO.builder()
                .studentId(studentId)
                .studentName("Student Name") // Would need student service integration
                .academicYear(academicYear)
                .term(term)
                .grades(gradeResponses)
                .overallAverage(overallAverage)
                .overallGrade(overallGrade)
                .gpa(gpa)
                .totalSubjects(totalSubjects)
                .passedSubjects(passedSubjects)
                .failedSubjects(failedSubjects)
                .academicStatus(academicStatus)
                .build();
    }


    @Override
    public ClassGradeReportDTO getClassGradeReport(String classSubjectId, String academicYear, String term) {
        List<Grade> grades = gradeRepository.findByClassSubjectId(classSubjectId).stream()
                .filter(grade -> academicYear.equals(grade.getAcademicYear()) && term.equals(grade.getTerm()))
                .collect(Collectors.toList());

        if (grades.isEmpty()) {
            throw new EntityNotFoundException("No grades found for class subject: " + classSubjectId);
        }

        Double classAverage = grades.stream().mapToDouble(Grade::getTotalScore).average().orElse(0.0);
        Double highestScore = grades.stream().mapToDouble(Grade::getTotalScore).max().orElse(0.0);
        Double lowestScore = grades.stream().mapToDouble(Grade::getTotalScore).min().orElse(0.0);

        String topStudentId = grades.stream()
                .max((g1, g2) -> Double.compare(g1.getTotalScore(), g2.getTotalScore()))
                .map(Grade::getStudentId)
                .orElse("");

        // Calculate grade distribution
        Map<String, Long> gradeCount = grades.stream()
                .collect(Collectors.groupingBy(Grade::getGradeLetter, Collectors.counting()));

        List<ClassGradeReportDTO.GradeDistribution> gradeDistribution = gradeCount.entrySet().stream()
                .map(entry -> ClassGradeReportDTO.GradeDistribution.builder()
                        .gradeLetter(entry.getKey())
                        .count(entry.getValue().intValue())
                        .percentage((entry.getValue().doubleValue() / grades.size()) * 100)
                        .build())
                .collect(Collectors.toList());

        // Create student grade summaries with positions
        List<Grade> sortedGrades = grades.stream()
                .sorted((g1, g2) -> Double.compare(g2.getTotalScore(), g1.getTotalScore()))
                .collect(Collectors.toList());

        List<ClassGradeReportDTO.StudentGradeSummary> studentGrades = sortedGrades.stream()
                .map(grade -> {
                    int position = sortedGrades.indexOf(grade) + 1;
                    return ClassGradeReportDTO.StudentGradeSummary.builder()
                            .studentId(grade.getStudentId())
                            .studentName("Student Name") // Would need student service integration
                            .totalScore(grade.getTotalScore())
                            .gradeLetter(grade.getGradeLetter())
                            .position(position)
                            .build();
                })
                .collect(Collectors.toList());

        return ClassGradeReportDTO.builder()
                .classSubjectId(classSubjectId)
                .subjectName("Subject Name") // Would need subject service integration
                .className("Class Name") // Would need class service integration
                .academicYear(academicYear)
                .term(term)
                .totalStudents(grades.size())
                .classAverage(classAverage)
                .highestScore(highestScore)
                .lowestScore(lowestScore)
                .topStudentId(topStudentId)
                .gradeDistribution(gradeDistribution)
                .studentGrades(studentGrades)
                .build();
    }

    @Override
    public GradeStatisticsDTO getGradeStatistics(String classSubjectId, String academicYear, String term) {
        List<Grade> grades = gradeRepository.findByClassSubjectId(classSubjectId).stream()
                .filter(grade -> academicYear.equals(grade.getAcademicYear()) && term.equals(grade.getTerm()))
                .collect(Collectors.toList());

        if (grades.isEmpty()) {
            return GradeStatisticsDTO.builder()
                    .classSubjectId(classSubjectId)
                    .academicYear(academicYear)
                    .term(term)
                    .totalStudents(0)
                    .build();
        }

        Double averageScore = grades.stream().mapToDouble(Grade::getTotalScore).average().orElse(0.0);
        Double highestScore = grades.stream().mapToDouble(Grade::getTotalScore).max().orElse(0.0);
        Double lowestScore = grades.stream().mapToDouble(Grade::getTotalScore).min().orElse(0.0);

        // Calculate standard deviation
        Double variance = grades.stream()
                .mapToDouble(grade -> Math.pow(grade.getTotalScore() - averageScore, 2))
                .average()
                .orElse(0.0);
        Double standardDeviation = Math.sqrt(variance);

        // Calculate pass rate (assuming 50 is passing grade)
        long passedCount = grades.stream().filter(grade -> grade.getTotalScore() >= 50).count();
        Double passRate = (passedCount * 100.0) / grades.size();

        // Grade distribution
        Map<String, Integer> gradeDistribution = grades.stream()
                .collect(Collectors.groupingBy(Grade::getGradeLetter,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));

        Map<String, Double> gradePercentages = gradeDistribution.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (entry.getValue() * 100.0) / grades.size()
                ));

        return GradeStatisticsDTO.builder()
                .classSubjectId(classSubjectId)
                .subjectName("Subject Name") // Would need subject service integration
                .academicYear(academicYear)
                .term(term)
                .totalStudents(grades.size())
                .averageScore(averageScore)
                .highestScore(highestScore)
                .lowestScore(lowestScore)
                .standardDeviation(standardDeviation)
                .passRate(passRate)
                .gradeDistribution(gradeDistribution)
                .gradePercentages(gradePercentages)
                .build();
    }

    @Override
    public TranscriptDTO getStudentTranscript(String studentId, String academicYear) {
        List<Grade> allGrades = gradeRepository.findByStudentId(studentId).stream()
                .filter(grade -> academicYear.equals(grade.getAcademicYear()))
                .collect(Collectors.toList());

        if (allGrades.isEmpty()) {
            throw new EntityNotFoundException("No grades found for student: " + studentId + " in " + academicYear);
        }

        // Group grades by term
        Map<String, List<GradeResponseDTO>> termGrades = allGrades.stream()
                .collect(Collectors.groupingBy(Grade::getTerm,
                        Collectors.mapping(this::convertToResponseDTO, Collectors.toList())));

        // Calculate term averages
        Map<String, Double> termAverages = allGrades.stream()
                .collect(Collectors.groupingBy(Grade::getTerm,
                        Collectors.averagingDouble(Grade::getTotalScore)));

        // Calculate term grades
        Map<String, String> termGrades_ = termAverages.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> calculateGradeLetter(entry.getValue())
                ));

        Double yearlyAverage = allGrades.stream().mapToDouble(Grade::getTotalScore).average().orElse(0.0);
        String yearlyGrade = calculateGradeLetter(yearlyAverage);
        String promotionStatus = getPromotionStatus(yearlyAverage, allGrades.size());

        return TranscriptDTO.builder()
                .studentId(studentId)
                .studentName("Student Name") // Would need student service integration
                .className("Class Name") // Would need class service integration
                .academicYear(academicYear)
                .termGrades(termGrades)
                .termAverages(termAverages)
                .termOverallGrades(termGrades_)
                .yearlyAverage(yearlyAverage)
                .yearlyGrade(yearlyGrade)
                .promotionStatus(promotionStatus)
                .build();
    }

    @Override
    public List<GradeResponseDTO> getTopPerformers(String classSubjectId, String academicYear, String term, int limit) {
        return gradeRepository.findByClassSubjectId(classSubjectId).stream()
                .filter(grade -> academicYear.equals(grade.getAcademicYear()) && term.equals(grade.getTerm()))
                .sorted((g1, g2) -> Double.compare(g2.getTotalScore(), g1.getTotalScore()))
                .limit(limit)
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<GradeResponseDTO> getGradesByRange(Double minScore, Double maxScore) {
        return gradeRepository.findByScoreRange(minScore, maxScore).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<GradeResponseDTO> getGradesByLetter(String gradeLetter) {
        return gradeRepository.findByGradeLetter(gradeLetter).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }



    @Override
    public Double calculateClassAverage(String classSubjectId, String academicYear, String term) {
        return gradeRepository.findByClassSubjectId(classSubjectId).stream()
                .filter(grade -> academicYear.equals(grade.getAcademicYear()) && term.equals(grade.getTerm()))
                .mapToDouble(Grade::getTotalScore)
                .average()
                .orElse(0.0);
    }

    @Override
    public GradeResponseDTO updateGrade(String studentId, String classSubjectId, GradeRequestDTO gradeRequestDTO) {
        Grade grade = gradeRepository.findByStudentIdAndClassSubjectId(studentId, classSubjectId)
                .orElseThrow(() -> new EntityNotFoundException("Grade not found for student: " + studentId + " and subject: " + classSubjectId));

        grade.setSbaScore(gradeRequestDTO.getSbaScore());
        grade.setExamScore(gradeRequestDTO.getExamScore());
        grade.setSbaPercentage(gradeRequestDTO.getSbaPercentage());
        grade.setExamPercentage(gradeRequestDTO.getExamPercentage());
        grade.setRemarks(gradeRequestDTO.getRemarks());

        calculateGradeMetrics(grade);

        Grade updatedGrade = gradeRepository.save(grade);
        return convertToResponseDTO(updatedGrade);
    }



    @Override
    public void deleteGrade(String studentId, String classSubjectId) {
        Grade grade = gradeRepository.findByStudentIdAndClassSubjectId(studentId, classSubjectId)
                .orElseThrow(() -> new EntityNotFoundException("Grade not found for student: " + studentId + " and subject: " + classSubjectId));
        gradeRepository.delete(grade);
    }



    // Helper methods
    private void calculateGradeMetrics(Grade grade) {
        // Calculate total score
        Double sbaContribution = (grade.getSbaScore() * grade.getSbaPercentage()) / 100;
        Double examContribution = (grade.getExamScore() * grade.getExamPercentage()) / 100;
        Double totalScore = sbaContribution + examContribution;

        grade.setTotalScore(totalScore);
        grade.setGradeLetter(calculateGradeLetter(totalScore));
    }

    private String calculateGradeLetter(Double score) {
        if (score >= 90) return "A+";
        if (score >= 85) return "A";
        if (score >= 80) return "B+";
        if (score >= 75) return "B";
        if (score >= 70) return "C+";
        if (score >= 65) return "C";
        if (score >= 60) return "D+";
        if (score >= 50) return "D";
        return "F";
    }

    private Double calculateGradePoints(Double score) {
        if (score >= 90) return 4.0;
        if (score >= 85) return 3.7;
        if (score >= 80) return 3.3;
        if (score >= 75) return 3.0;
        if (score >= 70) return 2.7;
        if (score >= 65) return 2.3;
        if (score >= 60) return 2.0;
        if (score >= 50) return 1.0;
        return 0.0;
    }

    private Double calculateGPA(List<Grade> grades) {
        if (grades.isEmpty()) return 0.0;

        return grades.stream()
                .mapToDouble(grade -> calculateGradePoints(grade.getTotalScore()))
                .average()
                .orElse(0.0);
    }

    private String getAcademicStatus(Double average) {
        if (average >= 85) return "EXCELLENT";
        if (average >= 75) return "GOOD";
        if (average >= 65) return "AVERAGE";
        return "POOR";
    }

    private String getPromotionStatus(Double average, int totalSubjects) {
        if (average >= 50 && totalSubjects >= 6) return "PROMOTED";
        if (average >= 40) return "PROBATION";
        return "REPEAT";
    }

    private GradeResponseDTO convertToResponseDTO(Grade grade) {
        return GradeResponseDTO.builder()
                .id(grade.getId())
                .classSubjectId(grade.getClassSubjectId())
                .studentId(grade.getStudentId())
                .sbaScore(grade.getSbaScore())
                .sbaPercentage(grade.getSbaPercentage())
                .examScore(grade.getExamScore())
                .examPercentage(grade.getExamPercentage())
                .totalScore(grade.getTotalScore())
                .gradeLetter(grade.getGradeLetter())
                .semesterId(grade.getSemesterId())
                .academicYear(grade.getAcademicYear())
                .term(grade.getTerm())
                .teacherId(grade.getTeacherId())
                .remarks(grade.getRemarks())
                .build();
    }
}
