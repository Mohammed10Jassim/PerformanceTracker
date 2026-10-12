package com.pt.PerformanceTracker.performance.kpaEmployee.repository;

import com.pt.PerformanceTracker.performance.kpaEmployee.entity.ProjectKpaEmployeeEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectKpaEmployeeRepository extends JpaRepository<ProjectKpaEmployeeEntity, Long>, JpaSpecificationExecutor<ProjectKpaEmployeeEntity> {
   Optional<ProjectKpaEmployeeEntity> findByIdAndEmployeeIdAndActiveTrue(Long assignmentId, Long employeeId);

   Optional<ProjectKpaEmployeeEntity> findByProjectKpaIdAndEmployeeId(Long projectKpaId, Long employeeId);

   Optional<ProjectKpaEmployeeEntity> findByProjectKpaIdAndEmployeeIdAndActiveTrue(Long projectKpaId, Long employeeId);

   boolean existsByProjectKpaIdAndEmployeeId(Long projectKpaId, Long employeeId);

   @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END\nFROM ProjectKpaEmployeeEntity a\nWHERE a.projectKpa.projectKra.id = :kraId\n  AND a.employee.id = :employeeId\n  AND a.active = true\n")
   boolean existsActiveAssignmentForKra(@Param("kraId") Long kraId, @Param("employeeId") Long employeeId);
}
