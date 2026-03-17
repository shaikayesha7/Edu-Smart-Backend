package com.edusmart.edusmart.services;

import com.edusmart.edusmart.dto.GlobalReportDto;
import com.edusmart.edusmart.entities.Course;
import com.edusmart.edusmart.entities.CourseEnrollment;
import com.edusmart.edusmart.repositories.AssessmentRepository;
import com.edusmart.edusmart.repositories.CourseEnrollmentRepository;
import com.edusmart.edusmart.repositories.CourseRepository;
import com.edusmart.edusmart.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GlobalReportServiceImpl implements GlobalReportService {

    @Autowired
    private CourseEnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Override
    public GlobalReportDto getGlobalPlatformReports() {
        GlobalReportDto report = new GlobalReportDto();
        List<CourseEnrollment> allEnrollments = enrollmentRepository.findAll();

        // 1. Calculate Platform KPIs
        GlobalReportDto.PlatformKpis kpis = new GlobalReportDto.PlatformKpis();
        kpis.setTotalEnrollments(allEnrollments.size());

        // Calculate global average progress across all students and all courses
        int globalCompletion = allEnrollments.isEmpty() ? 0 :
                (int) allEnrollments.stream().mapToInt(CourseEnrollment::getProgress).average().orElse(0);
        kpis.setGlobalCompletionRate(globalCompletion);

        kpis.setTotalAssessments(assessmentRepository.count());
        kpis.setActiveStudents(userRepository.countByRole("STUDENT"));
        report.setKpis(kpis);

        // 2. Calculate Top Performing Courses (Leaderboard)
        // Group enrollments by course, count them, and sort descending
        Map<Course, Long> enrollmentsByCourse = allEnrollments.stream()
                .collect(Collectors.groupingBy(CourseEnrollment::getCourse, Collectors.counting()));

        List<GlobalReportDto.TopCourse> topCourses = enrollmentsByCourse.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue())) // Sort Highest First
                .limit(5) // Get Top 5
                .map(entry -> {
                    GlobalReportDto.TopCourse tc = new GlobalReportDto.TopCourse();
                    tc.setCourseCode(entry.getKey().getCourseCode());
                    tc.setTitle(entry.getKey().getTitle());
                    tc.setInstructor(entry.getKey().getInstructor().getName());
                    tc.setStudentCount(entry.getValue());
                    return tc;
                })
                .collect(Collectors.toList());
        report.setTopCourses(topCourses);

        // 3. Calculate User Demographics
        long totalUsers = userRepository.count();
        List<GlobalReportDto.Demographic> demographics = new ArrayList<>();

        String[] roles = {"STUDENT", "INSTRUCTOR", "ADMIN"};
        for (String role : roles) {
            long count = userRepository.countByRole(role);
            GlobalReportDto.Demographic demo = new GlobalReportDto.Demographic();
            demo.setRole(role);
            demo.setCount(count);

            int percentage = totalUsers > 0 ? (int) (((double) count / totalUsers) * 100) : 0;
            demo.setPercentage(percentage + "%");
            demographics.add(demo);
        }
        report.setUserDemographics(demographics);

        return report;
    }}