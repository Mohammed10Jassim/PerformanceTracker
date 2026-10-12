package com.pt.PerformanceTracker.organization.projects.dto;

import jakarta.validation.constraints.NotNull;

public record ProjectDepartmentCreateRequest(@NotNull Long projectId, @NotNull Long departmentId) {
}
