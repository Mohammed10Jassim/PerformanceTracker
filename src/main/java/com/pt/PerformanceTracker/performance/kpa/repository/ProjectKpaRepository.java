package com.pt.PerformanceTracker.performance.kpa.repository;

import com.pt.PerformanceTracker.performance.kpa.entity.ProjectKpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectKpaRepository extends JpaRepository<ProjectKpaEntity, Long>, JpaSpecificationExecutor<ProjectKpaEntity> {
   boolean existsByProjectKraIdAndKpaNameIgnoreCase(Long projectKraId, String kpaName);
}
