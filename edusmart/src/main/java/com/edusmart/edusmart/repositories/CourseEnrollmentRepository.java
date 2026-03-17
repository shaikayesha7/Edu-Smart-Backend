package com.edusmart.edusmart.repositories;

import com.edusmart.edusmart.entities.CourseEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, Long> {

    // 🚀 ADD THIS LINE TO FIX THE ADMIN SERVICE ERROR
    long countByCourseId(Long courseId);

    @Query("SELECT COUNT(DISTINCT e.student.id) FROM CourseEnrollment e WHERE e.course.instructor.id = :instructorId")
    long countDistinctStudentsByInstructorId(Long instructorId);

    // Finds all enrollments for a specific student (for the Dashboard & My Learning)
    List<CourseEnrollment> findByStudentId(Long studentId);

    // Counts enrollments by status (for the Dashboard Metrics/KPIs)
    long countByStudentIdAndStatus(Long studentId, String status);

    // Existing instructor-side methods...
    List<CourseEnrollment> findByCourseInstructorIdOrderByIdDesc(Long instructorId);
}