package com.edusmart.edusmart.services;

import com.edusmart.edusmart.dto.CourseDto;
import java.util.List;

public interface CourseService {
    List<CourseDto> getAllCourses();
    List<CourseDto> getCoursesByInstructor(Long instructorId);
    CourseDto saveCourse(CourseDto dto);
    void deleteCourse(Long id);
}