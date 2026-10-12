package com.pt.PerformanceTracker.performance.kpa.mapper;

import com.pt.PerformanceTracker.performance.kpa.dto.ProjectKpaCreateRequest;
import com.pt.PerformanceTracker.performance.kpa.dto.ProjectKpaResponse;
import com.pt.PerformanceTracker.performance.kpa.dto.ProjectKpaUpdateRequest;
import com.pt.PerformanceTracker.performance.kpa.entity.ProjectKpaEntity;
import com.pt.PerformanceTracker.performance.kra.entity.ProjectKraEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-12T16:26:04+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class ProjectKpaMapperImpl implements ProjectKpaMapper {

    @Override
    public ProjectKpaResponse toResponse(ProjectKpaEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long projectKraId = null;
        String projectKraName = null;
        Long id = null;
        String kpaName = null;
        String description = null;
        Boolean active = null;

        projectKraId = entityProjectKraId( entity );
        projectKraName = entityProjectKraKraName( entity );
        id = entity.getId();
        kpaName = entity.getKpaName();
        description = entity.getDescription();
        active = entity.getActive();

        ProjectKpaResponse projectKpaResponse = new ProjectKpaResponse( id, projectKraId, projectKraName, kpaName, description, active );

        return projectKpaResponse;
    }

    @Override
    public ProjectKpaEntity toEntity(ProjectKpaCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        ProjectKpaEntity.ProjectKpaEntityBuilder projectKpaEntity = ProjectKpaEntity.builder();

        projectKpaEntity.kpaName( request.kpaName() );
        projectKpaEntity.description( request.description() );

        return projectKpaEntity.build();
    }

    @Override
    public void updateEntity(ProjectKpaUpdateRequest request, ProjectKpaEntity entity) {
        if ( request == null ) {
            return;
        }

        if ( request.kpaName() != null ) {
            entity.setKpaName( request.kpaName() );
        }
        if ( request.description() != null ) {
            entity.setDescription( request.description() );
        }
    }

    private Long entityProjectKraId(ProjectKpaEntity projectKpaEntity) {
        ProjectKraEntity projectKra = projectKpaEntity.getProjectKra();
        if ( projectKra == null ) {
            return null;
        }
        return projectKra.getId();
    }

    private String entityProjectKraKraName(ProjectKpaEntity projectKpaEntity) {
        ProjectKraEntity projectKra = projectKpaEntity.getProjectKra();
        if ( projectKra == null ) {
            return null;
        }
        return projectKra.getKraName();
    }
}
