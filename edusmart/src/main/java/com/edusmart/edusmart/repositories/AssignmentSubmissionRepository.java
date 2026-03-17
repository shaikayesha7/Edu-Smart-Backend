package com.edusmart.edusmart.repositories;

import com.edusmart.edusmart.entities.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, Long> {
    // Counts ungraded assignments for any course owned by this instructor
    long countByCourseInstructorIdAndStatus(Long instructorId, String status);
}