package com.pt.PerformanceTracker.performance.kra.dto;

public record ProjectKraResponse(
   Long id,
   Long projectDepartmentId,
   Long projectId,
   String projectName,
   Long departmentId,
   String departmentName,
   String kraName,
   String description,
   Boolean active
) {
}
