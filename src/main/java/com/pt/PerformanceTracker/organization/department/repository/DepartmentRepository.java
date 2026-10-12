package com.pt.PerformanceTracker.organization.department.repository;

import com.pt.PerformanceTracker.organization.department.entity.DepartmentEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
   Optional<DepartmentEntity> findByDepartmentCode(Long departmentCode);

   Optional<DepartmentEntity> findByDepartmentNameIgnoreCase(String departmentName);

   Optional<DepartmentEntity> findById(Long id);

   boolean existsByDepartmentNameIgnoreCase(String departmentName);

   List<DepartmentEntity> findByActiveTrue();

   @Query(
      value = "SELECT nextval('department_code_seq')",
      nativeQuery = true
   )
   Long getNextDepartmentCode();
}
