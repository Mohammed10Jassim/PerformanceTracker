package com.pt.PerformanceTracker.organization.user.serviceImpl;

import com.pt.PerformanceTracker.common.exception.ResourceNotFoundException;
import com.pt.PerformanceTracker.common.exception.code.ErrorCode;
import com.pt.PerformanceTracker.organization.user.dto.requestDto.UpdateUserRequestDto;
import com.pt.PerformanceTracker.organization.user.dto.responseDto.UserResponseDto;
import com.pt.PerformanceTracker.organization.user.entity.UserEntity;
import com.pt.PerformanceTracker.organization.user.mapper.UserMapper;
import com.pt.PerformanceTracker.organization.user.repository.UserRepository;
import com.pt.PerformanceTracker.organization.user.service.UserService;
import com.pt.PerformanceTracker.security.keycloak.service.KeycloakUserService;
import com.pt.PerformanceTracker.security.role.entity.RoleEntity;
import com.pt.PerformanceTracker.security.role.repository.RoleRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Generated;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
   private final UserRepository userRepository;
   private final RoleRepository roleRepository;
   private final UserMapper userMapper;
   private final KeycloakUserService keycloakUserService;

   @Transactional(
      readOnly = true
   )
   @Override
   public UserResponseDto getUserById(Long userId) {
      UserEntity user = this.findUser(userId);
      return this.userMapper.toResponse(user);
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public UserResponseDto getUserByEmployeeId(Long employeeId) {
      UserEntity user = this.userRepository
         .findByEmployeeId(employeeId)
         .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "User", "employeeId", employeeId));
      return this.userMapper.toResponse(user);
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public List<UserResponseDto> getAllUsers(boolean activeOnly) {
      List<UserEntity> users = activeOnly ? this.userRepository.findAllByEnabledTrue() : this.userRepository.findAll();
      return users.stream().map(this.userMapper::toResponse).toList();
   }

   @Override
   public UserResponseDto updateUser(Long userId, UpdateUserRequestDto request) {
      UserEntity user = this.findUser(userId);
      Set<RoleEntity> roles = this.getRoles(request.getRoleIds());
      Set<String> roleNames = roles.stream().map(RoleEntity::getName).collect(Collectors.toSet());
      user.setEnabled(request.getEnabled());
      user.setRoles(roles);
      UserEntity savedUser = (UserEntity)this.userRepository.saveAndFlush(user);
      this.keycloakUserService.updateUser(user.getKeycloakId(), roleNames, request.getEnabled());
      return this.userMapper.toResponse(savedUser);
   }

   @Override
   public UserResponseDto activateUser(Long userId) {
      UserEntity user = this.findUser(userId);
      user.setEnabled(true);
      UserEntity savedUser = (UserEntity)this.userRepository.saveAndFlush(user);
      this.keycloakUserService.updateUserEnabled(user.getKeycloakId(), true);
      return this.userMapper.toResponse(savedUser);
   }

   @Override
   public UserResponseDto deactivateUser(Long userId) {
      UserEntity user = this.findUser(userId);
      user.setEnabled(false);
      UserEntity savedUser = (UserEntity)this.userRepository.saveAndFlush(user);
      this.keycloakUserService.updateUserEnabled(user.getKeycloakId(), false);
      return this.userMapper.toResponse(savedUser);
   }

   private Set<RoleEntity> getRoles(Set<Long> roleIds) {
      List<RoleEntity> roles = this.roleRepository.findAllById(roleIds);
      if (roles.size() != roleIds.size()) {
         throw new ResourceNotFoundException(ErrorCode.ROLE_NOT_FOUND, "Role", "ids", roleIds);
      } else {
         return new HashSet<>(roles);
      }
   }

   private UserEntity findUser(Long userId) {
      return (UserEntity)this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "User", "id", userId));
   }

   @Generated
   public UserServiceImpl(
      final UserRepository userRepository, final RoleRepository roleRepository, final UserMapper userMapper, final KeycloakUserService keycloakUserService
   ) {
      this.userRepository = userRepository;
      this.roleRepository = roleRepository;
      this.userMapper = userMapper;
      this.keycloakUserService = keycloakUserService;
   }
}
