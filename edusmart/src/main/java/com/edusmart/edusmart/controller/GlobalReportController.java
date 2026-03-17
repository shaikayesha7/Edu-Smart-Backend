package com.edusmart.edusmart.controller;

import com.edusmart.edusmart.dto.GlobalReportDto;
import com.edusmart.edusmart.services.GlobalReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/reports")
@CrossOrigin(origins = "http://localhost:4200")
public class GlobalReportController {

    @Autowired
    private GlobalReportService globalReportService;

    @GetMapping
    public ResponseEntity<GlobalReportDto> getGlobalReports() {
        return ResponseEntity.ok(globalReportService.getGlobalPlatformReports());
    }
}
