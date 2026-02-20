package com.studentassistant.repository;

import com.studentassistant.entity.TeacherSubject;
import com.studentassistant.entity.User;
import com.studentassistant.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherSubjectRepository extends JpaRepository<TeacherSubject, Long> {

    List<TeacherSubject> findByTeacher(User teacher);

    List<TeacherSubject> findBySubject(Subject subject);

    boolean existsByTeacherAndSubject(User teacher, Subject subject);
}
