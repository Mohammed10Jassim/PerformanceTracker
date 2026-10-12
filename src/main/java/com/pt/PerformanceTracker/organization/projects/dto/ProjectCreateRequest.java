package com.pt.PerformanceTracker.organization.projects.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record ProjectCreateRequest(@NotBlank @Size(max = 150) String projectName, @Size(max = 1000) String description, LocalDate startDate, LocalDate endDate) {
}
