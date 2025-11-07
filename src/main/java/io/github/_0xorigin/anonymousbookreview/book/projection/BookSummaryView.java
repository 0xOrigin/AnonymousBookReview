package io.github._0xorigin.anonymousbookreview.book.projection;


import java.time.OffsetDateTime;
import java.util.UUID;

public interface BookSummaryView {
    UUID getId();
    String getTitle();
    String getIsbn();
    OffsetDateTime getPublishedDate();
    Integer getNumberOfPages();

    UUID getAuthorId();
    String getAuthorName();
}
