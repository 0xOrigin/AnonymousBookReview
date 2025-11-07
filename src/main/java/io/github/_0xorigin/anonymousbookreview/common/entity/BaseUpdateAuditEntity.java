package io.github._0xorigin.anonymousbookreview.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseUpdateAuditEntity extends BaseEntity {
    @LastModifiedDate
    @Column(insertable = false, nullable = true)
    protected OffsetDateTime updatedAt;
}
