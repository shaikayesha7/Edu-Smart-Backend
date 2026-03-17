package com.edusmart.edusmart.controller;

import com.edusmart.edusmart.dto.CourseDto;
import com.edusmart.edusmart.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructor")
@CrossOrigin(origins = "http://localhost:4200")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/{instructorId}/courses")
    public ResponseEntity<List<CourseDto>> getCourses(@PathVariable Long instructorId) {
        return ResponseEntity.ok(courseService.getCoursesByInstructor(instructorId));
    }

    @PostMapping("/{instructorId}/courses")
    public ResponseEntity<CourseDto> createOrUpdateCourse(
            @PathVariable Long instructorId,
            @RequestBody CourseDto courseDto) {

        // 🚀 Fix: Set the instructorId into the DTO before passing it to the service
        courseDto.setInstructorId(instructorId);

        // 🚀 Now calling with only ONE argument as required by the interface
        CourseDto savedCourse = courseService.saveCourse(courseDto);
        return ResponseEntity.ok(savedCourse);
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok().build();
    }
}