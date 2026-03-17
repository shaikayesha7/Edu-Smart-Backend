package com.edusmart.edusmart.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "course_enrollments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Links to the Student (Login table)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Login student;

    // Links to the Course
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // Tracking progress and status
    private Integer progress = 0; // 0 to 100 percentage
    private String grade;
    private String attendance; // PRESENT, ABSENT, etc.

    @Column(nullable = false)
    private String status = "ACTIVE"; // ACTIVE, COMPLETED, DROPPED

    private LocalDateTime enrolledAt = LocalDateTime.now();
    private LocalDateTime lastAccessed = LocalDateTime.now();
}