package com.pt.PerformanceTracker.security.handler;

import com.pt.PerformanceTracker.common.response.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.Generated;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
   private final ObjectMapper objectMapper;

   public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
      ApiResponse<?> apiResponse = ApiResponse.builder().success(false).status(403).message("You do not have permission to access this resource.").build();
      response.setStatus(403);
      response.setContentType("application/json");
      response.getWriter().write(this.objectMapper.writeValueAsString(apiResponse));
   }

   @Generated
   public CustomAccessDeniedHandler(final ObjectMapper objectMapper) {
      this.objectMapper = objectMapper;
   }
}
