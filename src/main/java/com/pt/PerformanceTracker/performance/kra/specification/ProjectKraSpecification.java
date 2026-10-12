package com.pt.PerformanceTracker.performance.kra.specification;

import com.pt.PerformanceTracker.performance.kpaEmployee.entity.ProjectKpaEmployeeEntity;
import com.pt.PerformanceTracker.performance.kra.entity.ProjectKraEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.util.Set;
import org.springframework.data.jpa.domain.Specification;

public final class ProjectKraSpecification {
   private ProjectKraSpecification() {
   }

   public static Specification<ProjectKraEntity> hasProjectDepartmentId(Long projectDepartmentId) {
      return (root, query, criteriaBuilder) -> {
         if (projectDepartmentId == null) {
            return null;
         } else {
            Join<Object, Object> projectDepartment = root.join("projectDepartment", JoinType.INNER);
            return criteriaBuilder.equal(projectDepartment.get("id"), projectDepartmentId);
         }
      };
   }

   public static Specification<ProjectKraEntity> hasProjectId(Long projectId) {
      return (root, query, criteriaBuilder) -> {
         if (projectId == null) {
            return null;
         } else {
            Join<Object, Object> projectDepartment = root.join("projectDepartment", JoinType.INNER);
            Join<Object, Object> project = projectDepartment.join("project", JoinType.INNER);
            return criteriaBuilder.equal(project.get("id"), projectId);
         }
      };
   }

   public static Specification<ProjectKraEntity> hasDepartmentId(Long departmentId) {
      return (root, query, criteriaBuilder) -> {
         if (departmentId == null) {
            return null;
         } else {
            Join<Object, Object> projectDepartment = root.join("projectDepartment", JoinType.INNER);
            Join<Object, Object> department = projectDepartment.join("department", JoinType.INNER);
            return criteriaBuilder.equal(department.get("id"), departmentId);
         }
      };
   }

   public static Specification<ProjectKraEntity> hasDepartmentIds(Set<Long> departmentIds) {
      return (root, query, criteriaBuilder) -> {
         if (departmentIds != null && !departmentIds.isEmpty()) {
            Join<Object, Object> projectDepartment = root.join("projectDepartment", JoinType.INNER);
            Join<Object, Object> department = projectDepartment.join("department", JoinType.INNER);
            return department.get("id").in(departmentIds);
         } else {
            return null;
         }
      };
   }

   public static Specification<ProjectKraEntity> hasEmployeeAssignment(Long employeeId) {
      return (root, query, criteriaBuilder) -> {
         if (employeeId == null) {
            return null;
         } else {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<ProjectKpaEmployeeEntity> assignment = subquery.from(ProjectKpaEmployeeEntity.class);
            Join<Object, Object> projectKpa = assignment.join("projectKpa", JoinType.INNER);
            Join<Object, Object> projectKra = projectKpa.join("projectKra", JoinType.INNER);
            subquery.select(projectKra.get("id"));
            subquery.where(
               criteriaBuilder.and(
                  new Predicate[]{
                     criteriaBuilder.equal(assignment.get("employee").get("id"), employeeId),
                     criteriaBuilder.isTrue(assignment.get("active")),
                     criteriaBuilder.equal(projectKra.get("id"), root.get("id"))
                  }
               )
            );
            return criteriaBuilder.exists(subquery);
         }
      };
   }

   public static Specification<ProjectKraEntity> isActive(boolean activeOnly) {
      return (root, query, criteriaBuilder) -> !activeOnly ? null : criteriaBuilder.isTrue(root.get("active"));
   }

   public static Specification<ProjectKraEntity> search(String search) {
      return (root, query, criteriaBuilder) -> {
         if (search != null && !search.isBlank()) {
            String value = "%" + search.trim().toLowerCase() + "%";
            return criteriaBuilder.or(
               criteriaBuilder.like(criteriaBuilder.lower(root.get("kraName")), value),
               criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), value)
            );
         } else {
            return null;
         }
      };
   }
}
