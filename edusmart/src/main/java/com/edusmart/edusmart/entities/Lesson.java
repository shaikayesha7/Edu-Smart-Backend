package com.edusmart.edusmart.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "lessons")
@Data
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String lessonTitle;

    // 🚀 FIX: Tell MySQL to use a TEXT column (holds up to 65,000+ characters)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String videoLink;

    // 🚀 FIX: Also expand the description in case an instructor writes a long paragraph
    @Column(columnDefinition = "TEXT")
    private String lessonDescription;
    // Link back to the course
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
}
