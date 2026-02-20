package com.studentassistant.repository;

import com.studentassistant.entity.Attendance;
import com.studentassistant.entity.Student;
import com.studentassistant.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStudent(Student student);

    Optional<Attendance> findByStudentAndSubject(Student student, Subject subject);
}
