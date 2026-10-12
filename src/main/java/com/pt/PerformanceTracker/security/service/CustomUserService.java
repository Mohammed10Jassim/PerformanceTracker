package com.pt.PerformanceTracker.security.service;

import com.pt.PerformanceTracker.organization.user.entity.UserEntity;
import java.util.Set;

public interface CustomUserService {
   String getCurrentKeycloakId();

   String getCurrentFirstName();

   String getCurrentLastName();

   String getCurrentEmail();

   UserEntity getCurrentUser();

   Set<String> getCurrentUserAuthorities();

   boolean hasAuthority(String authority);

   boolean hasRole(String roleName);
}
