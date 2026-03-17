package com.edusmart.edusmart.dto;

import lombok.Data;
import java.util.List;

@Data
public class StudentOverviewDto {
    private String studentName;
    private StudentMetrics metrics;
    private List<EnrolledCourseDto> enrolledCourses;
    private List<UpcomingDeadlineDto> upcomingDeadlines;

    @Data
    public static class StudentMetrics {
        private long coursesInProgress;
        private long completedCourses;
        private int currentAverage;
        private long certificatesEarned;
    }

    @Data
    public static class EnrolledCourseDto {
        private Long id;
        private String title;
        private String instructor;
        private int progress;
        private String nextLesson;
        private String image; // Emoji or URL
    }

    @Data
    public static class UpcomingDeadlineDto {
        private String title;
        private String course;
        private String dueDate;
        private String type; // QUIZ, ASSIGNMENT
        private boolean isUrgent;
    }
}