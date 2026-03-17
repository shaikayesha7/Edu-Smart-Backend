package com.edusmart.edusmart.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "assessments")
@Data
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String type; // QUIZ, EXAM, Assignment

    @Column(nullable = false)
    private Integer maxScore;

    @Column(nullable = false)
    private String status; // PUBLISHED, DRAFT

    // Link to the specific course
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // Link to the instructor who created it
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    private Login instructor;
    private Integer passingScore;

    @Column(columnDefinition = "TEXT")
    private String assignmentPrompt;

    private Boolean allowFileUpload = false;
}
