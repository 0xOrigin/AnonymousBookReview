package io.github._0xorigin.anonymousbookreview.author.dto;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record AuthorResponse(
    UUID id,
    String name,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {}
