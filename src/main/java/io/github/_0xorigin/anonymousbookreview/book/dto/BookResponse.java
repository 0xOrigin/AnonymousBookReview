package io.github._0xorigin.anonymousbookreview.book.dto;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record BookResponse(
    UUID id,
    String title,
    String isbn,
    OffsetDateTime publishedDate,
    Integer numberOfPages,
    BasicAuthorInfoResponse author,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt,
    Double averageRating,
    Long totalReviews
) {
    public BookResponse withRatingFields(Double averageRating, Long totalReviews) {
        return new BookResponse(
            this.id,
            this.title,
            this.isbn,
            this.publishedDate,
            this.numberOfPages,
            this.author,
            this.createdAt,
            this.updatedAt,
            averageRating,
            totalReviews
        );
    }
}
