package com.pt.PerformanceTracker.security.handler;

import com.pt.PerformanceTracker.common.response.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class CustomAuthenticationEntrypoint implements AuthenticationEntryPoint {
   private final ObjectMapper objectMapper;

   public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException
   {
      ApiResponse<Void> apiResponse = ApiResponse.<Void>builder().success(false).status(HttpStatus.UNAUTHORIZED.value()).message("Authentication is required to access this resource.").build();
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
   }

   @Generated
   public CustomAuthenticationEntrypoint(final ObjectMapper objectMapper) {
      this.objectMapper = objectMapper;
   }
}
