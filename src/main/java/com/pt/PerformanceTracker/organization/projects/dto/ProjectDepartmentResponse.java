package com.pt.PerformanceTracker.organization.projects.dto;

import com.pt.PerformanceTracker.organization.projects.enums.ProjectStatus;
import java.time.LocalDate;

public record ProjectDepartmentResponse(
   Long id,
   Long projectId,
   String projectName,
   LocalDate startDate,
   LocalDate endDate,
   ProjectStatus projectStatus,
   Long departmentId,
   String departmentName,
   Boolean active
) {
}
