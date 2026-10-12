package com.pt.PerformanceTracker.performance.kpaTask.dto;

import com.pt.PerformanceTracker.performance.kpaTask.entity.TaskStatus;
import jakarta.validation.constraints.Size;

public record ProjectKpaTaskUpdateRequest(@Size(max = 200) String taskName, @Size(max = 1000) String description, TaskStatus status) {
}
