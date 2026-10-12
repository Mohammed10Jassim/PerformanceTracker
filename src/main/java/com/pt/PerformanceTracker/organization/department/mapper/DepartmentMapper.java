package com.pt.PerformanceTracker.organization.department.mapper;

import com.pt.PerformanceTracker.organization.department.dto.requestDto.CreateDepartmentRequestDto;
import com.pt.PerformanceTracker.organization.department.dto.requestDto.UpdateDepartmentRequestDto;
import com.pt.PerformanceTracker.organization.department.dto.responseDto.DepartmentResponseDto;
import com.pt.PerformanceTracker.organization.department.entity.DepartmentEntity;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
   componentModel = "spring"
)
public interface DepartmentMapper {
   DepartmentEntity toEntity(CreateDepartmentRequestDto request);

   DepartmentResponseDto toResponse(DepartmentEntity entity);

   List<DepartmentResponseDto> toResponseList(List<DepartmentEntity> entities);

   @BeanMapping(
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
   )
   void updateDepartmentFromRequest(UpdateDepartmentRequestDto request, @MappingTarget DepartmentEntity entity);
}
