package io.github._0xorigin.anonymousbookreview.book.projection;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface BookView {
    UUID getId();
    String getTitle();
    String getIsbn();
    OffsetDateTime getPublishedDate();
    Integer getNumberOfPages();
    OffsetDateTime getCreatedAt();
    OffsetDateTime getUpdatedAt();

    UUID getAuthorId();
    String getAuthorName();
}
