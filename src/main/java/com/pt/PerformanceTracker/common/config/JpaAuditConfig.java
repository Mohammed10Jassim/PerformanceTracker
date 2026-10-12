package com.pt.PerformanceTracker.common.config;

import com.pt.PerformanceTracker.security.service.CustomUserService;
import java.util.Optional;
import lombok.Generated;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class JpaAuditConfig {
   private final CustomUserService customUserService;

   @Bean
   public AuditorAware<String> auditorAware() {
      return () -> {
         try {
            String email = this.customUserService.getCurrentEmail();
            return email != null && !email.isBlank() ? Optional.of(email) : Optional.of("SYSTEM");
         } catch (IllegalStateException var2) {
            return Optional.of("SYSTEM");
         }
      };
   }

   @Generated
   public JpaAuditConfig(final CustomUserService customUserService) {
      this.customUserService = customUserService;
   }
}
