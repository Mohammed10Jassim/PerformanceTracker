package com.pt.PerformanceTracker.performance.kpaTask.dto;

import com.pt.PerformanceTracker.performance.kpaTask.entity.TaskStatus;

public record ProjectKpaTaskResponse(Long id, Long kpaEmployeeAssignmentId, String taskName, String description, TaskStatus status, Boolean active) {
}
