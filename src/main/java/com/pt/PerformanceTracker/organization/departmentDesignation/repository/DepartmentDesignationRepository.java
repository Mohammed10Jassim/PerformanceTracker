package com.pt.PerformanceTracker.organization.departmentDesignation.repository;

import com.pt.PerformanceTracker.organization.departmentDesignation.entity.DepartmentDesignationEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DepartmentDesignationRepository extends JpaRepository<DepartmentDesignationEntity, Long>, JpaSpecificationExecutor<DepartmentDesignationEntity> {
   boolean existsByDepartmentIdAndDesignationId(Long departmentId, Long designationId);

   Optional<DepartmentDesignationEntity> findByDepartmentIdAndDesignationId(Long departmentId, Long designationId);

   List<DepartmentDesignationEntity> findAllByDesignationId(Long designationId);
}
