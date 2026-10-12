package com.pt.PerformanceTracker.organization.employee.serviceImpl;

import com.pt.PerformanceTracker.common.exception.ResourceAlreadyActiveException;
import com.pt.PerformanceTracker.common.exception.ResourceAlreadyExistsException;
import com.pt.PerformanceTracker.common.exception.ResourceAlreadyInactiveException;
import com.pt.PerformanceTracker.common.exception.ResourceNotFoundException;
import com.pt.PerformanceTracker.common.exception.code.ErrorCode;
import com.pt.PerformanceTracker.organization.departmentDesignation.entity.DepartmentDesignationEntity;
import com.pt.PerformanceTracker.organization.departmentDesignation.repository.DepartmentDesignationRepository;
import com.pt.PerformanceTracker.organization.employee.dto.requestDto.CreateEmployeeRequestDto;
import com.pt.PerformanceTracker.organization.employee.dto.requestDto.UpdateEmployeeRequestDto;
import com.pt.PerformanceTracker.organization.employee.dto.responseDto.EmployeeResponseDto;
import com.pt.PerformanceTracker.organization.employee.entity.EmployeeEntity;
import com.pt.PerformanceTracker.organization.employee.mapper.EmployeeMapper;
import com.pt.PerformanceTracker.organization.employee.repository.EmployeeRepository;
import com.pt.PerformanceTracker.organization.employee.service.EmployeeService;
import com.pt.PerformanceTracker.organization.employee.specification.EmployeeSpecification;
import com.pt.PerformanceTracker.organization.user.entity.UserEntity;
import com.pt.PerformanceTracker.security.access.AccessScopeService;
import com.pt.PerformanceTracker.security.service.CustomUserService;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
   private final EmployeeRepository employeeRepository;
   private final DepartmentDesignationRepository departmentDesignationRepository;
   private final EmployeeMapper employeeMapper;
   private final AccessScopeService accessScopeService;
   private final CustomUserService customUserService;

   @Override
   public EmployeeResponseDto createEmployee(CreateEmployeeRequestDto request) {
      this.validateEmployeeCode(request.getEmployeeCode());
      this.validateEmail(request.getEmail());
      Set<DepartmentDesignationEntity> departmentDesignations = this.getDepartmentDesignations(request.getDepartmentDesignationIds());
      this.validateSameDepartment(departmentDesignations);
      EmployeeEntity employee = this.employeeMapper.toEntity(request);
      if (employee.getLastName() == null) {
         employee.setLastName("");
      } else {
         employee.setLastName(employee.getLastName().trim());
      }

      employee.setDepartmentDesignations(departmentDesignations);
      EmployeeEntity savedEmployee = (EmployeeEntity)this.employeeRepository.save(employee);
      log.info("Employee created successfully. Employee Code: {}", savedEmployee.getEmployeeCode());
      return this.employeeMapper.toResponse(savedEmployee);
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public EmployeeResponseDto getEmployeeById(Long employeeId) {
      EmployeeEntity employee = this.getEmployeeEntity(employeeId);
      if (!this.canViewEmployee(employee)) {
         throw new AccessDeniedException("You do not have permission to view this employee");
      } else {
         return this.employeeMapper.toResponse(employee);
      }
   }

   @Transactional(
      readOnly = true
   )
   @Override
   public Page<EmployeeResponseDto> getEmployees(Long employeeCode, Long departmentId, Long designationId, String search, boolean activeOnly, Pageable pageable) {
      UserEntity currentUser = this.customUserService.getCurrentUser();
      Specification<EmployeeEntity> specification = Specification.where(EmployeeSpecification.hasEmployeeCode(employeeCode))
         .and(EmployeeSpecification.hasDepartmentId(departmentId))
         .and(EmployeeSpecification.hasDesignationId(designationId))
         .and(EmployeeSpecification.search(search))
         .and(EmployeeSpecification.isActive(activeOnly));
      if (this.accessScopeService.isAdmin(currentUser) || this.accessScopeService.isHrAdmin(currentUser)) {
         return this.employeeRepository.findAll(specification, pageable).map(this.employeeMapper::toResponse);
      } else if (this.accessScopeService.hasManagementRole(currentUser)) {
         if (departmentId != null) {
            this.accessScopeService.validateDepartmentAccess(departmentId);
            return this.employeeRepository.findAll(specification, pageable).map(this.employeeMapper::toResponse);
         } else if (currentUser.getEmployee() != null && currentUser.getEmployee().getDepartmentDesignations() != null) {
            Set<Long> authorizedDepartmentIds = currentUser.getEmployee()
               .getDepartmentDesignations()
               .stream()
               .filter(mapping -> mapping.getDepartment() != null)
               .map(mapping -> mapping.getDepartment().getId())
               .collect(Collectors.toSet());
            if (authorizedDepartmentIds.isEmpty()) {
               return Page.empty(pageable);
            } else {
               specification = specification.and(EmployeeSpecification.hasDepartmentIds(authorizedDepartmentIds));
               return this.employeeRepository.findAll(specification, pageable).map(this.employeeMapper::toResponse);
            }
         } else {
            return Page.empty(pageable);
         }
      } else if (currentUser.getEmployee() == null) {
         return Page.empty(pageable);
      } else {
         Long currentEmployeeId = currentUser.getEmployee().getId();
         specification = specification.and(EmployeeSpecification.hasEmployeeId(currentEmployeeId));
         return this.employeeRepository.findAll(specification, pageable).map(this.employeeMapper::toResponse);
      }
   }

   @Override
   public EmployeeResponseDto updateEmployee(Long employeeId, UpdateEmployeeRequestDto request) {
      EmployeeEntity employee = this.getEmployeeEntity(employeeId);
      Set<DepartmentDesignationEntity> mappings = this.getDepartmentDesignations(request.getDepartmentDesignationIds());
      this.validateSameDepartment(mappings);
      this.employeeMapper.updateEmployeeFromRequest(request, employee);
      employee.setDepartmentDesignations(mappings);
      EmployeeEntity updated = (EmployeeEntity)this.employeeRepository.save(employee);
      log.info("Employee updated successfully. Id: {}", employeeId);
      return this.employeeMapper.toResponse(updated);
   }

   @Override
   public EmployeeResponseDto activateEmployee(Long employeeId) {
      EmployeeEntity employee = this.getEmployeeEntity(employeeId);
      if (Boolean.TRUE.equals(employee.getActive())) {
         throw new ResourceAlreadyActiveException(ErrorCode.EMPLOYEE_ALREADY_ACTIVE, "Employee");
      } else {
         employee.setActive(Boolean.valueOf(true));
         return this.employeeMapper.toResponse((EmployeeEntity)this.employeeRepository.save(employee));
      }
   }

   @Override
   public EmployeeResponseDto deactivateEmployee(Long employeeId) {
      EmployeeEntity employee = this.getEmployeeEntity(employeeId);
      if (Boolean.FALSE.equals(employee.getActive())) {
         throw new ResourceAlreadyInactiveException(ErrorCode.EMPLOYEE_ALREADY_INACTIVE, "Employee");
      } else {
         employee.setActive(Boolean.valueOf(false));
         return this.employeeMapper.toResponse((EmployeeEntity)this.employeeRepository.save(employee));
      }
   }

   private EmployeeEntity getEmployeeEntity(Long id) {
      return (EmployeeEntity)this.employeeRepository
         .findById(id)
         .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.EMPLOYEE_NOT_FOUND, "Employee", "id", id));
   }

   private void validateEmployeeCode(Long employeeCode) {
      if (this.employeeRepository.existsByEmployeeCode(employeeCode)) {
         throw new ResourceAlreadyExistsException(ErrorCode.EMPLOYEE_CODE_ALREADY_EXISTS, "Employee Code");
      }
   }

   private void validateEmail(String email) {
      if (this.employeeRepository.existsByEmailIgnoreCase(email)) {
         throw new ResourceAlreadyExistsException(ErrorCode.EMPLOYEE_EMAIL_ALREADY_EXISTS, "Email");
      }
   }

   private Set<DepartmentDesignationEntity> getDepartmentDesignations(Set<Long> ids) {
      Set<DepartmentDesignationEntity> mappings = new HashSet<>(this.departmentDesignationRepository.findAllById(ids));
      if (mappings.size() != ids.size()) {
         throw new ResourceNotFoundException(ErrorCode.DEPARTMENT_DESIGNATION_NOT_FOUND, "Department Designation", "ids", ids);
      } else {
         return mappings;
      }
   }

   private void validateSameDepartment(Set<DepartmentDesignationEntity> mappings) {
      Long departmentId = mappings.iterator().next().getDepartment().getId();
      boolean invalid = mappings.stream().anyMatch(mapping -> !mapping.getDepartment().getId().equals(departmentId));
      if (invalid) {
         throw new IllegalArgumentException("Employee designations must belong to the same department.");
      }
   }

   private boolean canViewEmployee(EmployeeEntity employee) {
      UserEntity currentUser = this.customUserService.getCurrentUser();
      if (this.accessScopeService.isAdmin(currentUser) || this.accessScopeService.isHrAdmin(currentUser)) {
         return true;
      } else {
         return this.accessScopeService.hasManagementRole(currentUser)
            ? employee.getDepartmentDesignations()
               .stream()
               .anyMatch(mapping -> this.accessScopeService.canAccessDepartment(currentUser, mapping.getDepartment().getId()))
            : currentUser.getEmployee() != null && employee.getId().equals(currentUser.getEmployee().getId());
      }
   }

   @Generated
   public EmployeeServiceImpl(
      final EmployeeRepository employeeRepository,
      final DepartmentDesignationRepository departmentDesignationRepository,
      final EmployeeMapper employeeMapper,
      final AccessScopeService accessScopeService,
      final CustomUserService customUserService
   ) {
      this.employeeRepository = employeeRepository;
      this.departmentDesignationRepository = departmentDesignationRepository;
      this.employeeMapper = employeeMapper;
      this.accessScopeService = accessScopeService;
      this.customUserService = customUserService;
   }
}
