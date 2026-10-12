package com.pt.PerformanceTracker.organization.user.mapper;

import com.pt.PerformanceTracker.organization.user.dto.responseDto.UserResponseDto;
import com.pt.PerformanceTracker.organization.user.dto.responseDto.UserRoleResponseDto;
import com.pt.PerformanceTracker.organization.user.entity.UserEntity;
import com.pt.PerformanceTracker.security.role.entity.RoleEntity;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(
   componentModel = "spring",
   unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
   @Mappings({@Mapping(
         target = "employeeId",
         source = "employee.id"
      ), @Mapping(
         target = "employeeCode",
         source = "employee.employeeCode"
      ), @Mapping(
         target = "firstName",
         source = "firstName"
      ), @Mapping(
         target = "lastName",
         source = "lastName"
      )})
   UserResponseDto toResponse(UserEntity entity);

   UserRoleResponseDto toRoleResponse(RoleEntity role);

   Set<UserRoleResponseDto> toRoleResponseSet(Set<RoleEntity> roles);
}
