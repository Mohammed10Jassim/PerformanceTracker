package com.pt.PerformanceTracker.organization.projects.specification;

import com.pt.PerformanceTracker.organization.projects.entity.ProjectEntity;
import com.pt.PerformanceTracker.organization.projects.enums.ProjectStatus;
import com.pt.PerformanceTracker.performance.kpaEmployee.entity.ProjectKpaEmployeeEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.util.Set;
import org.springframework.data.jpa.domain.Specification;

public final class ProjectSpecification {
   private ProjectSpecification() {
   }

   public static Specification<ProjectEntity> hasStatus(ProjectStatus status) {
      return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
   }

   public static Specification<ProjectEntity> isActive(boolean activeOnly) {
      return (root, query, cb) -> !activeOnly ? null : cb.isTrue(root.get("active"));
   }

   public static Specification<ProjectEntity> search(String search) {
      return (root, query, cb) -> {
         if (search != null && !search.isBlank()) {
            String value = "%" + search.trim().toLowerCase() + "%";
            return cb.or(cb.like(cb.lower(root.get("projectName")), value), cb.like(cb.lower(root.get("description")), value));
         } else {
            return null;
         }
      };
   }

   public static Specification<ProjectEntity> hasDepartmentIds(Set<Long> departmentIds) {
      return (root, query, cb) -> {
         if (departmentIds != null && !departmentIds.isEmpty()) {
            Join<Object, Object> projectDepartment = root.join("projectDepartments", JoinType.INNER);
            Join<Object, Object> department = projectDepartment.join("department", JoinType.INNER);
            query.distinct(true);
            return department.get("id").in(departmentIds);
         } else {
            return null;
         }
      };
   }

   public static Specification<ProjectEntity> hasEmployeeAssignment(Long employeeId) {
      return (root, query, cb) -> {
         if (employeeId == null) {
            return null;
         } else {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<ProjectKpaEmployeeEntity> assignment = subquery.from(ProjectKpaEmployeeEntity.class);
            Join<Object, Object> projectKpa = assignment.join("projectKpa", JoinType.INNER);
            Join<Object, Object> projectKra = projectKpa.join("projectKra", JoinType.INNER);
            Join<Object, Object> projectDepartment = projectKra.join("projectDepartment", JoinType.INNER);
            Join<Object, Object> project = projectDepartment.join("project", JoinType.INNER);
            subquery.select(project.get("id"));
            subquery.where(
               cb.and(
                  new Predicate[]{
                     cb.equal(assignment.get("employee").get("id"), employeeId),
                     cb.isTrue(assignment.get("active")),
                     cb.equal(project.get("id"), root.get("id"))
                  }
               )
            );
            return cb.exists(subquery);
         }
      };
   }
}
