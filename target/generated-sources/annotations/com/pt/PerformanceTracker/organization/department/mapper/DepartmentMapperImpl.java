package com.pt.PerformanceTracker.organization.department.mapper;

import com.pt.PerformanceTracker.organization.department.dto.requestDto.CreateDepartmentRequestDto;
import com.pt.PerformanceTracker.organization.department.dto.requestDto.UpdateDepartmentRequestDto;
import com.pt.PerformanceTracker.organization.department.dto.responseDto.DepartmentResponseDto;
import com.pt.PerformanceTracker.organization.department.entity.DepartmentEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-12T16:26:04+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class DepartmentMapperImpl implements DepartmentMapper {

    @Override
    public DepartmentEntity toEntity(CreateDepartmentRequestDto request) {
        if ( request == null ) {
            return null;
        }

        DepartmentEntity.DepartmentEntityBuilder departmentEntity = DepartmentEntity.builder();

        departmentEntity.departmentName( request.getDepartmentName() );
        departmentEntity.description( request.getDescription() );

        return departmentEntity.build();
    }

    @Override
    public DepartmentResponseDto toResponse(DepartmentEntity entity) {
        if ( entity == null ) {
            return null;
        }

        DepartmentResponseDto.DepartmentResponseDtoBuilder departmentResponseDto = DepartmentResponseDto.builder();

        departmentResponseDto.id( entity.getId() );
        departmentResponseDto.departmentCode( entity.getDepartmentCode() );
        departmentResponseDto.departmentName( entity.getDepartmentName() );
        departmentResponseDto.description( entity.getDescription() );
        departmentResponseDto.active( entity.getActive() );

        return departmentResponseDto.build();
    }

    @Override
    public List<DepartmentResponseDto> toResponseList(List<DepartmentEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<DepartmentResponseDto> list = new ArrayList<DepartmentResponseDto>( entities.size() );
        for ( DepartmentEntity departmentEntity : entities ) {
            list.add( toResponse( departmentEntity ) );
        }

        return list;
    }

    @Override
    public void updateDepartmentFromRequest(UpdateDepartmentRequestDto request, DepartmentEntity entity) {
        if ( request == null ) {
            return;
        }

        if ( request.getDepartmentName() != null ) {
            entity.setDepartmentName( request.getDepartmentName() );
        }
        if ( request.getDescription() != null ) {
            entity.setDescription( request.getDescription() );
        }
    }
}
