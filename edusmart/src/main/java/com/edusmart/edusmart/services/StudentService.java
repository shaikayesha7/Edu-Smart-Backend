package com.edusmart.edusmart.services;

import com.edusmart.edusmart.dto.AssessmentDto;
import com.edusmart.edusmart.dto.StudentEnrollmentDto;
import com.edusmart.edusmart.dto.StudentOverviewDto;
import com.edusmart.edusmart.dto.SubmissionDto;

import java.util.List;

public interface StudentService {
    // 🚀 Added for the Dashboard Overview
    StudentOverviewDto getStudentOverview(Long studentId);

    // 🚀 Added for the "My Learning" course list (Fixes the current error)
    List<StudentEnrollmentDto> getEnrolledCourses(Long studentId);
    List<AssessmentDto> getStudentAssessments(Long studentId);
    // Existing methods for Instructor management
    List<StudentEnrollmentDto> getInstructorStudents(Long instructorId);
    StudentEnrollmentDto saveStudent(Long instructorId, StudentEnrollmentDto dto);
    void saveBulkAttendance(List<StudentEnrollmentDto> dtos);
    AssessmentDto getAssessmentById(Long id);

    void saveSubmission(SubmissionDto dto);
    void deleteStudent(Long enrollmentId);
}