package com.edusmart.edusmart.services;

import com.edusmart.edusmart.dto.AdminCourseDto;
import java.util.List;

public interface AdminCourseService {
    List<AdminCourseDto> getAllCourses();
    void updateCourseStatus(Long courseId, String newStatus, String adminUsername);
    void deleteCourse(Long courseId, String adminUsername);
}