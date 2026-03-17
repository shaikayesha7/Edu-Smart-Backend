package com.edusmart.edusmart.services;



import com.edusmart.edusmart.dto.*;
import com.edusmart.edusmart.entities.*;
import com.edusmart.edusmart.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentdashboardImpl implements StudentDashboardService {

    @Autowired private CourseEnrollmentRepository enrollmentRepo;
    @Autowired private UserRepository userRepository;
    @Autowired private CourseRepository courseRepo;

    @Override
    public StudentOverviewDto getStudentOverview(Long studentId) {
        StudentOverviewDto overview = new StudentOverviewDto();
        Login student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        overview.setStudentName(student.getName());

        // 1. Calculate Metrics
        StudentOverviewDto.StudentMetrics metrics = new StudentOverviewDto.StudentMetrics();
        metrics.setCoursesInProgress(enrollmentRepo.countByStudentIdAndStatus(studentId, "ACTIVE"));
        metrics.setCompletedCourses(enrollmentRepo.countByStudentIdAndStatus(studentId, "COMPLETED"));
        metrics.setCurrentAverage(88); // Logic can be expanded to average scores from submissions
        metrics.setCertificatesEarned(enrollmentRepo.countByStudentIdAndStatus(studentId, "COMPLETED"));
        overview.setMetrics(metrics);

        // 2. Fetch Active Courses for the Dashboard
        List<CourseEnrollment> activeEnrollments = enrollmentRepo.findByStudentId(studentId);
        overview.setEnrolledCourses(activeEnrollments.stream()
                .limit(3) // Only show top 3 on dashboard
                .map(this::mapToOverviewCourseDto)
                .collect(Collectors.toList()));

        return overview;
    }

    @Override
    public List<StudentEnrollmentDto> getEnrolledCourses(Long studentId) {
        return enrollmentRepo.findByStudentId(studentId).stream()
                .map(this::mapToEnrollmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public void enrollInCourse(Long studentId, Long courseId) {
        CourseEnrollment enrollment = new CourseEnrollment();
        enrollment.setStudent(userRepository.getReferenceById(studentId));
        enrollment.setCourse(courseRepo.getReferenceById(courseId));
        enrollment.setStatus("ACTIVE");
        enrollment.setProgress(0);
        enrollmentRepo.save(enrollment);
    }

    // Helper: Map to the Dashboard-specific DTO (with Emoji/Instructor)
    private StudentOverviewDto.EnrolledCourseDto mapToOverviewCourseDto(CourseEnrollment e) {
        StudentOverviewDto.EnrolledCourseDto dto = new StudentOverviewDto.EnrolledCourseDto();
        dto.setId(e.getCourse().getId());
        dto.setTitle(e.getCourse().getTitle());
        dto.setInstructor(e.getCourse().getInstructor().getName());
        dto.setProgress(e.getProgress());
        dto.setNextLesson("Continue Learning");
        dto.setImage("📘");
        return dto;
    }

    // Helper: Map to the general Enrollment DTO
    private StudentEnrollmentDto mapToEnrollmentDto(CourseEnrollment e) {
        StudentEnrollmentDto dto = new StudentEnrollmentDto();
        dto.setEnrollmentId(e.getId());
        dto.setCourseId(e.getCourse().getId());
        dto.setCourseCode(e.getCourse().getCourseCode());
        dto.setCourseTitle(e.getCourse().getTitle());
        dto.setProgress(e.getProgress());
        dto.setStatus(e.getStatus());
        return dto;
    }
}