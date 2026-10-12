package com.pt.PerformanceTracker.security.keycloak.service;

import com.pt.PerformanceTracker.security.keycloak.dto.KeycloakUserVerificationResult;

public interface KeycloakUserVerificationService {
   KeycloakUserVerificationResult verifyUsers();
}
