package com.edusmart.edusmart.services;

import com.edusmart.edusmart.dto.InstructorMetricsDto;

public interface InstructorDashboardService {
    InstructorMetricsDto getDashboardMetrics(Long instructorId);
}