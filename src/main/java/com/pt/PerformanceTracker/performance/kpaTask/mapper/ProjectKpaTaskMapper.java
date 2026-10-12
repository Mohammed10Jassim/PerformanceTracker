package com.pt.PerformanceTracker.performance.kpaTask.mapper;

import com.pt.PerformanceTracker.performance.kpaTask.dto.ProjectKpaTaskCreateRequest;
import com.pt.PerformanceTracker.performance.kpaTask.dto.ProjectKpaTaskResponse;
import com.pt.PerformanceTracker.performance.kpaTask.dto.ProjectKpaTaskUpdateRequest;
import com.pt.PerformanceTracker.performance.kpaTask.entity.ProjectKpaTaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
   componentModel = "spring",
   nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProjectKpaTaskMapper {
   @Mapping(
      target = "kpaEmployeeAssignment",
      ignore = true
   )
   ProjectKpaTaskEntity toEntity(ProjectKpaTaskCreateRequest request);

   @Mapping(
      target = "kpaEmployeeAssignmentId",
      source = "kpaEmployeeAssignment.id"
   )
   ProjectKpaTaskResponse toResponse(ProjectKpaTaskEntity entity);

   @Mappings({@Mapping(
         target = "id",
         ignore = true
      ), @Mapping(
         target = "kpaEmployeeAssignment",
         ignore = true
      ), @Mapping(
         target = "active",
         ignore = true
      ), @Mapping(
         target = "createdAt",
         ignore = true
      ), @Mapping(
         target = "updatedAt",
         ignore = true
      ), @Mapping(
         target = "createdBy",
         ignore = true
      ), @Mapping(
         target = "updatedBy",
         ignore = true
      )})
   void updateEntity(ProjectKpaTaskUpdateRequest request, @MappingTarget ProjectKpaTaskEntity entity);
}
