package com.edusmart.edusmart.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuizPayloadDto {
    private Long assessmentId;
    private Integer passingScore;
    private String type; // 'QUIZ' or 'ASSIGNMENT'

    // For Assignments
    private String assignmentPrompt;
    private Boolean allowFileUpload;

    // For Quizzes
    private List<QuestionDto> questions;
}
