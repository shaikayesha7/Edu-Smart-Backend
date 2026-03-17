package com.edusmart.edusmart.repositories;

import com.edusmart.edusmart.entities.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
    void deleteByAssessmentId(Long assessmentId);
}