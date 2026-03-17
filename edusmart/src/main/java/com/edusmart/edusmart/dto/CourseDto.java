package com.edusmart.edusmart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // 🚀 This automatically generates getCourseCode(), getTitle(), etc.
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private Long id;
    private String courseCode;
    private String title;
    private String description;
    private String category;
    private String status;
    private Long instructorId;
    private String instructorName;
}