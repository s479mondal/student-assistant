package com.studentassistant.repository;

import com.studentassistant.entity.Marks;
import com.studentassistant.entity.Student;
import com.studentassistant.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarksRepository extends JpaRepository<Marks, Long> {

    List<Marks> findByStudent(Student student);

    Optional<Marks> findByStudentAndSubject(Student student, Subject subject);
}
