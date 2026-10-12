package com.pt.PerformanceTracker.security.role.initializer;

import com.pt.PerformanceTracker.security.role.entity.RoleEntity;
import com.pt.PerformanceTracker.security.role.repository.RoleRepository;
import lombok.Generated;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleDataInitializer implements CommandLineRunner {
   private final RoleRepository roleRepository;

   public void run(String... args) {
      this.createRole("ADMIN");
      this.createRole("HR_ADMIN");
      this.createRole("MANAGER");
      this.createRole("EMPLOYEE");
   }

   private void createRole(String roleName) {
      if (!this.roleRepository.existsByNameIgnoreCase(roleName)) {
         RoleEntity role = RoleEntity.builder().name(roleName).build();
         this.roleRepository.save(role);
      }
   }

   @Generated
   public RoleDataInitializer(final RoleRepository roleRepository) {
      this.roleRepository = roleRepository;
   }
}
