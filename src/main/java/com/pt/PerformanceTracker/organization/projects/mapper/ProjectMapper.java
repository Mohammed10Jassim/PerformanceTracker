package com.pt.PerformanceTracker.organization.projects.mapper;

import com.pt.PerformanceTracker.organization.projects.dto.ProjectCreateRequest;
import com.pt.PerformanceTracker.organization.projects.dto.ProjectResponse;
import com.pt.PerformanceTracker.organization.projects.dto.ProjectUpdateRequest;
import com.pt.PerformanceTracker.organization.projects.entity.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
   componentModel = "spring",
   nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProjectMapper {
   ProjectEntity toEntity(ProjectCreateRequest request);

   void updateEntity(ProjectUpdateRequest request, @MappingTarget ProjectEntity project);

   ProjectResponse toResponse(ProjectEntity project);
}
