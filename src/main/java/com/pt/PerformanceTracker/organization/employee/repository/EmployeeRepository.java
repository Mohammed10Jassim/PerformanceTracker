package com.pt.PerformanceTracker.organization.employee.repository;

import com.pt.PerformanceTracker.organization.employee.entity.EmployeeEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long>, JpaSpecificationExecutor<EmployeeEntity> {
   Optional<EmployeeEntity> findByEmployeeCode(Long employeeCode);

   boolean existsByEmployeeCode(Long employeeCode);

   Optional<EmployeeEntity> findByEmailIgnoreCase(String email);

   boolean existsByEmailIgnoreCase(String email);
}
