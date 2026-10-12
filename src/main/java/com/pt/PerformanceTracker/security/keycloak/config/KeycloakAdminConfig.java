package com.pt.PerformanceTracker.security.keycloak.config;

import jakarta.annotation.PostConstruct;
import lombok.Generated;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakAdminConfig {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(KeycloakAdminConfig.class);
   private final KeycloakAdminProperties properties;

   @PostConstruct
   public void validateProperties() {
      log.info("Keycloak Server URL : {}", this.properties.getServerUrl());
      log.info("Keycloak Realm      : {}", this.properties.getRealm());
      log.info("Keycloak Client ID  : {}", this.properties.getClientId());
      if (this.properties.getClientSecret() == null || this.properties.getClientSecret().isBlank()) {
         log.warn("keycloak.admin.client-secret is empty - /keycloak/** and user role updates will fail");
      }
   }

   @Bean
   public Keycloak keycloakAdminClient() {
      return KeycloakBuilder.builder()
         .serverUrl(this.properties.getServerUrl())
         .realm(this.properties.getRealm())
         .clientId(this.properties.getClientId())
         .clientSecret(this.properties.getClientSecret())
         .grantType("client_credentials")
         .build();
   }

   @Generated
   public KeycloakAdminConfig(final KeycloakAdminProperties properties) {
      this.properties = properties;
   }
}
