package com.edusmart.edusmart.services;


import com.edusmart.edusmart.dto.StudentOverviewDto;
import com.edusmart.edusmart.dto.StudentEnrollmentDto;
import java.util.List;

public interface StudentDashboardService {
    // For Sarah's Overview Dashboard
    StudentOverviewDto getStudentOverview(Long studentId);

    // For the "My Learning" page
    List<StudentEnrollmentDto> getEnrolledCourses(Long studentId);

    // For finding and joining new courses
    void enrollInCourse(Long studentId, Long courseId);
}