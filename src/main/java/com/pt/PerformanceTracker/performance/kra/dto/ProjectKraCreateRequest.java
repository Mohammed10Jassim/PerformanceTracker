package com.pt.PerformanceTracker.performance.kra.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProjectKraCreateRequest(@NotNull Long projectDepartmentId, @NotBlank @Size(max = 150) String kraName, @Size(max = 500) String description) {
}
