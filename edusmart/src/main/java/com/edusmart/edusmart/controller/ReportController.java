package com.edusmart.edusmart.controller;

import com.edusmart.edusmart.dto.InstructorReportDto;
import com.edusmart.edusmart.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructor")
@CrossOrigin(origins = "http://localhost:4200")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/{instructorId}/reports")
    public ResponseEntity<InstructorReportDto> getReports(@PathVariable Long instructorId) {
        return ResponseEntity.ok(reportService.getInstructorReport(instructorId));
    }
}
