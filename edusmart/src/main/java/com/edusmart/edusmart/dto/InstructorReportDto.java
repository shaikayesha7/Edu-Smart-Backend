package com.edusmart.edusmart.dto;

import lombok.Data;

import java.util.List;

@Data
public class InstructorReportDto {
    private OverviewMetrics overviewMetrics;
    private List<CourseReport> courseReports;
    private List<StudentRisk> studentsAtRisk;

    @Data
    public static class OverviewMetrics {
        private long totalStudents;
        private int avgCompletionRate;
        private int overallAvgGrade;
        private long assessmentsGraded;
    }

    @Data
    public static class CourseReport {
        private String courseId;
        private String title;
        private long enrolled;
        private int avgScore;
        private int completionRate;
    }

    @Data
    public static class StudentRisk {
        private String name;
        private String course;
        private String issue;
        private String lastActive;
    }
}
