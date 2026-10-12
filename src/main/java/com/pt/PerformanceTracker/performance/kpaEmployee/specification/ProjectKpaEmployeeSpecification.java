package com.pt.PerformanceTracker.performance.kpaEmployee.specification;

import com.pt.PerformanceTracker.performance.kpaEmployee.entity.ProjectKpaEmployeeEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import java.util.Set;
import org.springframework.data.jpa.domain.Specification;

public final class ProjectKpaEmployeeSpecification {
   private ProjectKpaEmployeeSpecification() {
   }

   public static Specification<ProjectKpaEmployeeEntity> hasProjectKpaId(Long projectKpaId) {
      return (root, query, cb) -> {
         if (projectKpaId == null) {
            return null;
         } else {
            Join<Object, Object> projectKpa = root.join("projectKpa", JoinType.INNER);
            return cb.equal(projectKpa.get("id"), projectKpaId);
         }
      };
   }

   public static Specification<ProjectKpaEmployeeEntity> hasEmployeeId(Long employeeId) {
      return (root, query, cb) -> {
         if (employeeId == null) {
            return null;
         } else {
            Join<Object, Object> employee = root.join("employee", JoinType.INNER);
            return cb.equal(employee.get("id"), employeeId);
         }
      };
   }

   public static Specification<ProjectKpaEmployeeEntity> hasProjectId(Long projectId) {
      return (root, query, cb) -> {
         if (projectId == null) {
            return null;
         } else {
            Join<Object, Object> projectKpa = root.join("projectKpa", JoinType.INNER);
            Join<Object, Object> projectKra = projectKpa.join("projectKra", JoinType.INNER);
            Join<Object, Object> projectDepartment = projectKra.join("projectDepartment", JoinType.INNER);
            Join<Object, Object> project = projectDepartment.join("project", JoinType.INNER);
            return cb.equal(project.get("id"), projectId);
         }
      };
   }

   public static Specification<ProjectKpaEmployeeEntity> hasDepartmentId(Long departmentId) {
      return (root, query, cb) -> {
         if (departmentId == null) {
            return null;
         } else {
            Join<Object, Object> projectKpa = root.join("projectKpa", JoinType.INNER);
            Join<Object, Object> projectKra = projectKpa.join("projectKra", JoinType.INNER);
            Join<Object, Object> projectDepartment = projectKra.join("projectDepartment", JoinType.INNER);
            Join<Object, Object> department = projectDepartment.join("department", JoinType.INNER);
            return cb.equal(department.get("id"), departmentId);
         }
      };
   }

   public static Specification<ProjectKpaEmployeeEntity> hasDepartmentIds(Set<Long> departmentIds) {
      return (root, query, cb) -> {
         if (departmentIds != null && !departmentIds.isEmpty()) {
            Join<Object, Object> projectKpa = root.join("projectKpa", JoinType.INNER);
            Join<Object, Object> projectKra = projectKpa.join("projectKra", JoinType.INNER);
            Join<Object, Object> projectDepartment = projectKra.join("projectDepartment", JoinType.INNER);
            Join<Object, Object> department = projectDepartment.join("department", JoinType.INNER);
            return department.get("id").in(departmentIds);
         } else {
            return null;
         }
      };
   }

   public static Specification<ProjectKpaEmployeeEntity> isActive(boolean activeOnly) {
      return (root, query, cb) -> !activeOnly ? null : cb.isTrue(root.get("active"));
   }
}
