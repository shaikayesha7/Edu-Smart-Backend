package com.edusmart.edusmart.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "activity_logs")
@Data
public class ActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; // E.g., "Dr. Alan Smith" or "System"
    private String action;   // E.g., "Published a new course:"
    private String detail;   // E.g., "Data Structures & Algorithms"

    private String type;     // COURSE, SYSTEM, USER, ADMIN

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp = LocalDateTime.now();
}
