package com.pt.PerformanceTracker.organization.departmentDesignation.specification;

import com.pt.PerformanceTracker.organization.departmentDesignation.entity.DepartmentDesignationEntity;
import org.springframework.data.jpa.domain.Specification;

public final class DepartmentDesignationSpecification {
   private DepartmentDesignationSpecification() {
   }

   public static Specification<DepartmentDesignationEntity> hasDepartmentId(Long departmentId) {
      return (root, query, criteriaBuilder) -> departmentId == null ? null : criteriaBuilder.equal(root.get("department").get("id"), departmentId);
   }

   public static Specification<DepartmentDesignationEntity> hasDesignationId(Long designationId) {
      return (root, query, criteriaBuilder) -> designationId == null ? null : criteriaBuilder.equal(root.get("designation").get("id"), designationId);
   }

   public static Specification<DepartmentDesignationEntity> isActive(Boolean activeOnly) {
      return (root, query, criteriaBuilder) -> Boolean.TRUE.equals(activeOnly) ? criteriaBuilder.isTrue(root.get("active")) : null;
   }
}
