package io.github._0xorigin.anonymousbookreview.book.dto;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record BookSummaryResponse(
    UUID id,
    String title,
    String isbn,
    OffsetDateTime publishedDate,
    Integer numberOfPages,
    BasicAuthorInfoResponse author,
    Double averageRating,
    Long totalReviews
) {
    public BookSummaryResponse withRatingFields(Double averageRating, Long totalReviews) {
        return new BookSummaryResponse(
            this.id,
            this.title,
            this.isbn,
            this.publishedDate,
            this.numberOfPages,
            this.author,
            averageRating,
            totalReviews
        );
    }
}
