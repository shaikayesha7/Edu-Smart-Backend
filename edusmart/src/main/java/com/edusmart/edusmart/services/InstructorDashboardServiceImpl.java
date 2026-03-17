package com.edusmart.edusmart.services;

import com.edusmart.edusmart.dto.InstructorMetricsDto;
import com.edusmart.edusmart.repositories.AssessmentRepository;
import com.edusmart.edusmart.repositories.AssignmentSubmissionRepository;
import com.edusmart.edusmart.repositories.CourseEnrollmentRepository;
import com.edusmart.edusmart.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class InstructorDashboardServiceImpl implements InstructorDashboardService {

    @Autowired
    private CourseRepository courseRepository; // Lowercase 'c'

    @Autowired
    private CourseEnrollmentRepository enrollmentRepository; // Lowercase 'e'

    @Autowired
    private AssessmentRepository assessmentRepository; // Lowercase 'a'
    @Override
    public InstructorMetricsDto getDashboardMetrics(Long instructorId) {

        long activeCourses = courseRepository.countByInstructorIdAndStatus(instructorId, "PUBLISHED");

        long totalStudents = enrollmentRepository.countDistinctStudentsByInstructorId(instructorId);

        // 🚀 CHANGE THIS LINE: From countByInstructorId to countByCourseInstructorId
        long totalAssessments = assessmentRepository.countByCourseInstructorId(instructorId);

        return new InstructorMetricsDto(activeCourses, totalStudents, totalAssessments);
    }}