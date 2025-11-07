package io.github._0xorigin.anonymousbookreview.review.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record BookReviewRequest(
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    Integer rating,

    @Size(max = 2000, message = "Comment must not exceed 2000 characters")
    String comment
) {}
