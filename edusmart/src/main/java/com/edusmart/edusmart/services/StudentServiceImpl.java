package com.edusmart.edusmart.services;

import com.edusmart.edusmart.dto.*;
import com.edusmart.edusmart.entities.*;
import com.edusmart.edusmart.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired private CourseEnrollmentRepository enrollmentRepository;
    @Autowired private CourseRepository courseRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AssessmentRepository assessmentRepository;
    @Autowired private SubmissionRepository submissionRepository;
    @Autowired private QuestionRepository questionRepository;

    // --- 1. QUIZ & ASSESSMENT LOGIC ---

    @Override
    public AssessmentDto getAssessmentById(Long id) {
        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));
        return mapToAssessmentDto(assessment);
    }

    @Override
    @Transactional
    public void saveSubmission(SubmissionDto dto) {
        Assessment assessment = assessmentRepository.findById(dto.getAssessmentId())
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        Login student = userRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Submission submission = new Submission();
        submission.setAssessment(assessment);
        submission.setStudent(student);
        submission.setScore(dto.getScore());

        submissionRepository.save(submission);
    }

    @Override
    public List<AssessmentDto> getStudentAssessments(Long studentId) {
        List<Long> courseIds = enrollmentRepository.findByStudentId(studentId)
                .stream().map(e -> e.getCourse().getId()).collect(Collectors.toList());

        if (courseIds.isEmpty()) return new ArrayList<>();

        return assessmentRepository.findByCourseIdIn(courseIds).stream().map(a -> {
            AssessmentDto dto = mapToAssessmentDto(a);
            // Check if this specific student has already submitted this quiz
            submissionRepository.findByAssessmentIdAndStudentId(a.getId(), studentId)
                    .ifPresentOrElse(s -> {
                        dto.setStudentScore(s.getScore());
                        dto.setStatus("COMPLETED");
                    }, () -> dto.setStatus("PENDING"));
            return dto;
        }).collect(Collectors.toList());
    }

    // --- 2. DASHBOARD & OVERVIEW LOGIC ---

    @Override
    public StudentOverviewDto getStudentOverview(Long studentId) {
        StudentOverviewDto overview = new StudentOverviewDto();
        Login student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        overview.setStudentName(student.getName());

        StudentOverviewDto.StudentMetrics metrics = new StudentOverviewDto.StudentMetrics();
        metrics.setCoursesInProgress(enrollmentRepository.countByStudentIdAndStatus(studentId, "ACTIVE"));
        metrics.setCompletedCourses(enrollmentRepository.countByStudentIdAndStatus(studentId, "COMPLETED"));
        metrics.setCurrentAverage(88); // Calculation logic can be added later
        metrics.setCertificatesEarned(enrollmentRepository.countByStudentIdAndStatus(studentId, "COMPLETED"));
        overview.setMetrics(metrics);

        List<CourseEnrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        overview.setEnrolledCourses(enrollments.stream().limit(3).map(e -> {
            StudentOverviewDto.EnrolledCourseDto d = new StudentOverviewDto.EnrolledCourseDto();
            d.setId(e.getCourse().getId());
            d.setTitle(e.getCourse().getTitle());
            d.setInstructor(e.getCourse().getInstructor() != null ? e.getCourse().getInstructor().getName() : "TBA");
            d.setProgress(e.getProgress() != null ? e.getProgress() : 0);
            d.setNextLesson("Continue Learning");
            d.setImage("📚");
            return d;
        }).collect(Collectors.toList()));

        overview.setUpcomingDeadlines(new ArrayList<>());
        return overview;
    }

    // --- 3. ENROLLMENT & INSTRUCTOR MANAGEMENT ---

    @Override
    public List<StudentEnrollmentDto> getEnrolledCourses(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentEnrollmentDto> getInstructorStudents(Long instructorId) {
        return enrollmentRepository.findByCourseInstructorIdOrderByIdDesc(instructorId)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudentEnrollmentDto saveStudent(Long instructorId, StudentEnrollmentDto dto) {
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Login student = userRepository.findByEmail(dto.getEmail()).orElseGet(() -> {
            Login newStudent = new Login();
            newStudent.setEmail(dto.getEmail());
            newStudent.setName(dto.getName());
            newStudent.setRole("STUDENT");
            newStudent.setStatus("ACTIVE");
            newStudent.setPassword(passwordEncoder.encode("student123"));
            return userRepository.save(newStudent);
        });

        CourseEnrollment enrollment = (dto.getEnrollmentId() != null) ?
                enrollmentRepository.findById(dto.getEnrollmentId()).orElseThrow() : new CourseEnrollment();

        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setProgress(dto.getProgress());
        enrollment.setGrade(dto.getGrade());
        enrollment.setStatus(dto.getStatus());
        enrollment.setAttendance(dto.getAttendance());

        return mapToDto(enrollmentRepository.save(enrollment));
    }

    @Override
    @Transactional
    public void saveBulkAttendance(List<StudentEnrollmentDto> dtos) {
        for (StudentEnrollmentDto dto : dtos) {
            enrollmentRepository.findById(dto.getEnrollmentId()).ifPresent(e -> {
                e.setAttendance(dto.getAttendance());
                enrollmentRepository.save(e);
            });
        }
    }

    @Override
    public void deleteStudent(Long enrollmentId) {
        enrollmentRepository.deleteById(enrollmentId);
    }

    // --- 4. PRIVATE MAPPING HELPERS ---

    private StudentEnrollmentDto mapToDto(CourseEnrollment e) {
        StudentEnrollmentDto dto = new StudentEnrollmentDto();
        dto.setEnrollmentId(e.getId());
        dto.setStudentId(e.getStudent().getId());
        dto.setName(e.getStudent().getName());
        dto.setEmail(e.getStudent().getEmail());
        dto.setCourseId(e.getCourse().getId());
        dto.setCourseCode(e.getCourse().getCourseCode());
        dto.setCourseTitle(e.getCourse().getTitle());
        dto.setInstructorName(e.getCourse().getInstructor() != null ? e.getCourse().getInstructor().getName() : "TBA");
        dto.setProgress(e.getProgress() != null ? e.getProgress() : 0);
        dto.setGrade(e.getGrade());
        dto.setStatus(e.getStatus());
        dto.setAttendance(e.getAttendance());
        return dto;
    }

    private AssessmentDto mapToAssessmentDto(Assessment a) {
        AssessmentDto dto = new AssessmentDto();
        dto.setId(a.getId());
        dto.setTitle(a.getTitle());
        dto.setType(a.getType());
        dto.setCourseCode(a.getCourse().getCourseCode());
        dto.setMaxScore(a.getMaxScore());

        // Fetch questions and options for the Quiz Player
        List<Question> questions = questionRepository.findByAssessmentId(a.getId());
        dto.setQuestions(questions.stream().map(q -> {
            AssessmentDto.QuestionDto qDto = new AssessmentDto.QuestionDto();
            qDto.setId(q.getId());
            qDto.setText(q.getText());
            qDto.setOptions(q.getOptions());
            qDto.setCorrect(q.getCorrectAnswer());
            return qDto;
        }).collect(Collectors.toList()));

        return dto;
    }
}