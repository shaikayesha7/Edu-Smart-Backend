package com.edusmart.edusmart.services;

import com.edusmart.edusmart.dto.CourseDto;
import com.edusmart.edusmart.entities.Course;
import com.edusmart.edusmart.entities.Login;
import com.edusmart.edusmart.repositories.CourseRepository;
import com.edusmart.edusmart.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // 🚀 This is the most important line to fix your error!
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getCoursesByInstructor(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CourseDto saveCourse(CourseDto dto) {
        Course course;

        // 1. If updating, find the existing course; if new, create one
        if (dto.getId() != null) {
            course = courseRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));
        } else {
            course = new Course();
        }

        // 2. Map DTO to Entity
        course.setCourseCode(dto.getCourseCode());
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setCategory(dto.getCategory());
        course.setStatus(dto.getStatus());

        // 3. Link the Instructor (from your Login table)
        if (dto.getInstructorId() != null) {
            Login instructor = userRepository.findById(dto.getInstructorId())
                    .orElseThrow(() -> new RuntimeException("Instructor not found"));
            course.setInstructor(instructor);
        }

        Course savedCourse = courseRepository.save(course);
        return mapToDto(savedCourse);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    // 🚀 Internal helper to convert Entity to DTO
    private CourseDto mapToDto(Course course) {
        CourseDto dto = new CourseDto();
        dto.setId(course.getId());
        dto.setCourseCode(course.getCourseCode());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setCategory(course.getCategory());
        dto.setStatus(course.getStatus());

        if (course.getInstructor() != null) {
            dto.setInstructorId(course.getInstructor().getId());
            dto.setInstructorName(course.getInstructor().getName());
        }
        return dto;
    }
}