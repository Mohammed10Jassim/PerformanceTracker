package com.pt.PerformanceTracker.performance.kpaEmployee.mapper;

import com.pt.PerformanceTracker.organization.employee.entity.EmployeeEntity;
import com.pt.PerformanceTracker.performance.kpa.entity.ProjectKpaEntity;
import com.pt.PerformanceTracker.performance.kpaEmployee.dto.ProjectKpaEmployeeCreateRequest;
import com.pt.PerformanceTracker.performance.kpaEmployee.dto.ProjectKpaEmployeeResponse;
import com.pt.PerformanceTracker.performance.kpaEmployee.entity.ProjectKpaEmployeeEntity;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-12T16:26:04+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class ProjectKpaEmployeeMapperImpl implements ProjectKpaEmployeeMapper {

    @Override
    public ProjectKpaEmployeeEntity toEntity(ProjectKpaEmployeeCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        ProjectKpaEmployeeEntity.ProjectKpaEmployeeEntityBuilder projectKpaEmployeeEntity = ProjectKpaEmployeeEntity.builder();

        return projectKpaEmployeeEntity.build();
    }

    @Override
    public ProjectKpaEmployeeResponse toResponse(ProjectKpaEmployeeEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long projectKpaId = null;
        Long employeeId = null;
        Long id = null;
        BigDecimal completionPercentage = null;
        Boolean active = null;

        projectKpaId = entityProjectKpaId( entity );
        employeeId = entityEmployeeId( entity );
        id = entity.getId();
        completionPercentage = entity.getCompletionPercentage();
        active = entity.getActive();

        ProjectKpaEmployeeResponse projectKpaEmployeeResponse = new ProjectKpaEmployeeResponse( id, projectKpaId, employeeId, completionPercentage, active );

        return projectKpaEmployeeResponse;
    }

    private Long entityProjectKpaId(ProjectKpaEmployeeEntity projectKpaEmployeeEntity) {
        ProjectKpaEntity projectKpa = projectKpaEmployeeEntity.getProjectKpa();
        if ( projectKpa == null ) {
            return null;
        }
        return projectKpa.getId();
    }

    private Long entityEmployeeId(ProjectKpaEmployeeEntity projectKpaEmployeeEntity) {
        EmployeeEntity employee = projectKpaEmployeeEntity.getEmployee();
        if ( employee == null ) {
            return null;
        }
        return employee.getId();
    }
}
