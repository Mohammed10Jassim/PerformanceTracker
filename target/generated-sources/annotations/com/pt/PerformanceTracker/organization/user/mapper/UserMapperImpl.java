package com.pt.PerformanceTracker.organization.user.mapper;

import com.pt.PerformanceTracker.organization.employee.entity.EmployeeEntity;
import com.pt.PerformanceTracker.organization.user.dto.responseDto.UserResponseDto;
import com.pt.PerformanceTracker.organization.user.dto.responseDto.UserRoleResponseDto;
import com.pt.PerformanceTracker.organization.user.entity.UserEntity;
import com.pt.PerformanceTracker.security.role.entity.RoleEntity;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-12T16:26:04+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDto toResponse(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserResponseDto.UserResponseDtoBuilder userResponseDto = UserResponseDto.builder();

        userResponseDto.employeeId( entityEmployeeId( entity ) );
        userResponseDto.employeeCode( entityEmployeeEmployeeCode( entity ) );
        userResponseDto.firstName( entity.getFirstName() );
        userResponseDto.lastName( entity.getLastName() );
        userResponseDto.id( entity.getId() );
        userResponseDto.keycloakId( entity.getKeycloakId() );
        userResponseDto.email( entity.getEmail() );
        userResponseDto.enabled( entity.getEnabled() );
        userResponseDto.roles( toRoleResponseSet( entity.getRoles() ) );

        return userResponseDto.build();
    }

    @Override
    public UserRoleResponseDto toRoleResponse(RoleEntity role) {
        if ( role == null ) {
            return null;
        }

        UserRoleResponseDto.UserRoleResponseDtoBuilder userRoleResponseDto = UserRoleResponseDto.builder();

        userRoleResponseDto.id( role.getId() );
        userRoleResponseDto.name( role.getName() );

        return userRoleResponseDto.build();
    }

    @Override
    public Set<UserRoleResponseDto> toRoleResponseSet(Set<RoleEntity> roles) {
        if ( roles == null ) {
            return null;
        }

        Set<UserRoleResponseDto> set = LinkedHashSet.newLinkedHashSet( roles.size() );
        for ( RoleEntity roleEntity : roles ) {
            set.add( toRoleResponse( roleEntity ) );
        }

        return set;
    }

    private Long entityEmployeeId(UserEntity userEntity) {
        EmployeeEntity employee = userEntity.getEmployee();
        if ( employee == null ) {
            return null;
        }
        return employee.getId();
    }

    private Long entityEmployeeEmployeeCode(UserEntity userEntity) {
        EmployeeEntity employee = userEntity.getEmployee();
        if ( employee == null ) {
            return null;
        }
        return employee.getEmployeeCode();
    }
}
