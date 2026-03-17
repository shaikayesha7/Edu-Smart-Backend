package com.edusmart.edusmart.repositories;

import com.edusmart.edusmart.entities.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    // 🚀 THIS IS THE MISSING SYMBOL
    long countByCourseInstructorId(Long instructorId);
    List<Assessment> findByCourseIdIn(List<Long> courseIds);
    // Also ensures the "Assessments" tab works
    java.util.List<Assessment> findByCourseInstructorIdOrderByIdDesc(Long instructorId);

}