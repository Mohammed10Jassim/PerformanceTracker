package com.pt.PerformanceTracker.security.access;

import com.pt.PerformanceTracker.organization.user.entity.UserEntity;
import com.pt.PerformanceTracker.security.service.CustomUserService;
import lombok.Generated;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class AccessScopeServiceImpl implements AccessScopeService {
   private final CustomUserService customUserService;

   @Override
   public boolean hasVerifiedRole(String roleName) {
      return roleName != null && !roleName.isBlank() ? this.customUserService.hasRole(roleName) : false;
   }

   @Override
   public boolean isAdmin(UserEntity user) {
      return this.hasVerifiedRole("ADMIN");
   }

   @Override
   public boolean isHrAdmin(UserEntity user) {
      return this.hasVerifiedRole("HR_ADMIN");
   }

   @Override
   public boolean hasManagementRole(UserEntity user) {
      return this.hasVerifiedRole("MANAGER");
   }

   @Override
   public boolean canAccessDepartment(UserEntity user, Long departmentId) {
      if (this.isAdmin(user) || this.isHrAdmin(user)) {
         return true;
      } else if (!this.hasManagementRole(user)) {
         return false;
      } else {
         return departmentId != null && user != null && user.getEmployee() != null && user.getEmployee().getDepartmentDesignations() != null
            ? user.getEmployee()
               .getDepartmentDesignations()
               .stream()
               .anyMatch(
                  departmentDesignation -> departmentDesignation.getDepartment() != null && departmentId.equals(departmentDesignation.getDepartment().getId())
               )
            : false;
      }
   }

   @Override
   public void validateDepartmentAccess(Long departmentId) {
      UserEntity currentUser = this.customUserService.getCurrentUser();
      if (!this.canAccessDepartment(currentUser, departmentId)) {
         throw new AccessDeniedException("You do not have permission to access this department");
      }
   }

   @Generated
   public AccessScopeServiceImpl(final CustomUserService customUserService) {
      this.customUserService = customUserService;
   }
}
