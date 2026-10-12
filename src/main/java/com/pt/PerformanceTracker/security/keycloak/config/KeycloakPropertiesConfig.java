package com.pt.PerformanceTracker.security.keycloak.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({KeycloakAdminProperties.class})
public class KeycloakPropertiesConfig {
}
