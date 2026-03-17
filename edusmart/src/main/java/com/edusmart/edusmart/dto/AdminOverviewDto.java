package com.edusmart.edusmart.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdminOverviewDto {
    private SystemMetrics systemMetrics;
    private List<EnrollmentTrend> enrollmentTrends;
    private List<Activity> recentActivity;

    @Data
    public static class SystemMetrics {
        private long totalStudents;
        private long totalInstructors;
        private long activeCourses;
        private String systemHealth;
    }

    @Data
    public static class EnrollmentTrend {
        private String month;
        private int students;
        private String height; // E.g., "66%" for the CSS chart
    }

    @Data
    public static class Activity {
        private String user;
        private String action;
        private String detail;
        private String time;
        private String type;
    }
}
