package com.edusmart.edusmart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentEnrollmentDto {
    private Long enrollmentId;
    private Long studentId;
    private String name;        // Student Name
    private String email;       // Student Email
    private Long courseId;
    private String courseCode;
    private String courseTitle; // 🚀 ADDED THIS TO FIX THE ERROR
    private Integer progress;
    private String grade;
    private String instructorName;
    private String status;
    private String attendance;
}