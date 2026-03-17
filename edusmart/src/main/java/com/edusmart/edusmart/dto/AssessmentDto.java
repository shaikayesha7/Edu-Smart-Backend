package com.edusmart.edusmart.dto;

import lombok.Data;

import java.util.List;

@Data
public class AssessmentDto {
    private Long id;
    private String title;

    // We need both the Database ID (for saving) and the Course Code (for UI display)
    private Long courseId;
    private String courseCode;
    private Integer studentScore;
    private String type;
    private Integer maxScore;
    private String status;
    private List<QuestionDto> questions;

    @Data
    public static class QuestionDto {
        private Long id;
        private String text;
        private List<String> options;
        private String correct; // Only sent if you want to grade on the frontend
    }
}
