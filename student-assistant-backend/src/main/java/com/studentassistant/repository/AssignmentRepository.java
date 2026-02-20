package com.studentassistant.repository;

import com.studentassistant.entity.Assignment;
import com.studentassistant.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findBySubject(Subject subject);
    List<Assignment> findBySubjectIdAndSubject_DepartmentAndSubject_Semester(
        Long subjectId,
        String department,
        int semester);
}
