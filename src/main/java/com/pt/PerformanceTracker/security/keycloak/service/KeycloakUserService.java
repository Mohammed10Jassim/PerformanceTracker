package com.pt.PerformanceTracker.security.keycloak.service;

import com.pt.PerformanceTracker.security.keycloak.dto.KeycloakUserResponse;
import java.util.List;
import java.util.Set;

public interface KeycloakUserService {
   KeycloakUserResponse findUserByUsername(String username);

   KeycloakUserResponse findUserByEmail(String email);

   List<KeycloakUserResponse> findAllUsers();

   KeycloakUserResponse findUserById(String userId);

   void updateUser(String userId, Set<String> roles, boolean enabled);

   void updateUserEnabled(String userId, boolean enabled);

   void updateUserRoles(String userId, Set<String> roles);

   void ensureEmployeeRole(String userId);
}
