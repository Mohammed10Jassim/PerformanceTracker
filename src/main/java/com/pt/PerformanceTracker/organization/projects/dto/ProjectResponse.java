package com.pt.PerformanceTracker.organization.projects.dto;

import com.pt.PerformanceTracker.organization.projects.enums.ProjectStatus;
import java.time.LocalDate;

public record ProjectResponse(Long id, String projectName, String description, LocalDate startDate, LocalDate endDate, ProjectStatus status, Boolean active) {
}
