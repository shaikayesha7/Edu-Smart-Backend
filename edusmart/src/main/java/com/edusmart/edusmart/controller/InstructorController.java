package com.edusmart.edusmart.controller;

import com.edusmart.edusmart.dto.InstructorMetricsDto;
import com.edusmart.edusmart.services.InstructorDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/instructor")
public class InstructorController {

    @Autowired
    private InstructorDashboardService dashboardService;

    // Endpoint: GET http://localhost:9090/api/instructor/{instructorId}/metrics
    @GetMapping("/{instructorId}/metrics")
    public ResponseEntity<InstructorMetricsDto> getDashboardMetrics(@PathVariable Long instructorId) {
        InstructorMetricsDto metrics = dashboardService.getDashboardMetrics(instructorId);
        return ResponseEntity.ok(metrics);
    }
}