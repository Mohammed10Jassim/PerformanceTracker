package com.pt.PerformanceTracker.organization.employee.mapper;

import com.pt.PerformanceTracker.organization.departmentDesignation.entity.DepartmentDesignationEntity;
import com.pt.PerformanceTracker.organization.employee.dto.requestDto.CreateEmployeeRequestDto;
import com.pt.PerformanceTracker.organization.employee.dto.requestDto.UpdateEmployeeRequestDto;
import com.pt.PerformanceTracker.organization.employee.dto.responseDto.EmployeeDepartmentResponseDto;
import com.pt.PerformanceTracker.organization.employee.dto.responseDto.EmployeeDesignationResponseDto;
import com.pt.PerformanceTracker.organization.employee.dto.responseDto.EmployeeResponseDto;
import com.pt.PerformanceTracker.organization.employee.entity.EmployeeEntity;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(
   componentModel = "spring",
   unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EmployeeMapper {
   EmployeeEntity toEntity(CreateEmployeeRequestDto request);

   @Mappings({@Mapping(
         target = "id",
         ignore = true
      ), @Mapping(
         target = "employeeCode",
         ignore = true
      ), @Mapping(
         target = "email",
         ignore = true
      ), @Mapping(
         target = "departmentDesignations",
         ignore = true
      )})
   void updateEmployeeFromRequest(UpdateEmployeeRequestDto request, @MappingTarget EmployeeEntity entity);

   @Mappings({@Mapping(
         target = "department",
         expression = "java(mapDepartment(entity))"
      ), @Mapping(
         target = "designations",
         expression = "java(mapDesignations(entity.getDepartmentDesignations()))"
      )})
   EmployeeResponseDto toResponse(EmployeeEntity entity);

   default EmployeeDepartmentResponseDto mapDepartment(EmployeeEntity employee) {
      if (employee != null && employee.getDepartmentDesignations() != null && !employee.getDepartmentDesignations().isEmpty()) {
         DepartmentDesignationEntity mapping = employee.getDepartmentDesignations().iterator().next();
         return mapping.getDepartment() == null
            ? null
            : EmployeeDepartmentResponseDto.builder()
               .id(mapping.getDepartment().getId())
               .departmentCode(mapping.getDepartment().getDepartmentCode())
               .departmentName(mapping.getDepartment().getDepartmentName())
               .build();
      } else {
         return null;
      }
   }

   default Set<EmployeeDesignationResponseDto> mapDesignations(Set<DepartmentDesignationEntity> mappings) {
      return mappings != null && !mappings.isEmpty()
         ? mappings.stream()
            .filter(mapping -> mapping != null)
            .filter(mapping -> mapping.getDesignation() != null)
            .map(
               mapping -> EmployeeDesignationResponseDto.builder()
                     .id(mapping.getDesignation().getId())
                     .designationName(mapping.getDesignation().getDesignationName())
                     .build()
            )
            .collect(Collectors.toSet())
         : Set.of();
   }
}
