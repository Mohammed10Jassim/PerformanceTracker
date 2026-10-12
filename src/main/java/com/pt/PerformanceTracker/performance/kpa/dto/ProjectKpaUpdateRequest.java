package com.pt.PerformanceTracker.performance.kpa.dto;

import jakarta.validation.constraints.Size;

public record ProjectKpaUpdateRequest(
   @Size(max = 150,message = "KPA name cannot exceed 150 characters") String kpaName,
   @Size(max = 500,message = "Description cannot exceed 500 characters") String description
) {
}
