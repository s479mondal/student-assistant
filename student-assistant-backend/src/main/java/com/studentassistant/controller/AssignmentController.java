package com.studentassistant.controller;

import com.studentassistant.entity.*;
import com.studentassistant.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentRepository assignmentRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final UserRepository userRepository;

    // -----------------------------
    // Helper: Get Logged User
    // -----------------------------
    private User getLoggedInUser(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // -----------------------------
    // Helper: Role Check
    // -----------------------------
    private void checkRole(HttpServletRequest request, String requiredRole) {
        String role = (String) request.getAttribute("role");
        if (!requiredRole.equals(role)) {
            throw new RuntimeException("Access denied");
        }
    }

    // -----------------------------
    // CREATE ASSIGNMENT (Teacher Only)
    // -----------------------------
    @PostMapping
    public Assignment createAssignment(
            HttpServletRequest request,
            @RequestParam Long subjectId,
            @RequestBody Assignment assignment) {

        checkRole(request, "TEACHER");

        User teacher = getLoggedInUser(request);

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        boolean allowed = teacherSubjectRepository
                .existsByTeacherAndSubject(teacher, subject);

        if (!allowed) {
            throw new RuntimeException("You are not assigned to this subject");
        }

        assignment.setSubject(subject);

        return assignmentRepository.save(assignment);
    }

    // -----------------------------
    // UPDATE ASSIGNMENT (Teacher Only)
    // -----------------------------
    @PutMapping("/{id}")
    public Assignment updateAssignment(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody Assignment updatedAssignment) {

        checkRole(request, "TEACHER");

        Assignment existing = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        existing.setTitle(updatedAssignment.getTitle());
        existing.setDescription(updatedAssignment.getDescription());
        existing.setDueDate(updatedAssignment.getDueDate());

        return assignmentRepository.save(existing);
    }

    // -----------------------------
    // DELETE ASSIGNMENT (Teacher Only)
    // -----------------------------
    @DeleteMapping("/{id}")
    public String deleteAssignment(
            HttpServletRequest request,
            @PathVariable Long id) {

        checkRole(request, "TEACHER");

        assignmentRepository.deleteById(id);

        return "Assignment deleted successfully";
    }

    // -----------------------------
    // VIEW ASSIGNMENTS BY SUBJECT
    // -----------------------------
    @GetMapping("/subject/{subjectId}")
    public List<Assignment> viewBySubject(
            HttpServletRequest request,
            @PathVariable Long subjectId) {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        return assignmentRepository.findBySubject(subject);
    }
}
