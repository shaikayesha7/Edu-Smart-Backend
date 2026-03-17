package com.edusmart.edusmart.dto;

import lombok.Data;

@Data
public class SubmissionDto {
    private Long assessmentId;
    private Long studentId;
    private Integer score;
}