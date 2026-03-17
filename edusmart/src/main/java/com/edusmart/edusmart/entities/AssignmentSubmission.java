package com.edusmart.edusmart.entities;



import jakarta.persistence.*;

import lombok.Data;

import org.apache.catalina.User;



@Entity

@Table(name = "assignment_submissions")

@Data

public class AssignmentSubmission {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;



    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "course_id", nullable = false)

    private Course course;



    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "student_id", nullable = false)

    private Login student;



    @Column(nullable = false)

    private String status = "PENDING"; // "PENDING" (needs grading) or "GRADED"

}