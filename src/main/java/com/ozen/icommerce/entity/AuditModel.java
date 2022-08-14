package com.ozen.icommerce.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ozen.icommerce.entity.user.UserEntity;
import com.ozen.icommerce.enums.Status;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@MappedSuperclass
@FieldNameConstants
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditModel {
	  @Temporal(TemporalType.TIMESTAMP)
	  @Column(
	      name = "created_at",
	      nullable = false,
	      updatable = false,
	      columnDefinition = "timestamp with time zone")
	  @CreatedDate
	  private Date createdAt;

	  @Temporal(TemporalType.TIMESTAMP)
	  @Column(name = "updated_at", nullable = false, columnDefinition = "timestamp with time zone")
	  @LastModifiedDate
	  private Date updatedAt;

	  @ManyToOne
	  @JoinColumn(name = "created_by")
	  @CreatedBy
	  private UserEntity createdBy;

	  @ManyToOne
	  @JoinColumn(name = "updated_by")
	  @LastModifiedBy
	  private UserEntity updatedBy;

	  @Column(name = "status", columnDefinition = "int2 default 1")
	  @Enumerated(EnumType.ORDINAL)
	  private Status status = Status.ENABLED;
}
