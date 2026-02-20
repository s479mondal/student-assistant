package com.studentassistant.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "marks",
       uniqueConstraints = @UniqueConstraint(columnNames = {"student_id","subject_id"}))
public class Marks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(nullable = false)
    private int marks;
}
