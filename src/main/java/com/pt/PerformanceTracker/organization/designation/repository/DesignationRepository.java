package com.pt.PerformanceTracker.organization.designation.repository;

import com.pt.PerformanceTracker.organization.designation.entity.DesignationEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignationRepository extends JpaRepository<DesignationEntity, Long> {
   Page<DesignationEntity> findByActiveTrue(Pageable pageable);

   Optional<DesignationEntity> findByDesignationNameIgnoreCase(String designationName);

   boolean existsByDesignationNameIgnoreCase(String designationName);
}
