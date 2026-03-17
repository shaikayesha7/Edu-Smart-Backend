package com.edusmart.edusmart.services;

import com.edusmart.edusmart.dto.AssessmentDto;
import com.edusmart.edusmart.entities.Assessment;
import com.edusmart.edusmart.entities.Course;
import com.edusmart.edusmart.entities.Login;
import com.edusmart.edusmart.repositories.AssessmentRepository;
import com.edusmart.edusmart.repositories.CourseRepository;
import com.edusmart.edusmart.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssessmentServiceImpl implements AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<AssessmentDto> getAssessmentsByInstructor(Long instructorId) {
        // 🚀 FIXED: Calling the relational path method
        return assessmentRepository.findByCourseInstructorIdOrderByIdDesc(instructorId)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public AssessmentDto saveAssessment(Long instructorId, AssessmentDto dto) {
        // Verify Instructor exists
        userRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Assessment assessment = (dto.getId() != null)
                ? assessmentRepository.findById(dto.getId()).orElseThrow()
                : new Assessment();

        assessment.setTitle(dto.getTitle());
        assessment.setType(dto.getType());
        assessment.setMaxScore(dto.getMaxScore());
        assessment.setStatus(dto.getStatus());
        assessment.setCourse(course);

        // Note: If Assessment entity doesn't have a direct setInstructor,
        // it is derived from the Course.
        // assessment.setInstructor(instructor);

        Assessment saved = assessmentRepository.save(assessment);
        return mapToDto(saved);
    }

    @Override
    public void deleteAssessment(Long assessmentId) {
        assessmentRepository.deleteById(assessmentId);
    }

    private AssessmentDto mapToDto(Assessment assessment) {
        AssessmentDto dto = new AssessmentDto();
        dto.setId(assessment.getId());
        dto.setTitle(assessment.getTitle());
        dto.setCourseId(assessment.getCourse().getId());
        dto.setCourseCode(assessment.getCourse().getCourseCode());
        dto.setType(assessment.getType());
        dto.setMaxScore(assessment.getMaxScore());
        dto.setStatus(assessment.getStatus());
        return dto;
    }
}