package com.edusmart.edusmart.dto;

import lombok.Data;
import java.util.List;

@Data
public class GlobalReportDto {
    private PlatformKpis kpis;
    private List<TopCourse> topCourses;
    private List<Demographic> userDemographics;

    @Data
    public static class PlatformKpis {
        private long totalEnrollments;
        private int globalCompletionRate;
        private long totalAssessments;
        private long activeStudents;
    }

    @Data
    public static class TopCourse {
        private String courseCode;
        private String title;
        private String instructor;
        private long studentCount;
    }

    @Data
    public static class Demographic {
        private String role; // e.g., "STUDENT", "INSTRUCTOR"
        private long count;
        private String percentage; // e.g., "75%"
    }
}