package com.pt.PerformanceTracker.security.controller;

import com.pt.PerformanceTracker.security.service.CustomUserService;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/security"})
public class SecurityTestController {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(SecurityTestController.class);
   private final CustomUserService customUserService;

   @GetMapping({"/me"})
   public ResponseEntity<?> getCurrentUser() {
      String keycloakId = this.customUserService.getCurrentKeycloakId();
      String email = this.customUserService.getCurrentEmail();
      String firstName = this.customUserService.getCurrentFirstName();
      String lastName = this.customUserService.getCurrentLastName();
      log.debug("Current user: keycloakId={}, email={}", keycloakId, email);
      Map<String, String> body = new LinkedHashMap<>();
      body.put("keycloakId", String.valueOf(keycloakId));
      body.put("firstName", firstName);
      body.put("lastName", lastName);
      body.put("email", String.valueOf(email));
      return ResponseEntity.ok(body);
   }

   @GetMapping({"/authorities"})
   public ResponseEntity<?> getAuthorities() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      return ResponseEntity.ok(authentication.getAuthorities());
   }

   @Generated
   public SecurityTestController(final CustomUserService customUserService) {
      this.customUserService = customUserService;
   }
}
