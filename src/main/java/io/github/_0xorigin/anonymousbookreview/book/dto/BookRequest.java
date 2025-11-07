package io.github._0xorigin.anonymousbookreview.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record BookRequest(
    @NotBlank(message = "Book title is required")
    @Size(min = 1, max = 500, message = "Book title must be between 1 and 500 characters")
    String title,

    @NotBlank(message = "ISBN is required")
    @Size(min = 10, max = 17, message = "ISBN must be between 10 and 17 characters")
    String isbn,

    @NotNull(message = "Published date is required")
    OffsetDateTime publishedDate,

    @NotNull(message = "Number of pages is required")
    @Positive(message = "Number of pages must be positive")
    Integer numberOfPages,

    @NotNull(message = "Author ID is required")
    UUID authorId
) {}
