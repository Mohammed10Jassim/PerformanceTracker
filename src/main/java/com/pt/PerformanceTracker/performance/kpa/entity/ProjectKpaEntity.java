package com.pt.PerformanceTracker.performance.kpa.entity;

import com.pt.PerformanceTracker.common.entity.BaseEntity;
import com.pt.PerformanceTracker.performance.kra.entity.ProjectKraEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Generated;

@Entity
@Table(
   name = "project_kpas",
   uniqueConstraints = {@UniqueConstraint(
      name = "uk_project_kra_kpa_name",
      columnNames = {"project_kra_id", "kpa_name"}
   )},
   indexes = {@Index(
      name = "idx_project_kpa_project_kra",
      columnList = "project_kra_id"
   ), @Index(
      name = "idx_project_kpa_name",
      columnList = "kpa_name"
   ), @Index(
      name = "idx_project_kpa_active",
      columnList = "is_active"
   )}
)
public class ProjectKpaEntity extends BaseEntity {
   @ManyToOne(
      fetch = FetchType.LAZY,
      optional = false
   )
   @JoinColumn(
      name = "project_kra_id",
      nullable = false,
      foreignKey = @ForeignKey(
         name = "fk_project_kpa_project_kra"
      )
   )
   private ProjectKraEntity projectKra;
   @Column(
      name = "kpa_name",
      nullable = false,
      length = 150
   )
   private String kpaName;
   @Column(
      name = "description",
      length = 500
   )
   private String description;

   @Generated
   public static ProjectKpaEntity.ProjectKpaEntityBuilder builder() {
      return new ProjectKpaEntity.ProjectKpaEntityBuilder();
   }

   @Generated
   public ProjectKraEntity getProjectKra() {
      return this.projectKra;
   }

   @Generated
   public String getKpaName() {
      return this.kpaName;
   }

   @Generated
   public String getDescription() {
      return this.description;
   }

   @Generated
   public void setProjectKra(final ProjectKraEntity projectKra) {
      this.projectKra = projectKra;
   }

   @Generated
   public void setKpaName(final String kpaName) {
      this.kpaName = kpaName;
   }

   @Generated
   public void setDescription(final String description) {
      this.description = description;
   }

   @Generated
   public ProjectKpaEntity() {
   }

   @Generated
   public ProjectKpaEntity(final ProjectKraEntity projectKra, final String kpaName, final String description) {
      this.projectKra = projectKra;
      this.kpaName = kpaName;
      this.description = description;
   }

   @Generated
   public static class ProjectKpaEntityBuilder {
      @Generated
      private ProjectKraEntity projectKra;
      @Generated
      private String kpaName;
      @Generated
      private String description;

      @Generated
      ProjectKpaEntityBuilder() {
      }

      @Generated
      public ProjectKpaEntity.ProjectKpaEntityBuilder projectKra(final ProjectKraEntity projectKra) {
         this.projectKra = projectKra;
         return this;
      }

      @Generated
      public ProjectKpaEntity.ProjectKpaEntityBuilder kpaName(final String kpaName) {
         this.kpaName = kpaName;
         return this;
      }

      @Generated
      public ProjectKpaEntity.ProjectKpaEntityBuilder description(final String description) {
         this.description = description;
         return this;
      }

      @Generated
      public ProjectKpaEntity build() {
         return new ProjectKpaEntity(this.projectKra, this.kpaName, this.description);
      }

      @Generated
      @Override
      public String toString() {
         return "ProjectKpaEntity.ProjectKpaEntityBuilder(projectKra="
            + this.projectKra
            + ", kpaName="
            + this.kpaName
            + ", description="
            + this.description
            + ")";
      }
   }
}
