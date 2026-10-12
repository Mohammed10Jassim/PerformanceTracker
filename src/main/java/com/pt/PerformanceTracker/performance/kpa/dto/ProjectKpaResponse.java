package com.pt.PerformanceTracker.performance.kpa.dto;

public record ProjectKpaResponse(Long id, Long projectKraId, String projectKraName, String kpaName, String description, Boolean active) {
}
