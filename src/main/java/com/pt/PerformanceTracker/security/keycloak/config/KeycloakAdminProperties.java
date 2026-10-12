package com.pt.PerformanceTracker.security.keycloak.config;

import lombok.Generated;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
   prefix = "keycloak.admin"
)
public class KeycloakAdminProperties {
   private String serverUrl;
   private String realm;
   private String clientId;
   private String clientSecret;
   private String targetRealm;

   @Generated
   public String getServerUrl() {
      return this.serverUrl;
   }

   @Generated
   public String getRealm() {
      return this.realm;
   }

   @Generated
   public String getClientId() {
      return this.clientId;
   }

   @Generated
   public String getClientSecret() {
      return this.clientSecret;
   }

   @Generated
   public String getTargetRealm() {
      return this.targetRealm;
   }

   @Generated
   public void setServerUrl(final String serverUrl) {
      this.serverUrl = serverUrl;
   }

   @Generated
   public void setRealm(final String realm) {
      this.realm = realm;
   }

   @Generated
   public void setClientId(final String clientId) {
      this.clientId = clientId;
   }

   @Generated
   public void setClientSecret(final String clientSecret) {
      this.clientSecret = clientSecret;
   }

   @Generated
   public void setTargetRealm(final String targetRealm) {
      this.targetRealm = targetRealm;
   }
}
