package com.pt.PerformanceTracker.performance.kra.dto;

import jakarta.validation.constraints.Size;

public record ProjectKraUpdateRequest(@Size(max = 150) String kraName, @Size(max = 500) String description) {
}
