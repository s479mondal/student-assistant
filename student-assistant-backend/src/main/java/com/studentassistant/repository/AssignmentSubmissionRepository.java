package com.studentassistant.repository;

import com.studentassistant.entity.AssignmentSubmission;
import com.studentassistant.entity.Assignment;
import com.studentassistant.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, Long> {

    List<AssignmentSubmission> findByAssignment(Assignment assignment);

    List<AssignmentSubmission> findByStudent(Student student);

    Optional<AssignmentSubmission> findByAssignmentAndStudent(
            Assignment assignment, Student student);
}
