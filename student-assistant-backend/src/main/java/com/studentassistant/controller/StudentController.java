package com.studentassistant.controller;

import com.studentassistant.entity.*;
import com.studentassistant.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentSubmissionRepository submissionRepository;
    private final MarksRepository marksRepository;
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    private void checkStudent(HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if (!"STUDENT".equals(role)) {
            throw new RuntimeException("Access denied. Student only.");
        }
    }

    private Student getLoggedInStudent(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return studentRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student profile not found"));
    }

    // -----------------------------
    // VIEW ASSIGNMENTS
    // -----------------------------
    @GetMapping("/assignments")
    public List<Assignment> viewAssignments(HttpServletRequest request) {
        checkStudent(request);
        return assignmentRepository.findAll();
    }
    @GetMapping("/assignments/by-subject")
public List<Assignment> getAssignmentsBySubject(
        HttpServletRequest request,
        @RequestParam Long subjectId) {

    checkStudent(request);

    Student student = getLoggedInStudent(request);

    return assignmentRepository.findBySubjectIdAndSubject_DepartmentAndSubject_Semester(
            subjectId,
            student.getDepartment(),
            student.getSemester()
    );
}

    // -----------------------------
    // SUBMIT ASSIGNMENT
    // -----------------------------
    @PostMapping("/submit")
    public AssignmentSubmission submitAssignment(
            HttpServletRequest request,
            @RequestBody AssignmentSubmission submission) {

        checkStudent(request);

        Student student = getLoggedInStudent(request);
        submission.setStudent(student);

        return submissionRepository.save(submission);
    }

    // -----------------------------
    // VIEW MARKS (Own Only)
    // -----------------------------
    @GetMapping("/marks")
    public List<Marks> viewMarks(HttpServletRequest request) {

        checkStudent(request);
        Student student = getLoggedInStudent(request);

        return marksRepository.findByStudent(student);
    }

    // -----------------------------
    // VIEW ATTENDANCE (Own Only)
    // -----------------------------
    @GetMapping("/attendance")
    public List<Attendance> viewAttendance(HttpServletRequest request) {

        checkStudent(request);
        Student student = getLoggedInStudent(request);

        return attendanceRepository.findByStudent(student);
    }
}
