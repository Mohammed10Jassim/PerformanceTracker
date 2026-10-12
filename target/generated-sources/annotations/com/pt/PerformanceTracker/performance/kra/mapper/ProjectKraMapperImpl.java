package com.pt.PerformanceTracker.performance.kra.mapper;

import com.pt.PerformanceTracker.organization.department.entity.DepartmentEntity;
import com.pt.PerformanceTracker.organization.projects.entity.ProjectDepartmentEntity;
import com.pt.PerformanceTracker.organization.projects.entity.ProjectEntity;
import com.pt.PerformanceTracker.performance.kra.dto.ProjectKraResponse;
import com.pt.PerformanceTracker.performance.kra.entity.ProjectKraEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-12T16:26:04+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class ProjectKraMapperImpl implements ProjectKraMapper {

    @Override
    public ProjectKraResponse toResponse(ProjectKraEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long projectDepartmentId = null;
        Long projectId = null;
        String projectName = null;
        Long departmentId = null;
        String departmentName = null;
        Long id = null;
        String kraName = null;
        String description = null;
        Boolean active = null;

        projectDepartmentId = entityProjectDepartmentId( entity );
        projectId = entityProjectDepartmentProjectId( entity );
        projectName = entityProjectDepartmentProjectProjectName( entity );
        departmentId = entityProjectDepartmentDepartmentId( entity );
        departmentName = entityProjectDepartmentDepartmentDepartmentName( entity );
        id = entity.getId();
        kraName = entity.getKraName();
        description = entity.getDescription();
        active = entity.getActive();

        ProjectKraResponse projectKraResponse = new ProjectKraResponse( id, projectDepartmentId, projectId, projectName, departmentId, departmentName, kraName, description, active );

        return projectKraResponse;
    }

    private Long entityProjectDepartmentId(ProjectKraEntity projectKraEntity) {
        ProjectDepartmentEntity projectDepartment = projectKraEntity.getProjectDepartment();
        if ( projectDepartment == null ) {
            return null;
        }
        return projectDepartment.getId();
    }

    private Long entityProjectDepartmentProjectId(ProjectKraEntity projectKraEntity) {
        ProjectDepartmentEntity projectDepartment = projectKraEntity.getProjectDepartment();
        if ( projectDepartment == null ) {
            return null;
        }
        ProjectEntity project = projectDepartment.getProject();
        if ( project == null ) {
            return null;
        }
        return project.getId();
    }

    private String entityProjectDepartmentProjectProjectName(ProjectKraEntity projectKraEntity) {
        ProjectDepartmentEntity projectDepartment = projectKraEntity.getProjectDepartment();
        if ( projectDepartment == null ) {
            return null;
        }
        ProjectEntity project = projectDepartment.getProject();
        if ( project == null ) {
            return null;
        }
        return project.getProjectName();
    }

    private Long entityProjectDepartmentDepartmentId(ProjectKraEntity projectKraEntity) {
        ProjectDepartmentEntity projectDepartment = projectKraEntity.getProjectDepartment();
        if ( projectDepartment == null ) {
            return null;
        }
        DepartmentEntity department = projectDepartment.getDepartment();
        if ( department == null ) {
            return null;
        }
        return department.getId();
    }

    private String entityProjectDepartmentDepartmentDepartmentName(ProjectKraEntity projectKraEntity) {
        ProjectDepartmentEntity projectDepartment = projectKraEntity.getProjectDepartment();
        if ( projectDepartment == null ) {
            return null;
        }
        DepartmentEntity department = projectDepartment.getDepartment();
        if ( department == null ) {
            return null;
        }
        return department.getDepartmentName();
    }
}
