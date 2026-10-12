package com.pt.PerformanceTracker.organization.departmentDesignation.mapper;

import com.pt.PerformanceTracker.organization.department.entity.DepartmentEntity;
import com.pt.PerformanceTracker.organization.departmentDesignation.dto.requestDto.CreateDepartmentDesignationRequestDto;
import com.pt.PerformanceTracker.organization.departmentDesignation.dto.responseDto.DepartmentDesignationResponseDto;
import com.pt.PerformanceTracker.organization.departmentDesignation.entity.DepartmentDesignationEntity;
import com.pt.PerformanceTracker.organization.designation.entity.DesignationEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-12T16:26:04+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class DepartmentDesignationMapperImpl implements DepartmentDesignationMapper {

    @Override
    public DepartmentDesignationEntity toEntity(CreateDepartmentDesignationRequestDto request) {
        if ( request == null ) {
            return null;
        }

        DepartmentDesignationEntity.DepartmentDesignationEntityBuilder departmentDesignationEntity = DepartmentDesignationEntity.builder();

        return departmentDesignationEntity.build();
    }

    @Override
    public DepartmentDesignationResponseDto toResponse(DepartmentDesignationEntity entity) {
        if ( entity == null ) {
            return null;
        }

        DepartmentDesignationResponseDto departmentDesignationResponseDto = new DepartmentDesignationResponseDto();

        departmentDesignationResponseDto.setDepartmentId( entityDepartmentId( entity ) );
        departmentDesignationResponseDto.setDepartmentName( entityDepartmentDepartmentName( entity ) );
        departmentDesignationResponseDto.setDesignationId( entityDesignationId( entity ) );
        departmentDesignationResponseDto.setDesignationName( entityDesignationDesignationName( entity ) );
        departmentDesignationResponseDto.setId( entity.getId() );
        departmentDesignationResponseDto.setActive( entity.getActive() );

        return departmentDesignationResponseDto;
    }

    private Long entityDepartmentId(DepartmentDesignationEntity departmentDesignationEntity) {
        DepartmentEntity department = departmentDesignationEntity.getDepartment();
        if ( department == null ) {
            return null;
        }
        return department.getId();
    }

    private String entityDepartmentDepartmentName(DepartmentDesignationEntity departmentDesignationEntity) {
        DepartmentEntity department = departmentDesignationEntity.getDepartment();
        if ( department == null ) {
            return null;
        }
        return department.getDepartmentName();
    }

    private Long entityDesignationId(DepartmentDesignationEntity departmentDesignationEntity) {
        DesignationEntity designation = departmentDesignationEntity.getDesignation();
        if ( designation == null ) {
            return null;
        }
        return designation.getId();
    }

    private String entityDesignationDesignationName(DepartmentDesignationEntity departmentDesignationEntity) {
        DesignationEntity designation = departmentDesignationEntity.getDesignation();
        if ( designation == null ) {
            return null;
        }
        return designation.getDesignationName();
    }
}
