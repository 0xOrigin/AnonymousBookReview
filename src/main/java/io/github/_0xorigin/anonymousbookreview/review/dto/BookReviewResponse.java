package io.github._0xorigin.anonymousbookreview.review.dto;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record BookReviewResponse(
    UUID id,
    Integer rating,
    String comment,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {}
