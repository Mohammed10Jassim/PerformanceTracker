package com.pt.PerformanceTracker.organization.departmentDesignation.serviceImpl;

import com.pt.PerformanceTracker.common.exception.DuplicateResourceException;
import com.pt.PerformanceTracker.common.exception.ResourceAlreadyActiveException;
import com.pt.PerformanceTracker.common.exception.ResourceAlreadyInactiveException;
import com.pt.PerformanceTracker.common.exception.ResourceNotFoundException;
import com.pt.PerformanceTracker.common.exception.code.ErrorCode;
import com.pt.PerformanceTracker.organization.department.entity.DepartmentEntity;
import com.pt.PerformanceTracker.organization.department.repository.DepartmentRepository;
import com.pt.PerformanceTracker.organization.departmentDesignation.dto.requestDto.CreateDepartmentDesignationRequestDto;
import com.pt.PerformanceTracker.organization.departmentDesignation.dto.responseDto.DepartmentDesignationResponseDto;
import com.pt.PerformanceTracker.organization.departmentDesignation.entity.DepartmentDesignationEntity;
import com.pt.PerformanceTracker.organization.departmentDesignation.mapper.DepartmentDesignationMapper;
import com.pt.PerformanceTracker.organization.departmentDesignation.repository.DepartmentDesignationRepository;
import com.pt.PerformanceTracker.organization.departmentDesignation.service.DepartmentDesignationService;
import com.pt.PerformanceTracker.organization.departmentDesignation.specification.DepartmentDesignationSpecification;
import com.pt.PerformanceTracker.organization.designation.entity.DesignationEntity;
import com.pt.PerformanceTracker.organization.designation.repository.DesignationRepository;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DepartmentDesignationServiceImpl implements DepartmentDesignationService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(DepartmentDesignationServiceImpl.class);
   private final DepartmentDesignationRepository departmentDesignationRepository;
   private final DepartmentRepository departmentRepository;
   private final DesignationRepository designationRepository;
   private final DepartmentDesignationMapper departmentDesignationMapper;

   @Override
   public DepartmentDesignationResponseDto createDepartmentDesignation(CreateDepartmentDesignationRequestDto request) {
      DepartmentEntity department = this.getDepartment(request.getDepartmentId());
      DesignationEntity designation = this.getDesignation(request.getDesignationId());
      this.validateDepartmentDesignationMapping(request.getDepartmentId(), request.getDesignationId());
      DepartmentDesignationEntity entity = DepartmentDesignationEntity.builder().department(department).designation(designation).build();
      DepartmentDesignationEntity savedEntity = (DepartmentDesignationEntity)this.departmentDesignationRepository.save(entity);
      log.info("Department [{}] mapped with Designation [{}]", department.getDepartmentName(), designation.getDesignationName());
      return this.departmentDesignationMapper.toResponse(savedEntity);
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public DepartmentDesignationResponseDto getDepartmentDesignationById(Long id) {
      return this.departmentDesignationMapper.toResponse(this.getDepartmentDesignationEntity(id));
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public Page<DepartmentDesignationResponseDto> getDepartmentDesignations(Long departmentId, Long designationId, boolean activeOnly, Pageable pageable) {
      Specification<DepartmentDesignationEntity> specification = Specification.where(DepartmentDesignationSpecification.hasDepartmentId(departmentId))
         .and(DepartmentDesignationSpecification.hasDesignationId(designationId))
         .and(DepartmentDesignationSpecification.isActive(activeOnly));
      return this.departmentDesignationRepository.findAll(specification, pageable).map(this.departmentDesignationMapper::toResponse);
   }

   @Override
   public DepartmentDesignationResponseDto activateDepartmentDesignation(Long id) {
      DepartmentDesignationEntity entity = this.getDepartmentDesignationEntity(id);
      if (Boolean.TRUE.equals(entity.getActive())) {
         throw new ResourceAlreadyActiveException(ErrorCode.DEPARTMENT_DESIGNATION_ALREADY_ACTIVE, "Department Designation");
      } else {
         entity.setActive(Boolean.valueOf(true));
         return this.departmentDesignationMapper.toResponse((DepartmentDesignationEntity)this.departmentDesignationRepository.save(entity));
      }
   }

   @Override
   public DepartmentDesignationResponseDto deactivateDepartmentDesignation(Long id) {
      DepartmentDesignationEntity entity = this.getDepartmentDesignationEntity(id);
      if (Boolean.FALSE.equals(entity.getActive())) {
         throw new ResourceAlreadyInactiveException(ErrorCode.DEPARTMENT_DESIGNATION_ALREADY_INACTIVE, "Department Designation");
      } else {
         entity.setActive(Boolean.valueOf(false));
         return this.departmentDesignationMapper.toResponse((DepartmentDesignationEntity)this.departmentDesignationRepository.save(entity));
      }
   }

   private DepartmentEntity getDepartment(Long departmentId) {
      return this.departmentRepository
         .findById(departmentId)
         .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.DEPARTMENT_NOT_FOUND, "Department", "id", departmentId));
   }

   private DesignationEntity getDesignation(Long designationId) {
      return (DesignationEntity)this.designationRepository
         .findById(designationId)
         .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.DESIGNATION_NOT_FOUND, "Designation", "id", designationId));
   }

   private DepartmentDesignationEntity getDepartmentDesignationEntity(Long id) {
      return (DepartmentDesignationEntity)this.departmentDesignationRepository
         .findById(id)
         .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.DEPARTMENT_DESIGNATION_NOT_FOUND, "Department Designation", "id", id));
   }

   private void validateDepartmentDesignationMapping(Long departmentId, Long designationId) {
      if (this.departmentDesignationRepository.existsByDepartmentIdAndDesignationId(departmentId, designationId)) {
         throw new DuplicateResourceException(
            ErrorCode.DEPARTMENT_DESIGNATION_ALREADY_EXISTS, "Department Designation Mapping", "departmentId/designationId", departmentId + "/" + designationId
         );
      }
   }

   @Generated
   public DepartmentDesignationServiceImpl(
      final DepartmentDesignationRepository departmentDesignationRepository,
      final DepartmentRepository departmentRepository,
      final DesignationRepository designationRepository,
      final DepartmentDesignationMapper departmentDesignationMapper
   ) {
      this.departmentDesignationRepository = departmentDesignationRepository;
      this.departmentRepository = departmentRepository;
      this.designationRepository = designationRepository;
      this.departmentDesignationMapper = departmentDesignationMapper;
   }
}
