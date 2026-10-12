package com.pt.PerformanceTracker.organization.projects.repository;

import com.pt.PerformanceTracker.organization.projects.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long>, JpaSpecificationExecutor<ProjectEntity> {
   boolean existsByProjectNameIgnoreCase(String projectName);
}
