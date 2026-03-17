package com.edusmart.edusmart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorMetricsDto {
    private long activeCourses;
    private long totalStudents;
    private long pendingGrades;
}
