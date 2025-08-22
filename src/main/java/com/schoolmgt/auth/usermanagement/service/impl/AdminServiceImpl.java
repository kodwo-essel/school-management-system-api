package com.schoolmgt.auth.usermanagement.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.schoolmgt.auth.base.service.impl.BaseServiceImpl;
import com.schoolmgt.auth.usermanagement.dto.ParentDTO;
import com.schoolmgt.auth.usermanagement.dto.StudentRegistrationDTO;
import com.schoolmgt.auth.usermanagement.dto.StudentResponseDTO;
import com.schoolmgt.auth.usermanagement.dto.TeacherRegistrationDTO;
import com.schoolmgt.auth.usermanagement.dto.TeacherResponseDTO;
import com.schoolmgt.auth.usermanagement.entity.Admin;
import com.schoolmgt.auth.usermanagement.entity.Parent;
import com.schoolmgt.auth.usermanagement.entity.Role;
import com.schoolmgt.auth.usermanagement.entity.Student;
import com.schoolmgt.auth.usermanagement.entity.Teacher;
import com.schoolmgt.auth.usermanagement.repository.AdminRepository;
import com.schoolmgt.auth.usermanagement.repository.ParentRepository;
import com.schoolmgt.auth.usermanagement.repository.StudentRepository;
import com.schoolmgt.auth.usermanagement.repository.TeacherRepository;
import com.schoolmgt.auth.usermanagement.service.AdminService;
import com.schoolmgt.auth.utils.IdGenerators;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class AdminServiceImpl extends BaseServiceImpl<Admin, AdminRepository> implements AdminService{
    private final PasswordEncoder passwordEncoder;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;

    public AdminServiceImpl(AdminRepository repository, PasswordEncoder passwordEncoder, TeacherRepository teacherRepository, StudentRepository studentRepository, ParentRepository parentRepository) {
        super(repository);
        this.passwordEncoder = passwordEncoder;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.parentRepository = parentRepository;
    }


    @Override
    public TeacherResponseDTO registerTeacher(TeacherRegistrationDTO teacherRegistrationDTO) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(teacherRegistrationDTO.getFirstName());
        teacher.setLastName(teacherRegistrationDTO.getLastName());
        teacher.setOtherNames(teacherRegistrationDTO.getOtherNames());
        teacher.setEmail(teacherRegistrationDTO.getEmail());
        teacher.setPassword(passwordEncoder.encode(teacherRegistrationDTO.getPassword()));
        teacher.setRole(Role.TEACHER);
        teacher.setDateOfBirth(teacherRegistrationDTO.getDateOfBirth());
        teacher.setGender(teacherRegistrationDTO.getGender());
        teacher.setPhoneNumber(teacherRegistrationDTO.getPhoneNumber());
        teacher.setSchoolId(teacherRegistrationDTO.getSchoolId());
        teacher.setTeacherId(teacherRegistrationDTO.getTeacherId());
        teacher.setNationality(teacherRegistrationDTO.getNationality());

        Teacher savedTeacher = teacherRepository.save(teacher);

        if (savedTeacher.getTeacherId() == null){
            String teacherId = IdGenerators.generateTeacherId(savedTeacher.getId(), savedTeacher.getSchoolId());
            savedTeacher.setTeacherId(teacherId);
            savedTeacher = teacherRepository.save(savedTeacher);
        }

        return mapToTeacherResponseDTO(savedTeacher);
    }

    @Override
    public StudentResponseDTO registerStudent(StudentRegistrationDTO studentRegistrationDTO){
        ParentDTO parentDto = studentRegistrationDTO.getParent();
        Parent parent = new Parent();
        parent.setName(parentDto.getName());
        parent.setPhoneNumber(parentDto.getPhoneNumber());
        parent.setEmail(parentDto.getEmail());

        Parent savedParent = parentRepository.save(parent);

        Student student = new Student();
        student.setFirstName(studentRegistrationDTO.getFirstName());
        student.setLastName(studentRegistrationDTO.getLastName());
        student.setOtherNames(studentRegistrationDTO.getOtherNames());
        student.setEmail(studentRegistrationDTO.getEmail());
        student.setPassword(passwordEncoder.encode(studentRegistrationDTO.getPassword()));
        student.setRole(Role.STUDENT);
        student.setDateOfBirth(studentRegistrationDTO.getDateOfBirth());
        student.setGender(studentRegistrationDTO.getGender());
        student.setSchoolId(studentRegistrationDTO.getSchooldId());
        student.setStudentId(studentRegistrationDTO.getStudentId());
        student.setParentId(savedParent.getId());
        student.setNationality(studentRegistrationDTO.getNationality());

        Student savedStudent = studentRepository.save(student);

        if (savedStudent.getStudentId() == null){
            String studentId = IdGenerators.generateStudentId(savedStudent.getId(), savedStudent.getSchoolId());
            savedStudent.setStudentId(studentId);
            savedStudent = studentRepository.save(savedStudent);
        }

        
        return mapToStudentResponseDTO(savedStudent);
    }



    private TeacherResponseDTO mapToTeacherResponseDTO(Teacher teacher) {
        TeacherResponseDTO teacherResponseDTO = new TeacherResponseDTO();
        teacherResponseDTO.setFirstName(teacher.getFirstName());
        teacherResponseDTO.setLastName(teacher.getLastName());
        teacherResponseDTO.setOtherNames(teacher.getOtherNames());
        teacherResponseDTO.setEmail(teacher.getEmail());
        teacherResponseDTO.setDateOfBirth(teacher.getDateOfBirth());
        teacherResponseDTO.setGender(teacher.getGender());
        teacherResponseDTO.setPhoneNumber(teacher.getPhoneNumber());
        teacherResponseDTO.setSchoolId(teacher.getSchoolId());
        teacherResponseDTO.setTeacherId(teacher.getTeacherId());
        teacherResponseDTO.setNationality(teacher.getNationality());
        return teacherResponseDTO;
    }

    private StudentResponseDTO mapToStudentResponseDTO(Student student) {
        StudentResponseDTO studentResponseDTO = new StudentResponseDTO();
        studentResponseDTO.setFirstName(student.getFirstName());
        studentResponseDTO.setLastName(student.getLastName());
        studentResponseDTO.setOtherNames(student.getOtherNames());
        studentResponseDTO.setDateOfBirth(student.getDateOfBirth());
        studentResponseDTO.setNationality(student.getNationality());
        studentResponseDTO.setGender(student.getGender());
        studentResponseDTO.setSchoolId(student.getSchoolId());
        studentResponseDTO.setStudentId(student.getStudentId());
        studentResponseDTO.setNationality(student.getNationality());

        Parent parent = parentRepository.findById(student.getParentId())
        .orElseThrow(() -> new RuntimeException("Parent not found with id " + student.getParentId()));

        ParentDTO parentDto = new ParentDTO();
        parentDto.setName(parent.getName());
        parentDto.setPhoneNumber(parent.getPhoneNumber());
        parentDto.setEmail(parent.getEmail());

        studentResponseDTO.setParent(parentDto);

        return studentResponseDTO;
    }
}
