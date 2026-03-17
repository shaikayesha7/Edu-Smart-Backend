package com.edusmart.edusmart.controller;

import com.edusmart.edusmart.dto.StudentEnrollmentDto;
import com.edusmart.edusmart.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructor")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/{instructorId}/students")
    public ResponseEntity<List<StudentEnrollmentDto>> getStudents(@PathVariable Long instructorId) {
        return ResponseEntity.ok(studentService.getInstructorStudents(instructorId));
    }

    @PostMapping("/{instructorId}/students")
    public ResponseEntity<StudentEnrollmentDto> addOrUpdateStudent(
            @PathVariable Long instructorId, @RequestBody StudentEnrollmentDto dto) {
        return ResponseEntity.ok(studentService.saveStudent(instructorId, dto));
    }

    @PostMapping("/{instructorId}/students/attendance")
    public ResponseEntity<Void> saveAttendance(
            @PathVariable Long instructorId, @RequestBody List<StudentEnrollmentDto> dtos) {
        studentService.saveBulkAttendance(dtos);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/students/{enrollmentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long enrollmentId) {
        studentService.deleteStudent(enrollmentId);
        return ResponseEntity.ok().build();
    }
}
