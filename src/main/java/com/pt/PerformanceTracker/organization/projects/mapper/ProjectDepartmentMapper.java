package com.pt.PerformanceTracker.organization.projects.mapper;

import com.pt.PerformanceTracker.organization.projects.dto.ProjectDepartmentResponse;
import com.pt.PerformanceTracker.organization.projects.entity.ProjectDepartmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
   componentModel = "spring"
)
public interface ProjectDepartmentMapper {
   @Mappings({@Mapping(
         target = "projectId",
         source = "project.id"
      ), @Mapping(
         target = "projectName",
         source = "project.projectName"
      ), @Mapping(
         target = "startDate",
         source = "project.startDate"
      ), @Mapping(
         target = "endDate",
         source = "project.endDate"
      ), @Mapping(
         target = "projectStatus",
         source = "project.status"
      ), @Mapping(
         target = "departmentId",
         source = "department.id"
      ), @Mapping(
         target = "departmentName",
         source = "department.departmentName"
      )})
   ProjectDepartmentResponse toResponse(ProjectDepartmentEntity entity);
}
