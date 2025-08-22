package com.schoolmgt.auth.attendance.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.schoolmgt.auth.attendance.dto.TeacherAttendanceRequestDTO;
import com.schoolmgt.auth.attendance.dto.TeacherAttendanceResponseDTO;
import com.schoolmgt.auth.attendance.dto.TeacherAttendanceSummaryDTO;
import com.schoolmgt.auth.attendance.entity.TeacherAttendance;
import com.schoolmgt.auth.attendance.repository.TeacherAttendanceRepository;
import com.schoolmgt.auth.attendance.service.TeacherAttendanceService;
import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TeacherAttendanceServiceImpl extends BaseServiceImpl<TeacherAttendance, TeacherAttendanceRepository> implements TeacherAttendanceService {

    private final TeacherAttendanceRepository teacherAttendanceRepository;

    public TeacherAttendanceServiceImpl(TeacherAttendanceRepository repository, TeacherAttendanceRepository teacherAttendanceRepository) {
        super(repository);
        this.teacherAttendanceRepository = teacherAttendanceRepository;
    }

    @Override
    public TeacherAttendanceResponseDTO recordAttendance(TeacherAttendanceRequestDTO teacherAttendanceRequestDTO) {
        TeacherAttendance teacherAttendance = new TeacherAttendance();
        teacherAttendance.setDate(teacherAttendanceRequestDTO.getDate());
        teacherAttendance.setPresent(teacherAttendanceRequestDTO.isPresent());
        teacherAttendance.setTeacherId(teacherAttendanceRequestDTO.getTeacherId());
        teacherAttendanceRepository.save(teacherAttendance);

        return convertToResponseDTO(teacherAttendance);
    }
    

    @Override
    public TeacherAttendanceResponseDTO getTeacherAttendanceByDate(String teacherId, LocalDate date) {
        TeacherAttendance attendance = teacherAttendanceRepository.findByTeacherIdAndDate(teacherId, date)
                .orElseThrow(() -> new EntityNotFoundException("No attendance record found for teacher: " + teacherId + " on date: " + date));
        return convertToResponseDTO(attendance);
    }

    @Override
    public List<TeacherAttendanceResponseDTO> getTeacherAttendanceHistory(String teacherId) {
        return teacherAttendanceRepository.findByTeacherId(teacherId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherAttendanceResponseDTO> getTeacherAttendanceByDateRange(String teacherId, LocalDate startDate, LocalDate endDate) {
        return teacherAttendanceRepository.findByTeacherIdAndDateBetween(teacherId, startDate, endDate).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherAttendanceResponseDTO> getAllTeacherAttendanceByDate(LocalDate date) {
        return teacherAttendanceRepository.findByDate(date).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherAttendanceResponseDTO> getAllTeacherAttendanceByDateRange(LocalDate startDate, LocalDate endDate) {
        return teacherAttendanceRepository.findByDateBetween(startDate, endDate).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherAttendanceSummaryDTO getTeacherAttendanceSummary(String teacherId, LocalDate startDate, LocalDate endDate) {
        long presentDays = teacherAttendanceRepository.countPresentDaysByTeacherAndDateRange(teacherId, startDate, endDate);
        long absentDays = teacherAttendanceRepository.countAbsentDaysByTeacherAndDateRange(teacherId, startDate, endDate);
        long totalDays = presentDays + absentDays;

        double attendancePercentage = totalDays > 0 ? (double) presentDays / totalDays * 100 : 0;
        String status = getAttendanceStatus(attendancePercentage);

        return TeacherAttendanceSummaryDTO.builder()
                .teacherId(teacherId)
                .teacherName("Teacher Name") // Would need teacher service integration
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
    public List<TeacherAttendanceSummaryDTO> getAllTeacherAttendanceSummary(LocalDate startDate, LocalDate endDate) {
        // This would require getting all teachers from teacher service
        throw new UnsupportedOperationException("All teacher attendance summary requires teacher service integration");
    }

    @Override
    public List<TeacherAttendanceResponseDTO> getAbsentTeachers(LocalDate date) {
        return teacherAttendanceRepository.findByDate(date).stream()
                .filter(attendance -> !attendance.isPresent())
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public double calculateTeacherAttendancePercentage(String teacherId, LocalDate startDate, LocalDate endDate) {
        long presentDays = teacherAttendanceRepository.countPresentDaysByTeacherAndDateRange(teacherId, startDate, endDate);
        long absentDays = teacherAttendanceRepository.countAbsentDaysByTeacherAndDateRange(teacherId, startDate, endDate);
        long totalDays = presentDays + absentDays;

        return totalDays > 0 ? (double) presentDays / totalDays * 100 : 0;
    }

    @Override
    public TeacherAttendanceResponseDTO updateTeacherAttendance(String teacherId, LocalDate date, boolean isPresent, String remarks) {
        TeacherAttendance attendance = teacherAttendanceRepository.findByTeacherIdAndDate(teacherId, date)
                .orElseThrow(() -> new EntityNotFoundException("No attendance record found for teacher: " + teacherId + " on date: " + date));

        attendance.setPresent(isPresent);
        attendance.setRemarks(remarks);

        TeacherAttendance updatedAttendance = teacherAttendanceRepository.save(attendance);
        return convertToResponseDTO(updatedAttendance);
    }

    @Override
    public void deleteTeacherAttendance(String teacherId, LocalDate date) {
        TeacherAttendance attendance = teacherAttendanceRepository.findByTeacherIdAndDate(teacherId, date)
                .orElseThrow(() -> new EntityNotFoundException("No attendance record found for teacher: " + teacherId + " on date: " + date));
        teacherAttendanceRepository.delete(attendance);
    }

    private String getAttendanceStatus(double percentage) {
        if (percentage >= 95) return "EXCELLENT";
        if (percentage >= 85) return "GOOD";
        if (percentage >= 75) return "AVERAGE";
        return "POOR";
    }

    private TeacherAttendanceResponseDTO convertToResponseDTO(TeacherAttendance teacherAttendance) {
        TeacherAttendanceResponseDTO responseDTO = new TeacherAttendanceResponseDTO();
        responseDTO.setDate(teacherAttendance.getDate());
        responseDTO.setPresent(teacherAttendance.isPresent());
        responseDTO.setTeacherId(teacherAttendance.getTeacherId());

        return responseDTO;
    }
}
