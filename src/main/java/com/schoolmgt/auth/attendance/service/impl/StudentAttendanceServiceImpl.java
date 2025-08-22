package com.schoolmgt.auth.attendance.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.attendance.dto.AttendanceSummaryDTO;
import com.schoolmgt.auth.attendance.dto.BulkStudentAttendanceRequestDTO;
import com.schoolmgt.auth.attendance.dto.ClassAttendanceReportDTO;
import com.schoolmgt.auth.attendance.dto.StudentAttendanceRequestDTO;
import com.schoolmgt.auth.attendance.dto.StudentAttendanceResponseDTO;
import com.schoolmgt.auth.attendance.entity.StudentAttendance;
import com.schoolmgt.auth.attendance.repository.StudentAttendanceRepository;
import com.schoolmgt.auth.attendance.service.StudentAttendanceService;
import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class StudentAttendanceServiceImpl extends BaseServiceImpl<StudentAttendance, StudentAttendanceRepository> implements StudentAttendanceService {

    private final StudentAttendanceRepository studentAttendanceRepository;

    public StudentAttendanceServiceImpl(StudentAttendanceRepository repository, StudentAttendanceRepository studentAttendanceRepository) {
        super(repository);
        this.studentAttendanceRepository = studentAttendanceRepository;
    }

    @Override
    public StudentAttendanceResponseDTO recordAttendance(StudentAttendanceRequestDTO studentAttendanceRequestDTO) {
        StudentAttendance studentAttendance = new StudentAttendance();
        studentAttendance.setStudentId(studentAttendanceRequestDTO.getStudentId());
        studentAttendance.setDate(studentAttendanceRequestDTO.getDate());
        studentAttendance.setPresent(studentAttendanceRequestDTO.isPresent());
        studentAttendance.setTeacherId(studentAttendanceRequestDTO.getTeacherId());
        studentAttendanceRepository.save(studentAttendance);

        return convertToResponseDTO(studentAttendance);
    }
    

    @Override
    public List<StudentAttendanceResponseDTO> recordBulkAttendance(BulkStudentAttendanceRequestDTO bulkRequestDTO) {
        List<StudentAttendance> attendanceRecords = bulkRequestDTO.getAttendanceRecords().stream()
                .map(record -> StudentAttendance.builder()
                        .studentId(record.getStudentId())
                        .date(bulkRequestDTO.getDate())
                        .isPresent(record.isPresent())
                        .teacherId(bulkRequestDTO.getTeacherId())
                        .remarks(record.getRemarks())
                        .attendanceType("REGULAR")
                        .build())
                .collect(Collectors.toList());

        List<StudentAttendance> savedRecords = studentAttendanceRepository.saveAll(attendanceRecords);
        return savedRecords.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentAttendanceResponseDTO getStudentAttendanceByDate(String studentId, LocalDate date) {
        StudentAttendance attendance = studentAttendanceRepository.findByStudentIdAndDate(studentId, date)
                .orElseThrow(() -> new EntityNotFoundException("No attendance record found for student: " + studentId + " on date: " + date));
        return convertToResponseDTO(attendance);
    }

    @Override
    public List<StudentAttendanceResponseDTO> getStudentAttendanceHistory(String studentId) {
        return studentAttendanceRepository.findByStudentId(studentId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentAttendanceResponseDTO> getStudentAttendanceByDateRange(String studentId, LocalDate startDate, LocalDate endDate) {
        return studentAttendanceRepository.findByStudentIdAndDateBetween(studentId, startDate, endDate).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentAttendanceResponseDTO> getAttendanceByDate(LocalDate date) {
        return studentAttendanceRepository.findByDate(date).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentAttendanceResponseDTO> getAttendanceByDateRange(LocalDate startDate, LocalDate endDate) {
        return studentAttendanceRepository.findByDateBetween(startDate, endDate).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClassAttendanceReportDTO getClassAttendanceReport(String classId, LocalDate date) {
        // This would require integration with student enrollment to get students in class
        // For now, returning a basic implementation
        List<StudentAttendance> attendances = studentAttendanceRepository.findByDate(date);

        List<StudentAttendanceResponseDTO> studentAttendances = attendances.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

        int totalStudents = studentAttendances.size();
        int presentStudents = (int) studentAttendances.stream().filter(StudentAttendanceResponseDTO::isPresent).count();
        int absentStudents = totalStudents - presentStudents;
        double attendancePercentage = totalStudents > 0 ? (double) presentStudents / totalStudents * 100 : 0;

        return ClassAttendanceReportDTO.builder()
                .classId(classId)
                .className("Class Name") // Would need class service integration
                .date(date)
                .totalStudents(totalStudents)
                .presentStudents(presentStudents)
                .absentStudents(absentStudents)
                .attendancePercentage(attendancePercentage)
                .studentAttendances(studentAttendances)
                .build();
    }

    @Override
    public List<ClassAttendanceReportDTO> getClassAttendanceReportByDateRange(String classId, LocalDate startDate, LocalDate endDate) {
        // Implementation would iterate through date range and generate reports for each day
        throw new UnsupportedOperationException("Class attendance report by date range requires student enrollment integration");
    }

    @Override
    public AttendanceSummaryDTO getStudentAttendanceSummary(String studentId, LocalDate startDate, LocalDate endDate) {
        long totalDays = studentAttendanceRepository.countTotalDaysByStudentAndDateRange(studentId, startDate, endDate);
        long presentDays = studentAttendanceRepository.countPresentDaysByStudentAndDateRange(studentId, startDate, endDate);
        long absentDays = studentAttendanceRepository.countAbsentDaysByStudentAndDateRange(studentId, startDate, endDate);

        double attendancePercentage = totalDays > 0 ? (double) presentDays / totalDays * 100 : 0;
        String status = getAttendanceStatus(attendancePercentage);

        return AttendanceSummaryDTO.builder()
                .studentId(studentId)
                .studentName("Student Name") // Would need student service integration
                .startDate(startDate)
                .endDate(endDate)
                .totalDays(totalDays)
                .presentDays(presentDays)
                .absentDays(absentDays)
                .attendancePercentage(attendancePercentage)
                .status(status)
                .build();
    }

    @Override
    public List<AttendanceSummaryDTO> getClassAttendanceSummary(String classId, LocalDate startDate, LocalDate endDate) {
        // This would require integration with student enrollment to get students in class
        throw new UnsupportedOperationException("Class attendance summary requires student enrollment integration");
    }

    @Override
    public List<StudentAttendanceResponseDTO> getAbsentStudents(LocalDate date) {
        return studentAttendanceRepository.findByDate(date).stream()
                .filter(attendance -> !attendance.isPresent())
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentAttendanceResponseDTO> getAbsentStudentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return studentAttendanceRepository.findByDateBetween(startDate, endDate).stream()
                .filter(attendance -> !attendance.isPresent())
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public double calculateAttendancePercentage(String studentId, LocalDate startDate, LocalDate endDate) {
        long totalDays = studentAttendanceRepository.countTotalDaysByStudentAndDateRange(studentId, startDate, endDate);
        long presentDays = studentAttendanceRepository.countPresentDaysByStudentAndDateRange(studentId, startDate, endDate);

        return totalDays > 0 ? (double) presentDays / totalDays * 100 : 0;
    }

    @Override
    public StudentAttendanceResponseDTO updateAttendance(String studentId, LocalDate date, boolean isPresent, String remarks) {
        StudentAttendance attendance = studentAttendanceRepository.findByStudentIdAndDate(studentId, date)
                .orElseThrow(() -> new EntityNotFoundException("No attendance record found for student: " + studentId + " on date: " + date));

        attendance.setPresent(isPresent);
        attendance.setRemarks(remarks);

        StudentAttendance updatedAttendance = studentAttendanceRepository.save(attendance);
        return convertToResponseDTO(updatedAttendance);
    }

    @Override
    public void deleteAttendance(String studentId, LocalDate date) {
        StudentAttendance attendance = studentAttendanceRepository.findByStudentIdAndDate(studentId, date)
                .orElseThrow(() -> new EntityNotFoundException("No attendance record found for student: " + studentId + " on date: " + date));
        studentAttendanceRepository.delete(attendance);
    }

    private String getAttendanceStatus(double percentage) {
        if (percentage >= 95) return "EXCELLENT";
        if (percentage >= 85) return "GOOD";
        if (percentage >= 75) return "AVERAGE";
        return "POOR";
    }

    private StudentAttendanceResponseDTO convertToResponseDTO(StudentAttendance studentAttendance) {
        StudentAttendanceResponseDTO responseDTO = new StudentAttendanceResponseDTO();
        responseDTO.setStudentId(studentAttendance.getStudentId());
        responseDTO.setDate(studentAttendance.getDate());
        responseDTO.setPresent(studentAttendance.isPresent());
        responseDTO.setTeacherId(studentAttendance.getTeacherId());

        return responseDTO;
    }
}
