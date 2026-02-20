package com.studentassistant.repository;

import com.studentassistant.entity.Student;
import com.studentassistant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByUser(User user);

    List<Student> findByDepartment(String department);

    List<Student> findBySemester(int semester);
}
