package com.pt.PerformanceTracker.organization.employee.mapper;

import com.pt.PerformanceTracker.organization.employee.dto.requestDto.CreateEmployeeRequestDto;
import com.pt.PerformanceTracker.organization.employee.dto.requestDto.UpdateEmployeeRequestDto;
import com.pt.PerformanceTracker.organization.employee.dto.responseDto.EmployeeResponseDto;
import com.pt.PerformanceTracker.organization.employee.entity.EmployeeEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-12T16:26:04+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public EmployeeEntity toEntity(CreateEmployeeRequestDto request) {
        if ( request == null ) {
            return null;
        }

        EmployeeEntity.EmployeeEntityBuilder employeeEntity = EmployeeEntity.builder();

        employeeEntity.employeeCode( request.getEmployeeCode() );
        employeeEntity.firstName( request.getFirstName() );
        employeeEntity.lastName( request.getLastName() );
        employeeEntity.email( request.getEmail() );
        employeeEntity.joiningDate( request.getJoiningDate() );

        return employeeEntity.build();
    }

    @Override
    public void updateEmployeeFromRequest(UpdateEmployeeRequestDto request, EmployeeEntity entity) {
        if ( request == null ) {
            return;
        }

        entity.setFirstName( request.getFirstName() );
        entity.setLastName( request.getLastName() );
        entity.setJoiningDate( request.getJoiningDate() );
    }

    @Override
    public EmployeeResponseDto toResponse(EmployeeEntity entity) {
        if ( entity == null ) {
            return null;
        }

        EmployeeResponseDto.EmployeeResponseDtoBuilder employeeResponseDto = EmployeeResponseDto.builder();

        employeeResponseDto.id( entity.getId() );
        employeeResponseDto.employeeCode( entity.getEmployeeCode() );
        employeeResponseDto.firstName( entity.getFirstName() );
        employeeResponseDto.lastName( entity.getLastName() );
        employeeResponseDto.email( entity.getEmail() );
        employeeResponseDto.joiningDate( entity.getJoiningDate() );
        employeeResponseDto.active( entity.getActive() );

        employeeResponseDto.department( mapDepartment(entity) );
        employeeResponseDto.designations( mapDesignations(entity.getDepartmentDesignations()) );

        return employeeResponseDto.build();
    }
}
