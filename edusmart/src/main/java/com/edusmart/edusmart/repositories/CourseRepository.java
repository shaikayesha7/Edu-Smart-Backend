package com.edusmart.edusmart.repositories;

import com.edusmart.edusmart.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // Counts active courses for this specific instructor
    long countByInstructorIdAndStatus(Long instructorId, String status);
    List<Course> findByInstructorIdOrderByIdDesc(Long instructorId);
    long countByStatus(String status);
    List<Course> findByInstructorId(Long instructorId);
}