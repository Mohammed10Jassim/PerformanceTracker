package com.pt.PerformanceTracker.organization.employee.specification;

import com.pt.PerformanceTracker.organization.employee.entity.EmployeeEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import java.util.Set;
import org.springframework.data.jpa.domain.Specification;

public final class EmployeeSpecification {
   private EmployeeSpecification() {
   }

   public static Specification<EmployeeEntity> hasEmployeeCode(Long employeeCode) {
      return (root, query, criteriaBuilder) -> employeeCode == null ? null : criteriaBuilder.equal(root.get("employeeCode"), employeeCode);
   }

   public static Specification<EmployeeEntity> hasEmployeeId(Long employeeId) {
      return (root, query, criteriaBuilder) -> employeeId == null ? null : criteriaBuilder.equal(root.get("id"), employeeId);
   }

   public static Specification<EmployeeEntity> isActive(Boolean activeOnly) {
      return (root, query, criteriaBuilder) -> !Boolean.TRUE.equals(activeOnly) ? null : criteriaBuilder.isTrue(root.get("active"));
   }

   public static Specification<EmployeeEntity> hasDepartmentId(Long departmentId) {
      return (root, query, criteriaBuilder) -> {
         if (departmentId == null) {
            return null;
         } else {
            Join<Object, Object> departmentDesignation = root.join("departmentDesignations", JoinType.INNER);
            Join<Object, Object> department = departmentDesignation.join("department", JoinType.INNER);
            return criteriaBuilder.equal(department.get("id"), departmentId);
         }
      };
   }

   public static Specification<EmployeeEntity> hasDepartmentIds(Set<Long> departmentIds) {
      return (root, query, criteriaBuilder) -> {
         if (departmentIds != null && !departmentIds.isEmpty()) {
            Join<Object, Object> departmentDesignation = root.join("departmentDesignations", JoinType.INNER);
            Join<Object, Object> department = departmentDesignation.join("department", JoinType.INNER);
            return department.get("id").in(departmentIds);
         } else {
            return null;
         }
      };
   }

   public static Specification<EmployeeEntity> hasDesignationId(Long designationId) {
      return (root, query, criteriaBuilder) -> {
         if (designationId == null) {
            return null;
         } else {
            Join<Object, Object> departmentDesignation = root.join("departmentDesignations", JoinType.INNER);
            Join<Object, Object> designation = departmentDesignation.join("designation", JoinType.INNER);
            return criteriaBuilder.equal(designation.get("id"), designationId);
         }
      };
   }

   public static Specification<EmployeeEntity> search(String search) {
      return (root, query, criteriaBuilder) -> {
         if (search != null && !search.isBlank()) {
            String value = "%" + search.trim().toLowerCase() + "%";
            return criteriaBuilder.or(
               new Predicate[]{
                  criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), value),
                  criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), value),
                  criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), value)
               }
            );
         } else {
            return null;
         }
      };
   }
}
