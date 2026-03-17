package com.edusmart.edusmart.repositories;

import com.edusmart.edusmart.entities.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    // 🚀 This is the method your StudentServiceImpl calls!
    Optional<Submission> findByAssessmentIdAndStudentId(Long assessmentId, Long studentId);
}