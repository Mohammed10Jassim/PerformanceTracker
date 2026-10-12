package com.pt.PerformanceTracker.security.keycloak.serviceImpl;

import com.pt.PerformanceTracker.organization.user.entity.UserEntity;
import com.pt.PerformanceTracker.organization.user.repository.UserRepository;
import com.pt.PerformanceTracker.security.keycloak.dto.KeycloakUserResponse;
import com.pt.PerformanceTracker.security.keycloak.dto.KeycloakUserVerificationResult;
import com.pt.PerformanceTracker.security.keycloak.service.KeycloakUserService;
import com.pt.PerformanceTracker.security.keycloak.service.KeycloakUserVerificationService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import lombok.Generated;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KeycloakUserVerificationServiceImpl implements KeycloakUserVerificationService {
   private final UserRepository userRepository;
   private final KeycloakUserService keycloakUserService;

   @Transactional(
      readOnly = true
   )
   @Override
   public KeycloakUserVerificationResult verifyUsers() {
      List<UserEntity> pmsUsers = this.userRepository.findAll();
      List<KeycloakUserResponse> keycloakUsers = this.keycloakUserService.findAllUsers();
      Map<String, KeycloakUserResponse> keycloakById = new HashMap<>();
      Map<String, KeycloakUserResponse> keycloakByEmail = new HashMap<>();

      for (KeycloakUserResponse keycloakUser : keycloakUsers) {
         String id = this.normalizeId(keycloakUser.getId());
         String email = this.normalizeEmail(keycloakUser.getEmail());
         if (id != null) {
            keycloakById.putIfAbsent(id, keycloakUser);
         }

         if (email != null) {
            keycloakByEmail.putIfAbsent(email, keycloakUser);
         }
      }

      KeycloakUserVerificationResult result = KeycloakUserVerificationResult.builder()
         .pmsUsersChecked(pmsUsers.size())
         .missingUsers(new ArrayList<>())
         .emailMismatchUsers(new ArrayList<>())
         .employeeLinkErrorsDetails(new ArrayList<>())
         .keycloakIdMismatchUsers(new ArrayList<>())
         .keycloakAccountsWithoutPmsUserDetails(new ArrayList<>())
         .build();
      Set<String> matchedKeycloakIds = new HashSet<>();

      for (UserEntity pmsUser : pmsUsers) {
         String pmsId = this.normalizeId(pmsUser.getKeycloakId());
         String pmsEmail = this.normalizeEmail(pmsUser.getEmail());
         boolean employeeLinkValid = pmsUser.getEmployee() != null;
         if (!employeeLinkValid) {
            result.getEmployeeLinkErrorsDetails().add(this.describe(pmsUser) + " -> Employee link is missing");
         }

         if (pmsId == null) {
            result.getMissingUsers().add(this.describe(pmsUser) + " -> Keycloak ID is empty");
         } else {
            KeycloakUserResponse keycloakUser = keycloakById.get(pmsId);
            if (keycloakUser == null) {
               result.getMissingUsers().add(this.describe(pmsUser) + " -> Keycloak ID not found: " + pmsId);
               KeycloakUserResponse emailMatch = pmsEmail == null ? null : keycloakByEmail.get(pmsEmail);
               if (emailMatch != null) {
                  result.getKeycloakIdMismatchUsers()
                     .add(this.describe(pmsUser) + " -> Stored Keycloak ID=" + pmsId + ", Keycloak ID for email=" + emailMatch.getId());
               }
            } else {
               String actualKeycloakId = this.normalizeId(keycloakUser.getId());
               if (actualKeycloakId != null) {
                  matchedKeycloakIds.add(actualKeycloakId);
               }

               String keycloakEmail = this.normalizeEmail(keycloakUser.getEmail());
               boolean emailMatches = pmsEmail != null && pmsEmail.equals(keycloakEmail);
               if (!emailMatches) {
                  result.getEmailMismatchUsers()
                     .add(this.describe(pmsUser) + " -> PMS email=" + pmsUser.getEmail() + ", Keycloak email=" + keycloakUser.getEmail());
               }

               if (employeeLinkValid && emailMatches) {
                  result.setMatched(result.getMatched() + 1);
               }
            }
         }
      }

      result.setMissingInKeycloak(result.getMissingUsers().size());
      result.setEmailMismatches(result.getEmailMismatchUsers().size());
      result.setEmployeeLinkErrors(result.getEmployeeLinkErrorsDetails().size());

      for (KeycloakUserResponse keycloakUser : keycloakUsers) {
         String keycloakId = this.normalizeId(keycloakUser.getId());
         if (keycloakId != null && !matchedKeycloakIds.contains(keycloakId)) {
            result.getKeycloakAccountsWithoutPmsUserDetails().add("Email=" + keycloakUser.getEmail() + ", Keycloak ID=" + keycloakId);
         }
      }

      result.setKeycloakAccountsWithoutPmsUser(result.getKeycloakAccountsWithoutPmsUserDetails().size());
      return result;
   }

   private String normalizeId(String id) {
      return id != null && !id.isBlank() ? id.trim() : null;
   }

   private String normalizeEmail(String email) {
      return email != null && !email.isBlank() ? email.trim().toLowerCase(Locale.ROOT) : null;
   }

   private String describe(UserEntity user) {
      return "PMS user ID=" + user.getId() + ", email=" + user.getEmail();
   }

   @Generated
   public KeycloakUserVerificationServiceImpl(final UserRepository userRepository, final KeycloakUserService keycloakUserService) {
      this.userRepository = userRepository;
      this.keycloakUserService = keycloakUserService;
   }
}
