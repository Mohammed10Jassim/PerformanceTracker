package com.pt.PerformanceTracker.organization.department.serviceImpl;

import com.pt.PerformanceTracker.common.exception.DuplicateResourceException;
import com.pt.PerformanceTracker.common.exception.ResourceAlreadyActiveException;
import com.pt.PerformanceTracker.common.exception.ResourceAlreadyInactiveException;
import com.pt.PerformanceTracker.common.exception.ResourceNotFoundException;
import com.pt.PerformanceTracker.common.exception.code.ErrorCode;
import com.pt.PerformanceTracker.organization.department.dto.requestDto.CreateDepartmentRequestDto;
import com.pt.PerformanceTracker.organization.department.dto.requestDto.UpdateDepartmentRequestDto;
import com.pt.PerformanceTracker.organization.department.dto.responseDto.DepartmentResponseDto;
import com.pt.PerformanceTracker.organization.department.entity.DepartmentEntity;
import com.pt.PerformanceTracker.organization.department.mapper.DepartmentMapper;
import com.pt.PerformanceTracker.organization.department.repository.DepartmentRepository;
import com.pt.PerformanceTracker.organization.department.service.DepartmentService;
import java.util.List;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(DepartmentServiceImpl.class);
   private final DepartmentRepository departmentRepository;
   private final DepartmentMapper departmentMapper;

   @Override
   public DepartmentResponseDto createDepartment(CreateDepartmentRequestDto departmentRequestDto) {
      log.info("Creating Department : {}", departmentRequestDto.getDepartmentName());
      this.validateDepartmentName(departmentRequestDto.getDepartmentName());
      DepartmentEntity department = this.departmentMapper.toEntity(departmentRequestDto);
      department.setDepartmentCode(this.departmentRepository.getNextDepartmentCode());
      DepartmentEntity savedDepartment = (DepartmentEntity)this.departmentRepository.save(department);
      log.info("Department Created Successfully. Code: {}, Name: {}", department.getDepartmentCode(), department.getDepartmentName());
      return this.departmentMapper.toResponse(savedDepartment);
   }

   @Override
   public DepartmentResponseDto updateDepartment(Long departmentId, UpdateDepartmentRequestDto request) {
      DepartmentEntity department = this.getDepartmentEntity(departmentId);
      this.validateDepartmentNameForUpdate(departmentId, request.getDepartmentName());
      this.departmentMapper.updateDepartmentFromRequest(request, department);
      DepartmentEntity updatedDepartment = (DepartmentEntity)this.departmentRepository.save(department);
      log.info("Updating Department Id : {}", departmentId);
      return this.departmentMapper.toResponse(updatedDepartment);
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public DepartmentResponseDto getDepartmentById(Long departmentId) {
      return this.departmentMapper.toResponse(this.getDepartmentEntity(departmentId));
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public List<DepartmentResponseDto> getAllDepartments(boolean activeOnly) {
      List<DepartmentEntity> departments;
      if (activeOnly) {
         departments = this.departmentRepository.findByActiveTrue();
      } else {
         departments = this.departmentRepository.findAll();
      }

      return this.departmentMapper.toResponseList(departments);
   }

   @Override
   public DepartmentResponseDto activateDepartment(Long departmentId) {
      DepartmentEntity department = this.getDepartmentEntity(departmentId);
      if (Boolean.TRUE.equals(department.getActive())) {
         throw new ResourceAlreadyActiveException(ErrorCode.DEPARTMENT_ALREADY_ACTIVE, "Department");
      } else {
         department.setActive(Boolean.valueOf(true));
         log.info("Activating Department Id : {}", departmentId);
         return this.departmentMapper.toResponse((DepartmentEntity)this.departmentRepository.save(department));
      }
   }

   @Override
   public DepartmentResponseDto deactivateDepartment(Long departmentId) {
      DepartmentEntity department = this.getDepartmentEntity(departmentId);
      if (Boolean.FALSE.equals(department.getActive())) {
         throw new ResourceAlreadyInactiveException(ErrorCode.DEPARTMENT_ALREADY_INACTIVE, "Department");
      } else {
         department.setActive(Boolean.valueOf(false));
         log.info("Deactivating Department Id : {}", departmentId);
         return this.departmentMapper.toResponse((DepartmentEntity)this.departmentRepository.save(department));
      }
   }

   private DepartmentEntity getDepartmentEntity(Long departmentId) {
      return this.departmentRepository
         .findById(departmentId)
         .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.DEPARTMENT_NOT_FOUND, "Department", "id", departmentId));
   }

   private void validateDepartmentName(String departmentName) {
      if (this.departmentRepository.existsByDepartmentNameIgnoreCase(departmentName)) {
         throw new DuplicateResourceException(ErrorCode.DEPARTMENT_ALREADY_EXISTS, "Department", "name", departmentName);
      }
   }

   private void validateDepartmentNameForUpdate(Long departmentId, String departmentName) {
      this.departmentRepository.findByDepartmentNameIgnoreCase(departmentName).ifPresent(existingDepartment -> {
         if (!existingDepartment.getId().equals(departmentId)) {
            throw new DuplicateResourceException(ErrorCode.DEPARTMENT_ALREADY_EXISTS, "Department", "name", departmentName);
         }
      });
   }

   @Generated
   public DepartmentServiceImpl(final DepartmentRepository departmentRepository, final DepartmentMapper departmentMapper) {
      this.departmentRepository = departmentRepository;
      this.departmentMapper = departmentMapper;
   }
}
