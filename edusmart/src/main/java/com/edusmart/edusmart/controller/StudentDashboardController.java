package com.edusmart.edusmart.controller;

import com.edusmart.edusmart.dto.AssessmentDto;
import com.edusmart.edusmart.dto.StudentEnrollmentDto;
import com.edusmart.edusmart.dto.StudentOverviewDto;
import com.edusmart.edusmart.dto.SubmissionDto;
import com.edusmart.edusmart.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentDashboardController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/{studentId}/overview")
    public ResponseEntity<StudentOverviewDto> getOverview(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getStudentOverview(studentId));
    }

    @GetMapping("/{studentId}/courses")
    public ResponseEntity<List<StudentEnrollmentDto>> getMyCourses(@PathVariable Long studentId) {
        // 🚀 This call will now work because the interface has the method!
        return ResponseEntity.ok(studentService.getEnrolledCourses(studentId));
    }
    @GetMapping("/{studentId}/assessments")
    public ResponseEntity<List<AssessmentDto>> getAssessments(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getStudentAssessments(studentId));
    }
    @GetMapping("/quiz/{id}")
    public ResponseEntity<AssessmentDto> getQuizDetails(@PathVariable Long id) {
        // You already have mapToDto in StudentServiceImpl, just use it here
        return ResponseEntity.ok(studentService.getAssessmentById(id));
    }
    @PostMapping("/submit")
    public ResponseEntity<String> submitQuiz(@RequestBody SubmissionDto dto) {
        // In a real app, you'd get the studentId from the Auth token
        studentService.saveSubmission(dto);
        return ResponseEntity.ok("Quiz submitted successfully!");
    }
}