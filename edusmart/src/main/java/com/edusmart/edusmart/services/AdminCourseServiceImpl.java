package com.edusmart.edusmart.services;

import com.edusmart.edusmart.dto.AdminCourseDto;
import com.edusmart.edusmart.entities.ActivityLog;
import com.edusmart.edusmart.entities.Course;
import com.edusmart.edusmart.repositories.ActivityLogRepository;
import com.edusmart.edusmart.repositories.CourseEnrollmentRepository;
import com.edusmart.edusmart.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 🚀 IMPORT THIS

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminCourseServiceImpl implements AdminCourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseEnrollmentRepository enrollmentRepository;

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Override
    @Transactional // 🚀 CRITICAL FIX: Keeps DB connection open to fetch Instructor & Student count
    public List<AdminCourseDto> getAllCourses() {
        return courseRepository.findAll().stream().map(course -> {
            AdminCourseDto dto = new AdminCourseDto();
            dto.setId(course.getId());
            dto.setCourseCode(course.getCourseCode());
            dto.setTitle(course.getTitle());

            // These two lines will crash without @Transactional!
            dto.setInstructorName(course.getInstructor().getName());
            dto.setInstructorEmail(course.getInstructor().getEmail());

            dto.setStatus(course.getStatus());

            // Dynamically count how many students are in this specific course
            dto.setEnrolledStudents(enrollmentRepository.countByCourseId(course.getId()));

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateCourseStatus(Long courseId, String newStatus, String adminUsername) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setStatus(newStatus);
        courseRepository.save(course);

        logActivity(adminUsername, "Changed course status to " + newStatus + ":", course.getTitle(), "COURSE");
    }

    @Override
    @Transactional
    public void deleteCourse(Long courseId, String adminUsername) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        courseRepository.deleteById(courseId);

        logActivity(adminUsername, "Deleted course from platform:", course.getTitle(), "COURSE");
    }

    // Helper method for the activity feed
    private void logActivity(String username, String action, String detail, String type) {
        ActivityLog log = new ActivityLog();
        log.setUsername(username != null ? username : "System Admin");
        log.setAction(action);
        log.setDetail(detail);
        log.setType(type);
        activityLogRepository.save(log);
    }
}