package com.pt.PerformanceTracker.security.keycloak.service;

import com.pt.PerformanceTracker.security.keycloak.dto.KeycloakUserSyncResult;

public interface KeycloakUserSyncService {
   KeycloakUserSyncResult syncUsers();
}
