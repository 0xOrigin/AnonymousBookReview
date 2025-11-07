package io.github._0xorigin.anonymousbookreview.common.entity;

import io.github._0xorigin.anonymousbookreview.common.uuid.UUIDv7;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    @UUIDv7
    protected UUID id;
}
