package com.pt.PerformanceTracker.performance.kpaTask.specification;

import com.pt.PerformanceTracker.performance.kpaTask.entity.ProjectKpaTaskEntity;
import com.pt.PerformanceTracker.performance.kpaTask.entity.TaskStatus;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import java.util.Set;
import org.springframework.data.jpa.domain.Specification;

public final class ProjectKpaTaskSpecification {
   private ProjectKpaTaskSpecification() {
   }

   public static Specification<ProjectKpaTaskEntity> hasAssignmentId(Long assignmentId) {
      return (root, query, cb) -> {
         if (assignmentId == null) {
            return null;
         } else {
            Join<Object, Object> assignment = root.join("kpaEmployeeAssignment", JoinType.INNER);
            return cb.equal(assignment.get("id"), assignmentId);
         }
      };
   }

   public static Specification<ProjectKpaTaskEntity> hasEmployeeId(Long employeeId) {
      return (root, query, cb) -> {
         if (employeeId == null) {
            return null;
         } else {
            Join<Object, Object> assignment = root.join("kpaEmployeeAssignment", JoinType.INNER);
            Join<Object, Object> employee = assignment.join("employee", JoinType.INNER);
            return cb.equal(employee.get("id"), employeeId);
         }
      };
   }

   public static Specification<ProjectKpaTaskEntity> hasProjectId(Long projectId) {
      return (root, query, cb) -> {
         if (projectId == null) {
            return null;
         } else {
            Join<Object, Object> assignment = root.join("kpaEmployeeAssignment", JoinType.INNER);
            Join<Object, Object> projectKpa = assignment.join("projectKpa", JoinType.INNER);
            Join<Object, Object> projectKra = projectKpa.join("projectKra", JoinType.INNER);
            Join<Object, Object> projectDepartment = projectKra.join("projectDepartment", JoinType.INNER);
            Join<Object, Object> project = projectDepartment.join("project", JoinType.INNER);
            return cb.equal(project.get("id"), projectId);
         }
      };
   }

   public static Specification<ProjectKpaTaskEntity> hasDepartmentId(Long departmentId) {
      return (root, query, cb) -> {
         if (departmentId == null) {
            return null;
         } else {
            Join<Object, Object> assignment = root.join("kpaEmployeeAssignment", JoinType.INNER);
            Join<Object, Object> projectKpa = assignment.join("projectKpa", JoinType.INNER);
            Join<Object, Object> projectKra = projectKpa.join("projectKra", JoinType.INNER);
            Join<Object, Object> projectDepartment = projectKra.join("projectDepartment", JoinType.INNER);
            Join<Object, Object> department = projectDepartment.join("department", JoinType.INNER);
            return cb.equal(department.get("id"), departmentId);
         }
      };
   }

   public static Specification<ProjectKpaTaskEntity> hasDepartmentIds(Set<Long> departmentIds) {
      return (root, query, cb) -> {
         if (departmentIds != null && !departmentIds.isEmpty()) {
            Join<Object, Object> assignment = root.join("kpaEmployeeAssignment", JoinType.INNER);
            Join<Object, Object> projectKpa = assignment.join("projectKpa", JoinType.INNER);
            Join<Object, Object> projectKra = projectKpa.join("projectKra", JoinType.INNER);
            Join<Object, Object> projectDepartment = projectKra.join("projectDepartment", JoinType.INNER);
            Join<Object, Object> department = projectDepartment.join("department", JoinType.INNER);
            return department.get("id").in(departmentIds);
         } else {
            return null;
         }
      };
   }

   public static Specification<ProjectKpaTaskEntity> hasStatus(TaskStatus status) {
      return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
   }

   public static Specification<ProjectKpaTaskEntity> isActive(boolean activeOnly) {
      return (root, query, cb) -> !activeOnly ? null : cb.isTrue(root.get("active"));
   }

   public static Specification<ProjectKpaTaskEntity> isAssignmentActive() {
      return (root, query, cb) -> {
         Join<Object, Object> assignment = root.join("kpaEmployeeAssignment", JoinType.INNER);
         return cb.isTrue(assignment.get("active"));
      };
   }

   public static Specification<ProjectKpaTaskEntity> search(String search) {
      return (root, query, cb) -> {
         if (search != null && !search.isBlank()) {
            String value = "%" + search.trim().toLowerCase() + "%";
            return cb.or(cb.like(cb.lower(root.get("taskName")), value), cb.like(cb.lower(root.get("description")), value));
         } else {
            return null;
         }
      };
   }
}
