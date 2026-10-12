package com.pt.PerformanceTracker.organization.projects.dto;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record ProjectUpdateRequest(String projectName, @Size(max = 1000) String description, LocalDate startDate, LocalDate endDate) {
}
