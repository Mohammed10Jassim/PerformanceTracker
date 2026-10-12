package com.pt.PerformanceTracker.organization.projects.mapper;

import com.pt.PerformanceTracker.organization.projects.dto.ProjectCreateRequest;
import com.pt.PerformanceTracker.organization.projects.dto.ProjectResponse;
import com.pt.PerformanceTracker.organization.projects.dto.ProjectUpdateRequest;
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
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public ProjectEntity toEntity(ProjectCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        ProjectEntity.ProjectEntityBuilder projectEntity = ProjectEntity.builder();

        projectEntity.projectName( request.projectName() );
        projectEntity.description( request.description() );
        projectEntity.startDate( request.startDate() );
        projectEntity.endDate( request.endDate() );

        return projectEntity.build();
    }

    @Override
    public void updateEntity(ProjectUpdateRequest request, ProjectEntity project) {
        if ( request == null ) {
            return;
        }

        if ( request.projectName() != null ) {
            project.setProjectName( request.projectName() );
        }
        if ( request.description() != null ) {
            project.setDescription( request.description() );
        }
        if ( request.startDate() != null ) {
            project.setStartDate( request.startDate() );
        }
        if ( request.endDate() != null ) {
            project.setEndDate( request.endDate() );
        }
    }

    @Override
    public ProjectResponse toResponse(ProjectEntity project) {
        if ( project == null ) {
            return null;
        }

        Long id = null;
        String projectName = null;
        String description = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        ProjectStatus status = null;
        Boolean active = null;

        id = project.getId();
        projectName = project.getProjectName();
        description = project.getDescription();
        startDate = project.getStartDate();
        endDate = project.getEndDate();
        status = project.getStatus();
        active = project.getActive();

        ProjectResponse projectResponse = new ProjectResponse( id, projectName, description, startDate, endDate, status, active );

        return projectResponse;
    }
}
