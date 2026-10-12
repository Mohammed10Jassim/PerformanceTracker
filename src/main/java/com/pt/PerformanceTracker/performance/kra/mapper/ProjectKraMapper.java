package com.pt.PerformanceTracker.performance.kra.mapper;

import com.pt.PerformanceTracker.performance.kra.dto.ProjectKraResponse;
import com.pt.PerformanceTracker.performance.kra.entity.ProjectKraEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
   componentModel = "spring"
)
public interface ProjectKraMapper {
   @Mappings({@Mapping(
         target = "projectDepartmentId",
         source = "projectDepartment.id"
      ), @Mapping(
         target = "projectId",
         source = "projectDepartment.project.id"
      ), @Mapping(
         target = "projectName",
         source = "projectDepartment.project.projectName"
      ), @Mapping(
         target = "departmentId",
         source = "projectDepartment.department.id"
      ), @Mapping(
         target = "departmentName",
         source = "projectDepartment.department.departmentName"
      )})
   ProjectKraResponse toResponse(ProjectKraEntity entity);
}
