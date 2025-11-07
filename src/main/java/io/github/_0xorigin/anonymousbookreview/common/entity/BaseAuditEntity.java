package io.github._0xorigin.anonymousbookreview.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseAuditEntity extends BaseEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    protected OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false, nullable = true)
    protected OffsetDateTime updatedAt;
}
