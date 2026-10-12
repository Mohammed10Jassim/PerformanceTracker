package com.pt.PerformanceTracker.performance.kpaEmployee.dto;

import java.math.BigDecimal;

public record ProjectKpaEmployeeResponse(Long id, Long projectKpaId, Long employeeId, BigDecimal completionPercentage, Boolean active) {
}
