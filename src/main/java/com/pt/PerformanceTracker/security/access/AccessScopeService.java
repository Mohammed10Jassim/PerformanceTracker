package com.pt.PerformanceTracker.security.access;

import com.pt.PerformanceTracker.organization.user.entity.UserEntity;

public interface AccessScopeService {
   boolean hasVerifiedRole(String roleName);

   boolean isAdmin(UserEntity user);

   boolean isHrAdmin(UserEntity user);

   boolean hasManagementRole(UserEntity user);

   boolean canAccessDepartment(UserEntity user, Long departmentId);

   void validateDepartmentAccess(Long departmentId);
}
