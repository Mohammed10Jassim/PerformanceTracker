package com.pt.PerformanceTracker.organization.user.service;

import com.pt.PerformanceTracker.organization.user.dto.requestDto.UpdateUserRequestDto;
import com.pt.PerformanceTracker.organization.user.dto.responseDto.UserResponseDto;
import java.util.List;

public interface UserService {
   UserResponseDto getUserById(Long userId);

   UserResponseDto getUserByEmployeeId(Long employeeId);

   List<UserResponseDto> getAllUsers(boolean activeOnly);

   UserResponseDto updateUser(Long userId, UpdateUserRequestDto request);

   UserResponseDto activateUser(Long userId);

   UserResponseDto deactivateUser(Long userId);
}
