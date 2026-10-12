package com.pt.PerformanceTracker.organization.departmentDesignation.mapper;

import com.pt.PerformanceTracker.organization.departmentDesignation.dto.requestDto.CreateDepartmentDesignationRequestDto;
import com.pt.PerformanceTracker.organization.departmentDesignation.dto.responseDto.DepartmentDesignationResponseDto;
import com.pt.PerformanceTracker.organization.departmentDesignation.entity.DepartmentDesignationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
   componentModel = "spring"
)
public interface DepartmentDesignationMapper {
   DepartmentDesignationEntity toEntity(CreateDepartmentDesignationRequestDto request);

   @Mappings({@Mapping(
         target = "departmentId",
         source = "department.id"
      ), @Mapping(
         target = "departmentName",
         source = "department.departmentName"
      ), @Mapping(
         target = "designationId",
         source = "designation.id"
      ), @Mapping(
         target = "designationName",
         source = "designation.designationName"
      )})
   DepartmentDesignationResponseDto toResponse(DepartmentDesignationEntity entity);
}
