package com.edusmart.edusmart.services;

import com.edusmart.edusmart.dto.InstructorReportDto;

public interface ReportService {
    InstructorReportDto getInstructorReport(Long instructorId);
}
