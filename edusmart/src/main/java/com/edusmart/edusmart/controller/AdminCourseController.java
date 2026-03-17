package com.edusmart.edusmart.controller;

import com.edusmart.edusmart.dto.AdminCourseDto;
import com.edusmart.edusmart.services.AdminCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/courses")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminCourseController {

    @Autowired
    private AdminCourseService adminCourseService;

    @GetMapping
    public ResponseEntity<List<AdminCourseDto>> getAllCourses() {
        return ResponseEntity.ok(adminCourseService.getAllCourses());
    }

    @PatchMapping("/{courseId}/status")
    public ResponseEntity<Void> updateCourseStatus(
            @PathVariable Long courseId,
            @RequestParam String status) {
        adminCourseService.updateCourseStatus(courseId, status, "System Admin");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        adminCourseService.deleteCourse(courseId, "System Admin");
        return ResponseEntity.ok().build();
    }
}