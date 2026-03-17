package com.edusmart.edusmart.services;

import com.edusmart.edusmart.dto.QuizPayloadDto;

public interface QuizService {
    void saveQuizDetails(Long instructorId, QuizPayloadDto payload);
}
