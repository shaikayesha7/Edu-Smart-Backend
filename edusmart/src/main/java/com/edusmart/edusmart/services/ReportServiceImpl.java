package com.edusmart.edusmart.services;

import com.edusmart.edusmart.dto.InstructorReportDto;
import com.edusmart.edusmart.entities.Course;
import com.edusmart.edusmart.entities.CourseEnrollment;
import com.edusmart.edusmart.repositories.AssessmentRepository;
import com.edusmart.edusmart.repositories.AssignmentSubmissionRepository;
import com.edusmart.edusmart.repositories.CourseEnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private CourseEnrollmentRepository enrollmentRepository;

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Override
    public InstructorReportDto getInstructorReport(Long instructorId) {
        InstructorReportDto report = new InstructorReportDto();

        // Fetch every student enrollment for this instructor
        List<CourseEnrollment> allEnrollments = enrollmentRepository.findByCourseInstructorIdOrderByIdDesc(instructorId);

        // --- 1. OVERVIEW METRICS ---
        InstructorReportDto.OverviewMetrics overview = new InstructorReportDto.OverviewMetrics();

        // Calculate Total Students
        long totalUniqueStudents = allEnrollments.stream().map(e -> e.getStudent().getId()).distinct().count();
        overview.setTotalStudents(totalUniqueStudents);

        // Calculate Average Completion Rate based on the Progress slider in the Students Tab
        int avgCompletion = allEnrollments.isEmpty() ? 0 :
                (int) allEnrollments.stream().mapToInt(CourseEnrollment::getProgress).average().orElse(0);
        overview.setAvgCompletionRate(avgCompletion);

        // Mock average grade based on progress (since grades are text like "A-")
        overview.setOverallAvgGrade(avgCompletion > 0 ? avgCompletion + 5 : 0);

        // Count total assessments created
        overview.setAssessmentsGraded(assessmentRepository.countByCourseInstructorId(instructorId));

        report.setOverviewMetrics(overview);

        // --- 2. COURSE REPORTS (Grouped by Course) ---
        // Groups all students by the class they are taking
        Map<Course, List<CourseEnrollment>> groupedByCourse = allEnrollments.stream()
                .collect(Collectors.groupingBy(CourseEnrollment::getCourse));

        List<InstructorReportDto.CourseReport> courseReports = new ArrayList<>();
        for (Map.Entry<Course, List<CourseEnrollment>> entry : groupedByCourse.entrySet()) {
            Course course = entry.getKey();
            List<CourseEnrollment> courseEnrollments = entry.getValue();

            InstructorReportDto.CourseReport cr = new InstructorReportDto.CourseReport();
            cr.setCourseId(course.getCourseCode());
            cr.setTitle(course.getTitle());
            cr.setEnrolled(courseEnrollments.size());

            // Calculate the specific completion rate for THIS course
            int courseCompletion = (int) courseEnrollments.stream().mapToInt(CourseEnrollment::getProgress).average().orElse(0);
            cr.setCompletionRate(courseCompletion);
            cr.setAvgScore(courseCompletion > 0 ? courseCompletion + 5 : 0);

            courseReports.add(cr);
        }
        report.setCourseReports(courseReports);

        // --- 3. STUDENTS AT RISK ---
        // Automatically finds anyone you marked as "AT RISK" in the Students Tab!
        List<InstructorReportDto.StudentRisk> atRiskList = allEnrollments.stream()
                .filter(e -> "AT RISK".equalsIgnoreCase(e.getStatus()))
                .map(e -> {
                    InstructorReportDto.StudentRisk risk = new InstructorReportDto.StudentRisk();
                    risk.setName(e.getStudent().getName());
                    risk.setCourse(e.getCourse().getCourseCode());

                    // Generate a smart issue description based on their progress
                    if (e.getProgress() < 30) {
                        risk.setIssue("Critical: Only " + e.getProgress() + "% complete");
                    } else if (e.getAttendance() != null && e.getAttendance().equals("ABSENT")) {
                        risk.setIssue("Missed recent class");
                    } else {
                        risk.setIssue("Manually flagged as At Risk");
                    }

                    risk.setLastActive("Recently");
                    return risk;
                })
                .collect(Collectors.toList());

        report.setStudentsAtRisk(atRiskList);

        return report;
    }
}