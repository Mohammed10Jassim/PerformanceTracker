package com.pt.PerformanceTracker.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Generated;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseEntity {
   @Id
   @GeneratedValue(
      strategy = GenerationType.IDENTITY
   )
   private Long id;
   @CreatedDate
   @Column(
      nullable = false,
      updatable = false
   )
   private LocalDateTime createdAt;
   @LastModifiedDate
   private LocalDateTime updatedAt;
   @CreatedBy
   @Column(
      updatable = false
   )
   private String createdBy;
   @LastModifiedBy
   private String updatedBy;
   @Column(
      name = "is_active",
      nullable = false
   )
   private Boolean active = true;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public LocalDateTime getCreatedAt() {
      return this.createdAt;
   }

   @Generated
   public LocalDateTime getUpdatedAt() {
      return this.updatedAt;
   }

   @Generated
   public String getCreatedBy() {
      return this.createdBy;
   }

   @Generated
   public String getUpdatedBy() {
      return this.updatedBy;
   }

   @Generated
   public Boolean getActive() {
      return this.active;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setCreatedAt(final LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }

   @Generated
   public void setUpdatedAt(final LocalDateTime updatedAt) {
      this.updatedAt = updatedAt;
   }

   @Generated
   public void setCreatedBy(final String createdBy) {
      this.createdBy = createdBy;
   }

   @Generated
   public void setUpdatedBy(final String updatedBy) {
      this.updatedBy = updatedBy;
   }

   @Generated
   public void setActive(final Boolean active) {
      this.active = active;
   }
}
