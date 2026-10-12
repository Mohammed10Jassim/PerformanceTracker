package com.pt.PerformanceTracker.performance.kpaTask.mapper;

import com.pt.PerformanceTracker.performance.kpaEmployee.entity.ProjectKpaEmployeeEntity;
import com.pt.PerformanceTracker.performance.kpaTask.dto.ProjectKpaTaskCreateRequest;
import com.pt.PerformanceTracker.performance.kpaTask.dto.ProjectKpaTaskResponse;
import com.pt.PerformanceTracker.performance.kpaTask.dto.ProjectKpaTaskUpdateRequest;
import com.pt.PerformanceTracker.performance.kpaTask.entity.ProjectKpaTaskEntity;
import com.pt.PerformanceTracker.performance.kpaTask.entity.TaskStatus;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-12T16:26:04+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class ProjectKpaTaskMapperImpl implements ProjectKpaTaskMapper {

    @Override
    public ProjectKpaTaskEntity toEntity(ProjectKpaTaskCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        ProjectKpaTaskEntity.ProjectKpaTaskEntityBuilder projectKpaTaskEntity = ProjectKpaTaskEntity.builder();

        projectKpaTaskEntity.taskName( request.taskName() );
        projectKpaTaskEntity.description( request.description() );

        return projectKpaTaskEntity.build();
    }

    @Override
    public ProjectKpaTaskResponse toResponse(ProjectKpaTaskEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long kpaEmployeeAssignmentId = null;
        Long id = null;
        String taskName = null;
        String description = null;
        TaskStatus status = null;
        Boolean active = null;

        kpaEmployeeAssignmentId = entityKpaEmployeeAssignmentId( entity );
        id = entity.getId();
        taskName = entity.getTaskName();
        description = entity.getDescription();
        status = entity.getStatus();
        active = entity.getActive();

        ProjectKpaTaskResponse projectKpaTaskResponse = new ProjectKpaTaskResponse( id, kpaEmployeeAssignmentId, taskName, description, status, active );

        return projectKpaTaskResponse;
    }

    @Override
    public void updateEntity(ProjectKpaTaskUpdateRequest request, ProjectKpaTaskEntity entity) {
        if ( request == null ) {
            return;
        }

        if ( request.taskName() != null ) {
            entity.setTaskName( request.taskName() );
        }
        if ( request.description() != null ) {
            entity.setDescription( request.description() );
        }
        if ( request.status() != null ) {
            entity.setStatus( request.status() );
        }
    }

    private Long entityKpaEmployeeAssignmentId(ProjectKpaTaskEntity projectKpaTaskEntity) {
        ProjectKpaEmployeeEntity kpaEmployeeAssignment = projectKpaTaskEntity.getKpaEmployeeAssignment();
        if ( kpaEmployeeAssignment == null ) {
            return null;
        }
        return kpaEmployeeAssignment.getId();
    }
}
