package com.pt.PerformanceTracker.performance.kpa.mapper;

import com.pt.PerformanceTracker.performance.kpa.dto.ProjectKpaCreateRequest;
import com.pt.PerformanceTracker.performance.kpa.dto.ProjectKpaResponse;
import com.pt.PerformanceTracker.performance.kpa.dto.ProjectKpaUpdateRequest;
import com.pt.PerformanceTracker.performance.kpa.entity.ProjectKpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
   componentModel = "spring",
   nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProjectKpaMapper {
   @Mappings({@Mapping(
         target = "projectKraId",
         source = "projectKra.id"
      ), @Mapping(
         target = "projectKraName",
         source = "projectKra.kraName"
      )})
   ProjectKpaResponse toResponse(ProjectKpaEntity entity);

   @Mapping(
      target = "projectKra",
      ignore = true
   )
   ProjectKpaEntity toEntity(ProjectKpaCreateRequest request);

   @Mapping(
      target = "projectKra",
      ignore = true
   )
   void updateEntity(ProjectKpaUpdateRequest request, @MappingTarget ProjectKpaEntity entity);
}
