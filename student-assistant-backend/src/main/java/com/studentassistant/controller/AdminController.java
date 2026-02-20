package com.studentassistant.controller;

import com.studentassistant.dto.CreateStudentRequest;
import com.studentassistant.dto.SignupRequest;
import com.studentassistant.entity.*;
import com.studentassistant.repository.*;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final StudentRepository studentRepository;



    // -----------------------------
    // ROLE CHECK
    // -----------------------------
    private void checkAdmin(HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Access denied. Admin only.");
        }
    }

    // -----------------------------
    // CREATE TEACHER
    // -----------------------------
   private final BCryptPasswordEncoder passwordEncoder =
        new BCryptPasswordEncoder();
        @PostMapping("/create-teacher")
public String createTeacher(
        HttpServletRequest request,
        @RequestBody SignupRequest dto) {

    checkAdmin(request);

    User teacher = new User();
    teacher.setUsername(dto.getUsername());
    teacher.setPassword(passwordEncoder.encode(dto.getPassword())); // ✅ FIX
    teacher.setEmail(dto.getEmail());
    teacher.setRole("TEACHER");

    userRepository.save(teacher);

    return "Teacher created successfully";
}

    // -----------------------------
    // CREATE SUBJECT
    // -----------------------------
    @PostMapping("/create-subject")
    public Subject createSubject(
            HttpServletRequest request,
            @RequestBody Subject subject) {

        checkAdmin(request);
        return subjectRepository.save(subject);
    }

    // -----------------------------
    // ASSIGN TEACHER TO SUBJECT
    // -----------------------------
    @PostMapping("/assign")
    public String assignTeacher(
            HttpServletRequest request,
            @RequestParam Long teacherId,
            @RequestParam Long subjectId) {

        checkAdmin(request);

        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        TeacherSubject mapping = new TeacherSubject();
        mapping.setTeacher(teacher);
        mapping.setSubject(subject);

        teacherSubjectRepository.save(mapping);

        return "Teacher assigned to subject";
    }

    // -----------------------------
    // VIEW ALL TEACHERS
    // -----------------------------
    @GetMapping("/teachers")
    public List<User> getTeachers(HttpServletRequest request) {
        checkAdmin(request);
        return userRepository.findByRole("TEACHER");
    }

    @PostMapping("/create-student")
public Student createStudent(
        HttpServletRequest request,
        @RequestBody CreateStudentRequest dto) {

    checkAdmin(request);

    User user = userRepository.findById(dto.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (!"STUDENT".equals(user.getRole())) {
        throw new RuntimeException("User is not a STUDENT");
    }

    Student student = new Student();
    student.setUser(user);
    student.setDepartment(dto.getDepartment());
    student.setSemester(dto.getSemester());

    return studentRepository.save(student);
}
    
@GetMapping("/students")
public List<Student> getStudents(
        @RequestParam(required = false) String department) {

    if (department == null) {
        return studentRepository.findAll();
    }

    return studentRepository.findByDepartment(department);
}
}
