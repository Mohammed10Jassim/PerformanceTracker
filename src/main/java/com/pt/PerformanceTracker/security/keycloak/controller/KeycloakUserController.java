package com.pt.PerformanceTracker.security.keycloak.controller;

import com.pt.PerformanceTracker.security.keycloak.dto.KeycloakUserResponse;
import com.pt.PerformanceTracker.security.keycloak.dto.KeycloakUserSyncResult;
import com.pt.PerformanceTracker.security.keycloak.dto.KeycloakUserVerificationResult;
import com.pt.PerformanceTracker.security.keycloak.service.KeycloakUserService;
import com.pt.PerformanceTracker.security.keycloak.service.KeycloakUserSyncService;
import com.pt.PerformanceTracker.security.keycloak.service.KeycloakUserVerificationService;
import java.util.List;
import lombok.Generated;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasAnyRole('ADMIN','HR_ADMIN')")
@RestController
@RequestMapping({"/keycloak"})
public class KeycloakUserController {
   private final KeycloakUserService keycloakUserService;
   private final KeycloakUserSyncService keycloakUserSyncService;
   private final KeycloakUserVerificationService keycloakUserVerificationService;

   @GetMapping({"/users"})
   public List<KeycloakUserResponse> getAllKeycloakUsers() {
      return this.keycloakUserService.findAllUsers();
   }

   @PostMapping({"/sync-users"})
   public KeycloakUserSyncResult syncUsers() {
      return this.keycloakUserSyncService.syncUsers();
   }

   @GetMapping({"/verify-users"})
   public KeycloakUserVerificationResult verifyUsers() {
      return this.keycloakUserVerificationService.verifyUsers();
   }

   @Generated
   public KeycloakUserController(
      final KeycloakUserService keycloakUserService,
      final KeycloakUserSyncService keycloakUserSyncService,
      final KeycloakUserVerificationService keycloakUserVerificationService
   ) {
      this.keycloakUserService = keycloakUserService;
      this.keycloakUserSyncService = keycloakUserSyncService;
      this.keycloakUserVerificationService = keycloakUserVerificationService;
   }
}
