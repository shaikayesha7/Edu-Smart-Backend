package com.edusmart.edusmart.dto;

import lombok.Data;

@Data
public class AdminCourseDto {
    private Long id;
    private String courseCode;
    private String title;
    private String instructorName;
    private String instructorEmail;

    // 🚀 UPDATED: Officially supporting the Maker-Checker workflow
    private String status; // PUBLISHED, DRAFT, PENDING, ARCHIVED

    private long enrolledStudents;
}