package com.pt.PerformanceTracker.organization.designation.serviceImpl;

import com.pt.PerformanceTracker.common.entity.BaseEntity;
import com.pt.PerformanceTracker.common.exception.DuplicateResourceException;
import com.pt.PerformanceTracker.common.exception.ResourceAlreadyActiveException;
import com.pt.PerformanceTracker.common.exception.ResourceAlreadyInactiveException;
import com.pt.PerformanceTracker.common.exception.ResourceNotFoundException;
import com.pt.PerformanceTracker.common.exception.code.ErrorCode;
import com.pt.PerformanceTracker.organization.department.entity.DepartmentEntity;
import com.pt.PerformanceTracker.organization.department.repository.DepartmentRepository;
import com.pt.PerformanceTracker.organization.departmentDesignation.entity.DepartmentDesignationEntity;
import com.pt.PerformanceTracker.organization.departmentDesignation.repository.DepartmentDesignationRepository;
import com.pt.PerformanceTracker.organization.designation.dto.requestDto.CreateDesignationRequestDto;
import com.pt.PerformanceTracker.organization.designation.dto.requestDto.UpdateDesignationRequestDto;
import com.pt.PerformanceTracker.organization.designation.dto.responseDto.DesignationResponseDto;
import com.pt.PerformanceTracker.organization.designation.entity.DesignationEntity;
import com.pt.PerformanceTracker.organization.designation.mapper.DesignationMapper;
import com.pt.PerformanceTracker.organization.designation.repository.DesignationRepository;
import com.pt.PerformanceTracker.organization.designation.service.DesignationService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DesignationServiceImpl implements DesignationService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(DesignationServiceImpl.class);
   private final DesignationRepository designationRepository;
   private final DesignationMapper designationMapper;
   private final DepartmentRepository departmentRepository;
   private final DepartmentDesignationRepository departmentDesignationRepository;

   @Override
   public DesignationResponseDto createDesignation(CreateDesignationRequestDto request) {
      this.validateDesignationName(request.getDesignationName());
      Set<Long> departmentIds = request.getDepartmentIds();
      List<DepartmentEntity> departments = new ArrayList<>();
      if (departmentIds != null && !departmentIds.isEmpty()) {
         for (Long departmentId : departmentIds) {
            DepartmentEntity departmentEntity = this.departmentRepository
               .findById(departmentId)
               .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.DEPARTMENT_NOT_FOUND, "Department", "id", departmentId));
            departments.add(departmentEntity);
         }
      }

      DesignationEntity designationEntity = this.designationMapper.toEntity(request);
      DesignationEntity savedEntity = (DesignationEntity)this.designationRepository.save(designationEntity);

      for (DepartmentEntity departmentEntity : departments) {
         DepartmentDesignationEntity mapping = DepartmentDesignationEntity.builder().department(departmentEntity).designation(savedEntity).build();
         this.departmentDesignationRepository.save(mapping);
      }

      return this.buildDesignationResponse(savedEntity);
   }

   @Override
   public DesignationResponseDto updateDesignation(Long designationId, UpdateDesignationRequestDto request) {
      DesignationEntity designation = this.getDesignationEntity(designationId);
      this.validateDesignationNameForUpdate(designationId, request.getDesignationName());
      this.designationMapper.updateDesignationFromRequest(request, designation);
      DesignationEntity savedEntity = (DesignationEntity)this.designationRepository.save(designation);
      return this.buildDesignationResponse(savedEntity);
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public DesignationResponseDto getDesignationById(Long designationId) {
      DesignationEntity designation = this.getDesignationEntity(designationId);
      return this.buildDesignationResponse(designation);
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public Page<DesignationResponseDto> getAllDesignations(boolean activeOnly, Pageable pageable) {
      Page<DesignationEntity> designations;
      if (activeOnly) {
         designations = this.designationRepository.findByActiveTrue(pageable);
      } else {
         designations = this.designationRepository.findAll(pageable);
      }

      return designations.map(this::buildDesignationResponse);
   }

   @Override
   public DesignationResponseDto activateDesignation(Long designationId) {
      DesignationEntity designation = this.getDesignationEntity(designationId);
      if (Boolean.TRUE.equals(designation.getActive())) {
         throw new ResourceAlreadyActiveException(ErrorCode.DESIGNATION_ALREADY_ACTIVE, designation.getDesignationName());
      } else {
         designation.setActive(Boolean.valueOf(true));
         log.info("Designation Activated : {}", designation.getDesignationName());
         DesignationEntity savedDesignation = (DesignationEntity)this.designationRepository.save(designation);
         return this.buildDesignationResponse(savedDesignation);
      }
   }

   @Override
   public DesignationResponseDto deactivateDesignation(Long designationId) {
      DesignationEntity designationEntity = this.getDesignationEntity(designationId);
      if (Boolean.FALSE.equals(designationEntity.getActive())) {
         throw new ResourceAlreadyInactiveException(ErrorCode.DESIGNATION_ALREADY_INACTIVE, designationEntity.getDesignationName());
      } else {
         designationEntity.setActive(Boolean.valueOf(false));
         log.info("Designation Deactivated : {}", designationEntity.getDesignationName());
         DesignationEntity savedDesignation = (DesignationEntity)this.designationRepository.save(designationEntity);
         return this.buildDesignationResponse(savedDesignation);
      }
   }

   private DesignationResponseDto buildDesignationResponse(DesignationEntity designation) {
      DesignationResponseDto response = this.designationMapper.toResponse(designation);
      List<DepartmentDesignationEntity> mappings = this.departmentDesignationRepository.findAllByDesignationId(designation.getId());
      response.setDepartmentIds(mappings.stream().map(mapping -> mapping.getDepartment().getId()).toList());
      response.setDepartmentDesignationIds(mappings.stream().map(BaseEntity::getId).toList());
      return response;
   }

   private DesignationEntity getDesignationEntity(Long designationId) {
      return (DesignationEntity)this.designationRepository
         .findById(designationId)
         .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.DESIGNATION_NOT_FOUND, "Designation", "id", designationId));
   }

   private void validateDesignationName(String designationName) {
      if (this.designationRepository.existsByDesignationNameIgnoreCase(designationName)) {
         throw new DuplicateResourceException(ErrorCode.DESIGNATION_ALREADY_EXISTS, "Designation", "designationName", designationName);
      }
   }

   private void validateDesignationNameForUpdate(Long designationId, String designationName) {
      this.designationRepository.findByDesignationNameIgnoreCase(designationName).ifPresent(existingDesignation -> {
         if (!existingDesignation.getId().equals(designationId)) {
            throw new DuplicateResourceException(ErrorCode.DESIGNATION_ALREADY_EXISTS, "Designation", "id", designationId);
         }
      });
   }

   @Generated
   public DesignationServiceImpl(
      final DesignationRepository designationRepository,
      final DesignationMapper designationMapper,
      final DepartmentRepository departmentRepository,
      final DepartmentDesignationRepository departmentDesignationRepository
   ) {
      this.designationRepository = designationRepository;
      this.designationMapper = designationMapper;
      this.departmentRepository = departmentRepository;
      this.departmentDesignationRepository = departmentDesignationRepository;
   }
}
