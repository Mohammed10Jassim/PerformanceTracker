package com.pt.PerformanceTracker.organization.user.repository;

import com.pt.PerformanceTracker.organization.user.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
   Optional<UserEntity> findByKeycloakId(String keycloakId);

   Optional<UserEntity> findByEmailIgnoreCase(String email);

   Optional<UserEntity> findByEmployeeId(Long employeeId);

   List<UserEntity> findAllByEnabledTrue();

   boolean existsByKeycloakId(String keycloakId);

   boolean existsByEmailIgnoreCase(String email);

   boolean existsByEmployeeId(Long employeeId);
}
