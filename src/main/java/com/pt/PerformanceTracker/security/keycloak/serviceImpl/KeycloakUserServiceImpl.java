package com.pt.PerformanceTracker.security.keycloak.serviceImpl;

import com.pt.PerformanceTracker.security.keycloak.config.KeycloakAdminProperties;
import com.pt.PerformanceTracker.security.keycloak.dto.KeycloakUserResponse;
import com.pt.PerformanceTracker.security.keycloak.service.KeycloakUserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Generated;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RoleMappingResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class KeycloakUserServiceImpl implements KeycloakUserService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(KeycloakUserServiceImpl.class);
   private static final int PAGE_SIZE = 100;
   private static final String PERFORMANCE_TRACKER_CLIENT = "performance-tracker";
   private static final String DEFAULT_ROLE = "EMPLOYEE";
   private final Keycloak keycloak;
   private final KeycloakAdminProperties properties;
   private volatile String cachedClientInternalId;

   @Override
   public KeycloakUserResponse findUserByUsername(String username) {
      if (username != null && !username.isBlank()) {
         List<UserRepresentation> users = this.getUsersResource().searchByUsername(username.trim(), true);
         return users.isEmpty() ? null : this.toResponse(users.get(0));
      } else {
         return null;
      }
   }

   @Override
   public KeycloakUserResponse findUserByEmail(String email) {
      if (email != null && !email.isBlank()) {
         List<UserRepresentation> users = this.getUsersResource().searchByEmail(email.trim(), true);
         return users.isEmpty() ? null : this.toResponse(users.get(0));
      } else {
         return null;
      }
   }

   @Override
   public KeycloakUserResponse findUserById(String userId) {
      if (userId != null && !userId.isBlank()) {
         UserRepresentation user = this.getUsersResource().get(userId).toRepresentation();
         return this.toResponse(user);
      } else {
         return null;
      }
   }

   @Override
   public List<KeycloakUserResponse> findAllUsers() {
      List<KeycloakUserResponse> users = new ArrayList<>();
      UsersResource usersResource = this.getUsersResource();
      Map<String, Set<String>> rolesByUserId = this.loadClientRoleMembership();
      int first = 0;

      while (true) {
         List<UserRepresentation> page = usersResource.list(first, 100);
         if (page == null || page.isEmpty()) {
            break;
         }

         for (UserRepresentation user : page) {
            if (rolesByUserId != null) {
               users.add(this.toResponse(user, new HashSet<>(rolesByUserId.getOrDefault(user.getId(), Set.of()))));
            } else {
               users.add(this.toResponse(user));
            }
         }

         if (page.size() < 100) {
            break;
         }

         first += 100;
      }

      return users;
   }

   @Override
   public void updateUser(String userId, Set<String> roles, boolean enabled) {
      this.updateUserEnabled(userId, enabled);
      this.updateUserRoles(userId, roles);
   }

   @Override
   public void updateUserEnabled(String userId, boolean enabled) {
      this.validateUserId(userId);
      UserRepresentation user = this.getUsersResource().get(userId).toRepresentation();
      user.setEnabled(enabled);
      this.getUsersResource().get(userId).update(user);
   }

   @Override
   public void updateUserRoles(String userId, Set<String> roles) {
      this.validateUserId(userId);
      Set<String> requestedRoles = (Set<String>)(roles == null
         ? new HashSet<>()
         : roles.stream().filter(role -> role != null && !role.isBlank()).map(String::trim).collect(Collectors.toCollection(HashSet::new)));
      if (requestedRoles.isEmpty()) {
         requestedRoles.add("EMPLOYEE");
      }

      ClientResource clientResource = this.getClientResource();
      String clientInternalId = this.getClientInternalId();
      List<RoleRepresentation> newRoles = requestedRoles.stream().map(roleName -> {
         try {
            return clientResource.roles().get(roleName).toRepresentation();
         } catch (Exception var3x) {
            throw new IllegalArgumentException("Keycloak client role not found: " + roleName, var3x);
         }
      }).toList();
      RoleMappingResource roleMapping = this.getUsersResource().get(userId).roles();
      List<RoleRepresentation> existingRoles = roleMapping.clientLevel(clientInternalId).listAll();
      if (existingRoles != null && !existingRoles.isEmpty()) {
         roleMapping.clientLevel(clientInternalId).remove(existingRoles);
      }

      roleMapping.clientLevel(clientInternalId).add(newRoles);
   }

   @Override
   public void ensureEmployeeRole(String userId) {
      this.validateUserId(userId);
      Set<String> existingRoles = this.getClientRoles(userId);
      if (!existingRoles.isEmpty()) {
         log.debug("User {} already has client roles {}; preserving them", userId, existingRoles);
      } else {
         RoleRepresentation employeeRole = this.getClientResource().roles().get("EMPLOYEE").toRepresentation();
         this.getUsersResource().get(userId).roles().clientLevel(this.getClientInternalId()).add(List.of(employeeRole));
         log.info("Assigned default EMPLOYEE role to Keycloak user {}", userId);
      }
   }

   private Set<String> getClientRoles(String userId) {
      return this.getUsersResource()
         .get(userId)
         .roles()
         .clientLevel(this.getClientInternalId())
         .listAll()
         .stream()
         .<String>map(RoleRepresentation::getName)
         .collect(Collectors.toSet());
   }

   private ClientResource getClientResource() {
      String targetRealm = this.properties.getTargetRealm();
      String clientId = "performance-tracker";
      List<ClientRepresentation> clients = this.keycloak.realm(targetRealm).clients().findByClientId(clientId);
      if (clients != null && !clients.isEmpty()) {
         String internalId = clients.get(0).getId();
         if (internalId != null && !internalId.isBlank()) {
            return this.keycloak.realm(targetRealm).clients().get(internalId);
         } else {
            throw new IllegalStateException("Keycloak returned no internal ID for client '" + clientId + "' in realm '" + targetRealm + "'");
         }
      } else {
         throw new IllegalStateException("Keycloak client '" + clientId + "' not found in realm '" + targetRealm + "'");
      }
   }

   private String getClientInternalId() {
      String internalId = this.cachedClientInternalId;
      if (internalId != null) {
         return internalId;
      } else {
         synchronized (this) {
            if (this.cachedClientInternalId != null) {
               return this.cachedClientInternalId;
            } else {
               String targetRealm = this.properties.getTargetRealm();
               log.info("Looking up Keycloak client '{}' in target realm '{}'", "performance-tracker", targetRealm);
               List<ClientRepresentation> clients = this.keycloak.realm(targetRealm).clients().findByClientId("performance-tracker");
               if (clients != null && !clients.isEmpty()) {
                  this.cachedClientInternalId = clients.get(0).getId();
                  if (this.cachedClientInternalId != null && !this.cachedClientInternalId.isBlank()) {
                     log.info("Resolved Keycloak client '{}' to internal ID '{}'", "performance-tracker", this.cachedClientInternalId);
                     return this.cachedClientInternalId;
                  } else {
                     this.cachedClientInternalId = null;
                     throw new IllegalStateException("Keycloak returned an empty internal client ID for performance-tracker");
                  }
               } else {
                  log.error("No Keycloak client '{}' found in target realm '{}'", "performance-tracker", targetRealm);
                  throw new IllegalStateException("Keycloak client not found: performance-tracker in realm: " + targetRealm);
               }
            }
         }
      }
   }

   private UsersResource getUsersResource() {
      return this.keycloak.realm(this.properties.getTargetRealm()).users();
   }

   private Map<String, Set<String>> loadClientRoleMembership() {
      try {
         ClientResource clientResource = this.getClientResource();
         Map<String, Set<String>> rolesByUserId = new HashMap<>();

         for (RoleRepresentation role : clientResource.roles().list()) {
            int first = 0;

            while (true) {
               List<UserRepresentation> members = clientResource.roles().get(role.getName()).getUserMembers(first, 100);
               if (members == null || members.isEmpty()) {
                  break;
               }

               for (UserRepresentation member : members) {
                  rolesByUserId.computeIfAbsent(member.getId(), id -> new HashSet<>()).add(role.getName());
               }

               if (members.size() < 100) {
                  break;
               }

               first += 100;
            }
         }

         return rolesByUserId;
      } catch (RuntimeException var9) {
         log.warn("Could not load client role membership in bulk; falling back to per-user lookup: {}", var9.getMessage());
         return null;
      }
   }

   private KeycloakUserResponse toResponse(UserRepresentation user) {
      return this.toResponse(user, this.getClientRoles(user.getId()));
   }

   private KeycloakUserResponse toResponse(UserRepresentation user, Set<String> roles) {
      return KeycloakUserResponse.builder()
         .id(user.getId())
         .username(user.getUsername())
         .firstName(user.getFirstName())
         .lastName(user.getLastName())
         .email(user.getEmail())
         .enabled(user.isEnabled())
         .emailVerified(user.isEmailVerified())
         .roles(roles)
         .build();
   }

   private void validateUserId(String userId) {
      if (userId == null || userId.isBlank()) {
         throw new IllegalArgumentException("Keycloak user ID must not be blank");
      }
   }

   @Generated
   public KeycloakUserServiceImpl(final Keycloak keycloak, final KeycloakAdminProperties properties) {
      this.keycloak = keycloak;
      this.properties = properties;
   }
}
