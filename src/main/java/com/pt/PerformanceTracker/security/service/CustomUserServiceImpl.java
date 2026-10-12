package com.pt.PerformanceTracker.security.service;

import com.pt.PerformanceTracker.organization.user.entity.UserEntity;
import com.pt.PerformanceTracker.organization.user.repository.UserRepository;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Generated;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class CustomUserServiceImpl implements CustomUserService {
   private final UserRepository userRepository;

   @Override
   public String getCurrentKeycloakId() {
      return ((Jwt)this.getAuthenticationToken().getToken()).getSubject();
   }

   @Override
   public String getCurrentFirstName() {
      return ((Jwt)this.getAuthenticationToken().getToken()).getClaimAsString("given_name");
   }

   @Override
   public String getCurrentLastName() {
      return ((Jwt)this.getAuthenticationToken().getToken()).getClaimAsString("family_name");
   }

   @Override
   public String getCurrentEmail() {
      return ((Jwt)this.getAuthenticationToken().getToken()).getClaimAsString("email");
   }

   @Override
   public UserEntity getCurrentUser() {
      String keycloakId = this.getCurrentKeycloakId();
      return this.userRepository
         .findByKeycloakId(keycloakId)
         .orElseThrow(() -> new UsernameNotFoundException("Current user not found for Keycloak ID : " + keycloakId));
   }

   @Override
   public Set<String> getCurrentUserAuthorities() {
      return this.getAuthenticationToken().getAuthorities().stream().<String>map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
   }

   @Override
   public boolean hasAuthority(String authority) {
      return this.getCurrentUserAuthorities().contains(authority);
   }

   @Override
   public boolean hasRole(String roleName) {
      String authority = "ROLE_" + roleName.toUpperCase();
      return this.hasAuthority(authority);
   }

   private JwtAuthenticationToken getAuthenticationToken() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication instanceof JwtAuthenticationToken) {
         return (JwtAuthenticationToken)authentication;
      } else {
         throw new IllegalStateException("Current Authentication is not a JWT authentication");
      }
   }

   @Generated
   public CustomUserServiceImpl(final UserRepository userRepository) {
      this.userRepository = userRepository;
   }
}
