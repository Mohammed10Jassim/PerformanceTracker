package com.pt.PerformanceTracker.performance.kpa.specification;

import com.pt.PerformanceTracker.performance.kpa.entity.ProjectKpaEntity;
import com.pt.PerformanceTracker.performance.kpaEmployee.entity.ProjectKpaEmployeeEntity;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.util.Set;
import org.springframework.data.jpa.domain.Specification;

public final class ProjectKpaSpecification {
   private ProjectKpaSpecification() {
   }

   public static Specification<ProjectKpaEntity> hasProjectKraId(Long projectKraId) {
      return (root, query, criteriaBuilder) -> {
         if (projectKraId == null) {
            return null;
         } else {
            Join<Object, Object> projectKra = root.join("projectKra", JoinType.INNER);
            return criteriaBuilder.equal(projectKra.get("id"), projectKraId);
         }
      };
   }

   public static Specification<ProjectKpaEntity> hasProjectId(Long projectId) {
      return (root, query, criteriaBuilder) -> {
         if (projectId == null) {
            return null;
         } else {
            Join<Object, Object> projectKra = root.join("projectKra", JoinType.INNER);
            Join<Object, Object> projectDepartment = projectKra.join("projectDepartment", JoinType.INNER);
            Join<Object, Object> project = projectDepartment.join("project", JoinType.INNER);
            return criteriaBuilder.equal(project.get("id"), projectId);
         }
      };
   }

   public static Specification<ProjectKpaEntity> hasDepartmentId(Long departmentId) {
      return (root, query, criteriaBuilder) -> {
         if (departmentId == null) {
            return null;
         } else {
            Join<Object, Object> projectKra = root.join("projectKra", JoinType.INNER);
            Join<Object, Object> projectDepartment = projectKra.join("projectDepartment", JoinType.INNER);
            Join<Object, Object> department = projectDepartment.join("department", JoinType.INNER);
            return criteriaBuilder.equal(department.get("id"), departmentId);
         }
      };
   }

   public static Specification<ProjectKpaEntity> isActive(boolean activeOnly) {
      return (root, query, criteriaBuilder) -> !activeOnly ? null : criteriaBuilder.isTrue(root.get("active"));
   }

   public static Specification<ProjectKpaEntity> search(String search) {
      return (root, query, criteriaBuilder) -> {
         if (search != null && !search.isBlank()) {
            String value = "%" + search.trim().toLowerCase() + "%";
            return criteriaBuilder.or(
               criteriaBuilder.like(criteriaBuilder.lower(root.get("kpaName")), value),
               criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), value)
            );
         } else {
            return null;
         }
      };
   }

   public static Specification<ProjectKpaEntity> hasDepartmentIds(Set<Long> departmentIds) {
      return (root, query, criteriaBuilder) -> {
         if (departmentIds != null && !departmentIds.isEmpty()) {
            Join<Object, Object> projectKra = root.join("projectKra", JoinType.INNER);
            Join<Object, Object> projectDepartment = projectKra.join("projectDepartment", JoinType.INNER);
            Join<Object, Object> department = projectDepartment.join("department", JoinType.INNER);
            return department.get("id").in(departmentIds);
         } else {
            return null;
         }
      };
   }

   public static Specification<ProjectKpaEntity> hasEmployeeAssignment(Long employeeId) {
      return (root, query, cb) -> {
         if (employeeId == null) {
            return null;
         } else {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<ProjectKpaEmployeeEntity> assignment = subquery.from(ProjectKpaEmployeeEntity.class);
            subquery.select(assignment.get("projectKpa").get("id"));
            subquery.where(cb.and(cb.equal(assignment.get("employee").get("id"), employeeId), cb.isTrue(assignment.get("active"))));
            return root.get("id").in(new Expression[]{subquery});
         }
      };
   }
}
