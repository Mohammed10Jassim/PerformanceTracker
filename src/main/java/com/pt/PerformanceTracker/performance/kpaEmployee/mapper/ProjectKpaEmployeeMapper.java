package com.pt.PerformanceTracker.performance.kpaEmployee.mapper;

import com.pt.PerformanceTracker.performance.kpaEmployee.dto.ProjectKpaEmployeeCreateRequest;
import com.pt.PerformanceTracker.performance.kpaEmployee.dto.ProjectKpaEmployeeResponse;
import com.pt.PerformanceTracker.performance.kpaEmployee.entity.ProjectKpaEmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
   componentModel = "spring"
)
public interface ProjectKpaEmployeeMapper {
   @Mappings({@Mapping(
         target = "projectKpa",
         ignore = true
      ), @Mapping(
         target = "employee",
         ignore = true
      )})
   ProjectKpaEmployeeEntity toEntity(ProjectKpaEmployeeCreateRequest request);

   @Mappings({@Mapping(
         target = "projectKpaId",
         source = "projectKpa.id"
      ), @Mapping(
         target = "employeeId",
         source = "employee.id"
      )})
   ProjectKpaEmployeeResponse toResponse(ProjectKpaEmployeeEntity entity);
}
