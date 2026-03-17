package com.edusmart.edusmart.services;

import com.edusmart.edusmart.dto.AdminOverviewDto;
import com.edusmart.edusmart.entities.ActivityLog;
import com.edusmart.edusmart.repositories.ActivityLogRepository;
import com.edusmart.edusmart.repositories.CourseRepository;
import com.edusmart.edusmart.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Override
    public AdminOverviewDto getDashboardOverview() {
        AdminOverviewDto overview = new AdminOverviewDto();

        // 1. Calculate System Metrics
        AdminOverviewDto.SystemMetrics metrics = new AdminOverviewDto.SystemMetrics();
        metrics.setTotalStudents(userRepository.countByRole("STUDENT"));
        metrics.setTotalInstructors(userRepository.countByRole("INSTRUCTOR"));
        metrics.setActiveCourses(courseRepository.countByStatus("PUBLISHED"));
        metrics.setSystemHealth("99.9%"); // Hardcoded for display purposes
        overview.setSystemMetrics(metrics);
        metrics.setTotalStudents(userRepository.countByRole("STUDENT"));
        metrics.setActiveCourses(courseRepository.countByStatus("PUBLISHED"));
        // 2. Generate Enrollment Trends (Dynamically scales the CSS height!)
        overview.setEnrollmentTrends(generateTrendData());

        // 3. Fetch Recent Activity
        List<AdminOverviewDto.Activity> activities = new ArrayList<>();
        List<ActivityLog> logs = activityLogRepository.findTop10ByOrderByTimestampDesc();

        for (ActivityLog log : logs) {
            AdminOverviewDto.Activity act = new AdminOverviewDto.Activity();
            act.setUser(log.getUsername());
            act.setAction(log.getAction());
            act.setDetail(log.getDetail());
            act.setType(log.getType());
            act.setTime(calculateTimeAgo(log.getTimestamp()));
            activities.add(act);
        }

        // Add a fallback if logs are empty (so your UI isn't blank on first boot)
        if (activities.isEmpty()) {
            AdminOverviewDto.Activity systemAct = new AdminOverviewDto.Activity();
            systemAct.setUser("System");
            systemAct.setAction("EduSmart Platform Initialized");
            systemAct.setType("SYSTEM");
            systemAct.setTime("Just now");
            activities.add(systemAct);
        }

        overview.setRecentActivity(activities);

        return overview;
    }

    // --- Helper Methods ---

    private List<AdminOverviewDto.EnrollmentTrend> generateTrendData() {
        // In a real production app, this queries historical timestamps.
        // We generate realistic math here so your CSS chart looks beautiful.
        List<AdminOverviewDto.EnrollmentTrend> trends = new ArrayList<>();
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun"};
        int[] counts = {1200, 1850, 1500, 2300, 2800, (int) userRepository.countByRole("STUDENT")};

        // Find the max value to calculate the CSS percentages
        int max = 1;
        for (int c : counts) if (c > max) max = c;

        for (int i = 0; i < months.length; i++) {
            AdminOverviewDto.EnrollmentTrend trend = new AdminOverviewDto.EnrollmentTrend();
            trend.setMonth(months[i]);
            trend.setStudents(counts[i]);

            // Calculate percentage for CSS height
            int heightPct = (int) (((double) counts[i] / max) * 100);
            trend.setHeight(heightPct + "%");
            trends.add(trend);
        }
        return trends;
    }

    private String calculateTimeAgo(LocalDateTime past) {
        long minutes = ChronoUnit.MINUTES.between(past, LocalDateTime.now());
        if (minutes < 60) return minutes + " mins ago";
        long hours = ChronoUnit.HOURS.between(past, LocalDateTime.now());
        if (hours < 24) return hours + " hours ago";
        return ChronoUnit.DAYS.between(past, LocalDateTime.now()) + " days ago";
    }
}
