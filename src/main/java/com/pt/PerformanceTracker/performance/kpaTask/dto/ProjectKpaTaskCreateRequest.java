package com.pt.PerformanceTracker.performance.kpaTask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProjectKpaTaskCreateRequest(
   @NotNull Long kpaEmployeeAssignmentId, @NotBlank @Size(max = 200) String taskName, @Size(max = 1000) String description
) {
}
