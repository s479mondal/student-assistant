package com.studentassistant.controller;

import com.studentassistant.entity.*;
import com.studentassistant.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final AssignmentRepository assignmentRepository;
    private final MarksRepository marksRepository;
    private final AttendanceRepository attendanceRepository;
    private final AssignmentSubmissionRepository submissionRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    // -----------------------------
    // ROLE CHECK
    // -----------------------------
    private void checkTeacher(HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if (!"TEACHER".equals(role)) {
            throw new RuntimeException("Access denied. Teacher only.");
        }
    }

    private User getLoggedInTeacher(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
    }

    private void validateTeacherSubject(User teacher, Subject subject) {
        boolean exists = teacherSubjectRepository
                .existsByTeacherAndSubject(teacher, subject);

        if (!exists) {
            throw new RuntimeException("You are not assigned to this subject");
        }
    }

    // -----------------------------
    // CREATE ASSIGNMENT
    // -----------------------------
@GetMapping("/assignments")
public List<Assignment> getAssignmentsBySubject(
        HttpServletRequest request,
        @RequestParam Long subjectId) {

    checkTeacher(request);

    User teacher = getLoggedInTeacher(request);

    Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new RuntimeException("Subject not found"));

    validateTeacherSubject(teacher, subject);

    return assignmentRepository.findBySubject(subject);
}


    // -----------------------------
// UPDATE ASSIGNMENT
// -----------------------------
@PutMapping("/assignment/{assignmentId}")
public Assignment updateAssignment(
        HttpServletRequest request,
        @PathVariable Long assignmentId,
        @RequestBody Assignment updatedData) {

    checkTeacher(request);

    User teacher = getLoggedInTeacher(request);

    Assignment assignment = assignmentRepository.findById(assignmentId)
            .orElseThrow(() -> new RuntimeException("Assignment not found"));

    validateTeacherSubject(teacher, assignment.getSubject());

    // 🔐 Update only non-null fields
    if (updatedData.getTitle() != null) {
        assignment.setTitle(updatedData.getTitle());
    }

    if (updatedData.getDescription() != null) {
        assignment.setDescription(updatedData.getDescription());
    }

    if (updatedData.getDueDate() != null) {
        assignment.setDueDate(updatedData.getDueDate());
    }

    return assignmentRepository.save(assignment);
}
    // -----------------------------
    // ENTER MARKS
    // -----------------------------
    @PostMapping("/marks")
    public Marks addMarks(
            HttpServletRequest request,
            @RequestParam Long subjectId,
            @RequestBody Marks marks) {

        checkTeacher(request);

        User teacher = getLoggedInTeacher(request);

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        validateTeacherSubject(teacher, subject);

        marks.setSubject(subject);

        return marksRepository.save(marks);
    }

    // -----------------------------
    // MARK ATTENDANCE
    // -----------------------------
    @PostMapping("/attendance")
    public Attendance markAttendance(
            HttpServletRequest request,
            @RequestParam Long subjectId,
            @RequestBody Attendance attendance) {

        checkTeacher(request);

        User teacher = getLoggedInTeacher(request);

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        validateTeacherSubject(teacher, subject);

        attendance.setSubject(subject);

        return attendanceRepository.save(attendance);
    }

    // -----------------------------
    // VIEW SUBMISSIONS
    // -----------------------------
    @GetMapping("/submissions/{assignmentId}")
    public List<AssignmentSubmission> viewSubmissions(
            HttpServletRequest request,
            @PathVariable Long assignmentId) {

        checkTeacher(request);

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        return submissionRepository.findByAssignment(assignment);
    }
}
