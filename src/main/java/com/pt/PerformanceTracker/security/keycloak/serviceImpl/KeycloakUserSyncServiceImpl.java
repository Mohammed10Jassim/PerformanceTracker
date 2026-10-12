package com.pt.PerformanceTracker.security.keycloak.serviceImpl;

import com.pt.PerformanceTracker.organization.employee.entity.EmployeeEntity;
import com.pt.PerformanceTracker.organization.employee.repository.EmployeeRepository;
import com.pt.PerformanceTracker.organization.user.entity.UserEntity;
import com.pt.PerformanceTracker.organization.user.repository.UserRepository;
import com.pt.PerformanceTracker.security.keycloak.dto.KeycloakUserResponse;
import com.pt.PerformanceTracker.security.keycloak.dto.KeycloakUserSyncResult;
import com.pt.PerformanceTracker.security.keycloak.service.KeycloakUserService;
import com.pt.PerformanceTracker.security.keycloak.service.KeycloakUserSyncService;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import lombok.Generated;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KeycloakUserSyncServiceImpl implements KeycloakUserSyncService {
   private final KeycloakUserService keycloakUserService;
   private final UserRepository userRepository;
   private final EmployeeRepository employeeRepository;

   @Transactional
   @Override
   public KeycloakUserSyncResult syncUsers() {
      List<KeycloakUserResponse> keycloakUsers = this.keycloakUserService.findAllUsers();
      List<String> createdUsers = new ArrayList<>();
      List<String> updatedUsers = new ArrayList<>();
      List<String> skippedUsers = new ArrayList<>();
      List<String> failedUsers = new ArrayList<>();

      for (KeycloakUserResponse keycloakUser : keycloakUsers) {
         String email = this.normalizeEmail(keycloakUser.getEmail());

         try {
            if (email == null) {
               skippedUsers.add("Keycloak ID " + keycloakUser.getId() + " -> Email is missing");
            } else {
               Optional<UserEntity> existingByKeycloakId = this.userRepository.findByKeycloakId(keycloakUser.getId());
               if (existingByKeycloakId.isPresent()) {
                  this.updateExistingUser(existingByKeycloakId.get(), keycloakUser);
                  updatedUsers.add(email + " -> userId=" + existingByKeycloakId.get().getId());
               } else {
                  Optional<UserEntity> existingByEmail = this.userRepository.findByEmailIgnoreCase(email);
                  if (existingByEmail.isPresent()) {
                     UserEntity user = existingByEmail.get();
                     this.updateExistingUser(user, keycloakUser);
                     updatedUsers.add(email + " -> userId=" + user.getId());
                  } else {
                     Optional<EmployeeEntity> employeeOptional = this.employeeRepository.findByEmailIgnoreCase(email);
                     if (employeeOptional.isEmpty()) {
                        skippedUsers.add(email + " -> Employee not found");
                     } else {
                        EmployeeEntity employee = employeeOptional.get();
                        if (keycloakUser.getRoles() == null || keycloakUser.getRoles().isEmpty()) {
                           this.keycloakUserService.ensureEmployeeRole(keycloakUser.getId());
                           keycloakUser = this.keycloakUserService.findUserById(keycloakUser.getId());
                        }

                        UserEntity newUser = UserEntity.builder()
                           .employee(employee)
                           .firstName(employee.getFirstName())
                           .lastName(employee.getLastName())
                           .keycloakId(keycloakUser.getId())
                           .email(email)
                           .enabled(Boolean.TRUE.equals(keycloakUser.getEnabled()))
                           .build();
                        UserEntity savedUser = (UserEntity)this.userRepository.save(newUser);
                        createdUsers.add(email + " -> userId=" + savedUser.getId());
                     }
                  }
               }
            }
         } catch (Exception var15) {
            failedUsers.add(email + " -> " + var15.getMessage());
         }
      }

      return KeycloakUserSyncResult.builder()
         .total(keycloakUsers.size())
         .created(createdUsers.size())
         .updated(updatedUsers.size())
         .skipped(skippedUsers.size())
         .failed(failedUsers.size())
         .createdUsers(createdUsers)
         .updatedUsers(updatedUsers)
         .skippedUsers(skippedUsers)
         .failedUsers(failedUsers)
         .build();
   }

   private void updateExistingUser(UserEntity user, KeycloakUserResponse keycloakUser) {
      String email = this.normalizeEmail(keycloakUser.getEmail());
      user.setKeycloakId(keycloakUser.getId());
      user.setEmail(email);
      user.setEnabled(Boolean.TRUE.equals(keycloakUser.getEnabled()));
      if (user.getEmployee() != null) {
         user.setFirstName(user.getEmployee().getFirstName());
         user.setLastName(user.getEmployee().getLastName());
      }

      this.userRepository.save(user);
   }

   private String normalizeEmail(String email) {
      return email != null && !email.isBlank() ? email.trim().toLowerCase(Locale.ROOT) : null;
   }

   @Generated
   public KeycloakUserSyncServiceImpl(
      final KeycloakUserService keycloakUserService, final UserRepository userRepository, final EmployeeRepository employeeRepository
   ) {
      this.keycloakUserService = keycloakUserService;
      this.userRepository = userRepository;
      this.employeeRepository = employeeRepository;
   }
}
