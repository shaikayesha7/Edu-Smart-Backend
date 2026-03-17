package com.edusmart.edusmart.services;

import com.edusmart.edusmart.dto.OptionDto;
import com.edusmart.edusmart.dto.QuestionDto;
import com.edusmart.edusmart.dto.QuizPayloadDto;
import com.edusmart.edusmart.entities.Assessment;
import com.edusmart.edusmart.entities.QuizQuestion;
import com.edusmart.edusmart.repositories.AssessmentRepository;
import com.edusmart.edusmart.repositories.QuizQuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    @Override
    @Transactional
    public void saveQuizDetails(Long instructorId, QuizPayloadDto payload) {
        // 1. Find the Assessment created in the previous step
        Assessment assessment = assessmentRepository.findById(payload.getAssessmentId())
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        // 2. Update the shared fields
        assessment.setPassingScore(payload.getPassingScore());
        assessment.setType(payload.getType());

        // 3. Process based on Mode
        if ("ASSIGNMENT".equals(payload.getType())) {
            assessment.setAssignmentPrompt(payload.getAssignmentPrompt());
            assessment.setAllowFileUpload(payload.getAllowFileUpload());

            // Clean up any old questions if they switched from Quiz to Assignment
            quizQuestionRepository.deleteByAssessmentId(assessment.getId());
        }
        else if ("QUIZ".equals(payload.getType())) {
            // Clear out old questions so we can save the updated list
            quizQuestionRepository.deleteByAssessmentId(assessment.getId());

            // Map and save the new questions
            if (payload.getQuestions() != null) {
                for (QuestionDto qDto : payload.getQuestions()) {
                    QuizQuestion question = new QuizQuestion();
                    question.setAssessment(assessment);
                    question.setQuestionText(qDto.getQuestionText());
                    question.setPoints(qDto.getPoints());
                    question.setQuestionType(qDto.getQuestionType());
                    question.setCorrectOptionIndex(qDto.getCorrectOptionIndex());

                    // Map the list of Option objects down to a simple list of Strings
                    List<String> optionStrings = qDto.getOptions().stream()
                            .map(OptionDto::getOptionText)
                            .collect(Collectors.toList());
                    question.setOptions(optionStrings);

                    quizQuestionRepository.save(question);
                }
            }
        }

        assessmentRepository.save(assessment);
    }
}
