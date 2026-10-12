package com.pt.PerformanceTracker.performance.kpaEmployee.dto;

import jakarta.validation.constraints.NotNull;

public record ProjectKpaEmployeeCreateRequest(@NotNull Long projectKpaId, @NotNull Long employeeId) {
}
