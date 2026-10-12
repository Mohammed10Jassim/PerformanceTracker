package com.pt.PerformanceTracker.organization.designation.mapper;

import com.pt.PerformanceTracker.organization.designation.dto.requestDto.CreateDesignationRequestDto;
import com.pt.PerformanceTracker.organization.designation.dto.requestDto.UpdateDesignationRequestDto;
import com.pt.PerformanceTracker.organization.designation.dto.responseDto.DesignationResponseDto;
import com.pt.PerformanceTracker.organization.designation.entity.DesignationEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
   componentModel = "spring"
)
public interface DesignationMapper {
   DesignationEntity toEntity(CreateDesignationRequestDto request);

   DesignationResponseDto toResponse(DesignationEntity entity);

   @BeanMapping(
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
   )
   void updateDesignationFromRequest(UpdateDesignationRequestDto request, @MappingTarget DesignationEntity entity);
}
