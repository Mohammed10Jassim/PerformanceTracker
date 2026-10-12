package com.pt.PerformanceTracker.security.converter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class KeycloakJwtRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
   private static final String CLIENT_ID = "performance-tracker";

   public Collection<GrantedAuthority> convert(Jwt source) {
      Map<?, ?> resourceAccess = (Map<?, ?>)source.getClaim("resource_access");
      if (resourceAccess == null) {
         return Collections.emptyList();
      } else if (resourceAccess.get("performance-tracker") instanceof Map<?, ?> clientRoles) {
         return clientRoles.get("roles") instanceof List<?> roles
            ? roles.stream().map(Object::toString).map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList())
            : Collections.emptyList();
      } else {
         return Collections.emptyList();
      }
   }
}
