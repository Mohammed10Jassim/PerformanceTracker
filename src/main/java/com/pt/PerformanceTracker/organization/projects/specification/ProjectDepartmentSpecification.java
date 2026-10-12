package com.pt.PerformanceTracker.organization.projects.specification;

import com.pt.PerformanceTracker.organization.projects.entity.ProjectDepartmentEntity;
import com.pt.PerformanceTracker.organization.projects.enums.ProjectStatus;
import com.pt.PerformanceTracker.performance.kpaEmployee.entity.ProjectKpaEmployeeEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.util.Set;
import org.springframework.data.jpa.domain.Specification;

public final class ProjectDepartmentSpecification {
   private ProjectDepartmentSpecification() {
   }

   public static Specification<ProjectDepartmentEntity> hasProjectId(Long projectId) {
      return (root, query, criteriaBuilder) -> {
         if (projectId == null) {
            return null;
         } else {
            Join<Object, Object> project = root.join("project", JoinType.INNER);
            return criteriaBuilder.equal(project.get("id"), projectId);
         }
      };
   }

   public static Specification<ProjectDepartmentEntity> hasDepartmentId(Long departmentId) {
      return (root, query, criteriaBuilder) -> {
         if (departmentId == null) {
            return null;
         } else {
            Join<Object, Object> department = root.join("department", JoinType.INNER);
            return criteriaBuilder.equal(department.get("id"), departmentId);
         }
      };
   }

   public static Specification<ProjectDepartmentEntity> hasDepartmentIds(Set<Long> departmentIds) {
      return (root, query, criteriaBuilder) -> {
         if (departmentIds != null && !departmentIds.isEmpty()) {
            Join<Object, Object> department = root.join("department", JoinType.INNER);
            return department.get("id").in(departmentIds);
         } else {
            return null;
         }
      };
   }

   public static Specification<ProjectDepartmentEntity> hasEmployeeAssignment(Long employeeId) {
      return (root, query, criteriaBuilder) -> {
         if (employeeId == null) {
            return null;
         } else {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<ProjectKpaEmployeeEntity> assignment = subquery.from(ProjectKpaEmployeeEntity.class);
            Join<Object, Object> projectKpa = assignment.join("projectKpa", JoinType.INNER);
            Join<Object, Object> projectKra = projectKpa.join("projectKra", JoinType.INNER);
            Join<Object, Object> projectDepartment = projectKra.join("projectDepartment", JoinType.INNER);
            subquery.select(projectDepartment.get("id"));
            subquery.where(
               criteriaBuilder.and(
                  new Predicate[]{
                     criteriaBuilder.equal(assignment.get("employee").get("id"), employeeId),
                     criteriaBuilder.isTrue(assignment.get("active")),
                     criteriaBuilder.equal(projectDepartment.get("id"), root.get("id"))
                  }
               )
            );
            return criteriaBuilder.exists(subquery);
         }
      };
   }

   public static Specification<ProjectDepartmentEntity> hasProjectStatus(ProjectStatus projectStatus) {
      return (root, query, criteriaBuilder) -> {
         if (projectStatus == null) {
            return null;
         } else {
            Join<Object, Object> project = root.join("project", JoinType.INNER);
            return criteriaBuilder.equal(project.get("status"), projectStatus);
         }
      };
   }

   public static Specification<ProjectDepartmentEntity> isActive(Boolean activeOnly) {
      return (root, query, criteriaBuilder) -> !Boolean.TRUE.equals(activeOnly) ? null : criteriaBuilder.isTrue(root.get("active"));
   }
}
