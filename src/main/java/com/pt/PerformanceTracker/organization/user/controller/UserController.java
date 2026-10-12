package com.pt.PerformanceTracker.organization.user.controller;

import com.pt.PerformanceTracker.organization.user.dto.requestDto.UpdateUserRequestDto;
import com.pt.PerformanceTracker.organization.user.dto.responseDto.UserResponseDto;
import com.pt.PerformanceTracker.organization.user.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.Generated;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasAnyRole('ADMIN','HR_ADMIN')")
@RestController
@RequestMapping({"/users"})
public class UserController {
   private final UserService userService;

   @PreAuthorize("hasAnyRole('ADMIN','HR_ADMIN')")
   @GetMapping({"/{userId}"})
   public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
      return ResponseEntity.ok(this.userService.getUserById(userId));
   }

   @GetMapping({"/employee/{employeeId}"})
   public ResponseEntity<UserResponseDto> getUserByEmployeeId(@PathVariable Long employeeId) {
      return ResponseEntity.ok(this.userService.getUserByEmployeeId(employeeId));
   }

   @GetMapping
   public ResponseEntity<List<UserResponseDto>> getAllUsers(@RequestParam(defaultValue = "false") boolean activeOnly) {
      return ResponseEntity.ok(this.userService.getAllUsers(activeOnly));
   }

   @PutMapping({"/update/{userId}"})
   public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId, @Valid @RequestBody UpdateUserRequestDto request) {
      return ResponseEntity.ok(this.userService.updateUser(userId, request));
   }

   @PatchMapping({"/activate/{userId}"})
   public ResponseEntity<UserResponseDto> activateUser(@PathVariable Long userId) {
      return ResponseEntity.ok(this.userService.activateUser(userId));
   }

   @PatchMapping({"/deactivate/{userId}"})
   public ResponseEntity<UserResponseDto> deactivateUser(@PathVariable Long userId) {
      return ResponseEntity.ok(this.userService.deactivateUser(userId));
   }

   @Generated
   public UserController(final UserService userService) {
      this.userService = userService;
   }
}
