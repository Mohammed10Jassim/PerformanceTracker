package com.pt.PerformanceTracker.organization.designation.mapper;

import com.pt.PerformanceTracker.organization.designation.dto.requestDto.CreateDesignationRequestDto;
import com.pt.PerformanceTracker.organization.designation.dto.requestDto.UpdateDesignationRequestDto;
import com.pt.PerformanceTracker.organization.designation.dto.responseDto.DesignationResponseDto;
import com.pt.PerformanceTracker.organization.designation.entity.DesignationEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-12T16:26:04+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class DesignationMapperImpl implements DesignationMapper {

    @Override
    public DesignationEntity toEntity(CreateDesignationRequestDto request) {
        if ( request == null ) {
            return null;
        }

        DesignationEntity.DesignationEntityBuilder designationEntity = DesignationEntity.builder();

        designationEntity.designationName( request.getDesignationName() );
        designationEntity.description( request.getDescription() );

        return designationEntity.build();
    }

    @Override
    public DesignationResponseDto toResponse(DesignationEntity entity) {
        if ( entity == null ) {
            return null;
        }

        DesignationResponseDto designationResponseDto = new DesignationResponseDto();

        designationResponseDto.setId( entity.getId() );
        designationResponseDto.setDesignationName( entity.getDesignationName() );
        designationResponseDto.setDescription( entity.getDescription() );
        designationResponseDto.setActive( entity.getActive() );

        return designationResponseDto;
    }

    @Override
    public void updateDesignationFromRequest(UpdateDesignationRequestDto request, DesignationEntity entity) {
        if ( request == null ) {
            return;
        }

        if ( request.getDesignationName() != null ) {
            entity.setDesignationName( request.getDesignationName() );
        }
        if ( request.getDescription() != null ) {
            entity.setDescription( request.getDescription() );
        }
    }
}
