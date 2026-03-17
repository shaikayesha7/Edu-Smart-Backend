package com.edusmart.edusmart.controller;

import com.edusmart.edusmart.dto.AssessmentDto;
import com.edusmart.edusmart.services.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructor")
@CrossOrigin(origins = "http://localhost:4200")
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    @GetMapping("/{instructorId}/assessments")
    public ResponseEntity<List<AssessmentDto>> getAssessments(@PathVariable Long instructorId) {
        return ResponseEntity.ok(assessmentService.getAssessmentsByInstructor(instructorId));
    }

    @PostMapping("/{instructorId}/assessments")
    public ResponseEntity<AssessmentDto> createOrUpdateAssessment(
            @PathVariable Long instructorId, @RequestBody AssessmentDto dto) {
        return ResponseEntity.ok(assessmentService.saveAssessment(instructorId, dto));
    }

    @DeleteMapping("/assessments/{assessmentId}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable Long assessmentId) {
        assessmentService.deleteAssessment(assessmentId);
        return ResponseEntity.ok().build();
    }
}
