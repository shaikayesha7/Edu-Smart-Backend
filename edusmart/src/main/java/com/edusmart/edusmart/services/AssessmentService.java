package com.edusmart.edusmart.services;

import com.edusmart.edusmart.dto.AssessmentDto;

import java.util.List;

public interface
AssessmentService {
    List<AssessmentDto> getAssessmentsByInstructor(Long instructorId);
    AssessmentDto saveAssessment(Long instructorId, AssessmentDto dto);
    void deleteAssessment(Long assessmentId);
}
