package com.pt.PerformanceTracker.security.config;

import com.pt.PerformanceTracker.security.converter.KeycloakJwtRoleConverter;
import com.pt.PerformanceTracker.security.handler.CustomAccessDeniedHandler;
import com.pt.PerformanceTracker.security.handler.CustomAuthenticationEntrypoint;
import java.util.Arrays;
import java.util.List;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizedUrl;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
   private final CustomAuthenticationEntrypoint authenticationEntrypoint;
   private final CustomAccessDeniedHandler accessDeniedHandler;
   @Value("${app.cors.allowed-origins:http://localhost:3000,http://localhost:5173}")
   private String allowedOrigins;

   @Bean
   public JwtAuthenticationConverter jwtAuthenticationConverter() {
      JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
      converter.setJwtGrantedAuthoritiesConverter(new KeycloakJwtRoleConverter());
      return converter;
   }

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      return (SecurityFilterChain)http.csrf(csrf -> csrf.disable())
         .cors(cors -> cors.configurationSource(this.corsConfigurationSource()))
         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
         .authorizeHttpRequests(
            auth -> ((AuthorizedUrl)((AuthorizedUrl)((AuthorizedUrl)auth.requestMatchers(new String[]{"/error", "/actuator/health", "/actuator/info"}))
                        .permitAll()
                        .requestMatchers(new String[]{"/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html"}))
                     .hasAnyRole(new String[]{"ADMIN", "HR_ADMIN"})
                     .anyRequest())
                  .authenticated()
         )
         .exceptionHandling(exception -> exception.authenticationEntryPoint(this.authenticationEntrypoint).accessDeniedHandler(this.accessDeniedHandler))
         .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(this.jwtAuthenticationConverter())))
         .build();
   }

   @Bean
   public CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration configuration = new CorsConfiguration();
      configuration.setAllowedOriginPatterns(
         Arrays.stream(this.allowedOrigins.split(","))
            .map(String::trim)
            .map(origin -> origin.endsWith("/") ? origin.substring(0, origin.length() - 1) : origin)
            .filter(origin -> !origin.isEmpty())
            .toList()
      );
      configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
      configuration.setAllowedHeaders(List.of("*"));
      configuration.setExposedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "Content-Disposition"));
      configuration.setAllowCredentials(true);
      configuration.setMaxAge(3600L);
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);
      return source;
   }

   @Generated
   public SecurityConfig(final CustomAuthenticationEntrypoint authenticationEntrypoint, final CustomAccessDeniedHandler accessDeniedHandler) {
      this.authenticationEntrypoint = authenticationEntrypoint;
      this.accessDeniedHandler = accessDeniedHandler;
   }
}
