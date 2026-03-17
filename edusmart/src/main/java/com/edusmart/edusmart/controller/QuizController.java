package com.edusmart.edusmart.controller;

import com.edusmart.edusmart.dto.QuizPayloadDto;
import com.edusmart.edusmart.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructor")
@CrossOrigin(origins = "http://localhost:4200")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/{instructorId}/quiz-builder")
    public ResponseEntity<Void> saveQuizDetails(
            @PathVariable Long instructorId,
            @RequestBody QuizPayloadDto payload) {

        quizService.saveQuizDetails(instructorId, payload);
        return ResponseEntity.ok().build();
    }
}