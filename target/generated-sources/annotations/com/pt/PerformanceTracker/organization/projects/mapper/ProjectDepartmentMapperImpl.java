package com.pt.PerformanceTracker.organization.projects.mapper;

import com.pt.PerformanceTracker.organization.department.entity.DepartmentEntity;
import com.pt.PerformanceTracker.organization.projects.dto.ProjectDepartmentResponse;
import com.pt.PerformanceTracker.organization.projects.entity.ProjectDepartmentEntity;
import com.pt.PerformanceTracker.organization.projects.entity.ProjectEntity;
import com.pt.PerformanceTracker.organization.projects.enums.ProjectStatus;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-12T16:26:04+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class ProjectDepartmentMapperImpl implements ProjectDepartmentMapper {

    @Override
    public ProjectDepartmentResponse toResponse(ProjectDepartmentEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long projectId = null;
        String projectName = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        ProjectStatus projectStatus = null;
        Long departmentId = null;
        String departmentName = null;
        Long id = null;
        Boolean active = null;

        projectId = entityProjectId( entity );
        projectName = entityProjectProjectName( entity );
        startDate = entityProjectStartDate( entity );
        endDate = entityProjectEndDate( entity );
        projectStatus = entityProjectStatus( entity );
        departmentId = entityDepartmentId( entity );
        departmentName = entityDepartmentDepartmentName( entity );
        id = entity.getId();
        active = entity.getActive();

        ProjectDepartmentResponse projectDepartmentResponse = new ProjectDepartmentResponse( id, projectId, projectName, startDate, endDate, projectStatus, departmentId, departmentName, active );

        return projectDepartmentResponse;
    }

    private Long entityProjectId(ProjectDepartmentEntity projectDepartmentEntity) {
        ProjectEntity project = projectDepartmentEntity.getProject();
        if ( project == null ) {
            return null;
        }
        return project.getId();
    }

    private String entityProjectProjectName(ProjectDepartmentEntity projectDepartmentEntity) {
        ProjectEntity project = projectDepartmentEntity.getProject();
        if ( project == null ) {
            return null;
        }
        return project.getProjectName();
    }

    private LocalDate entityProjectStartDate(ProjectDepartmentEntity projectDepartmentEntity) {
        ProjectEntity project = projectDepartmentEntity.getProject();
        if ( project == null ) {
            return null;
        }
        return project.getStartDate();
    }

    private LocalDate entityProjectEndDate(ProjectDepartmentEntity projectDepartmentEntity) {
        ProjectEntity project = projectDepartmentEntity.getProject();
        if ( project == null ) {
            return null;
        }
        return project.getEndDate();
    }

    private ProjectStatus entityProjectStatus(ProjectDepartmentEntity projectDepartmentEntity) {
        ProjectEntity project = projectDepartmentEntity.getProject();
        if ( project == null ) {
            return null;
        }
        return project.getStatus();
    }

    private Long entityDepartmentId(ProjectDepartmentEntity projectDepartmentEntity) {
        DepartmentEntity department = projectDepartmentEntity.getDepartment();
        if ( department == null ) {
            return null;
        }
        return department.getId();
    }

    private String entityDepartmentDepartmentName(ProjectDepartmentEntity projectDepartmentEntity) {
        DepartmentEntity department = projectDepartmentEntity.getDepartment();
        if ( department == null ) {
            return null;
        }
        return department.getDepartmentName();
    }
}
