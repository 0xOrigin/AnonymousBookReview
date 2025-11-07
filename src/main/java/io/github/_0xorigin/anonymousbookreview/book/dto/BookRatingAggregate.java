package io.github._0xorigin.anonymousbookreview.book.dto;

import java.util.UUID;

public record BookRatingAggregate (
    UUID bookId,
    Double averageRating,
    Long totalReviews
) {}
