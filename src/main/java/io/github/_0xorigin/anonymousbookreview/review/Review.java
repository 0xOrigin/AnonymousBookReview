package io.github._0xorigin.anonymousbookreview.review;

import io.github._0xorigin.anonymousbookreview.book.Book;
import io.github._0xorigin.anonymousbookreview.common.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "reviews", indexes = {
    @Index(name = "idx_review_id_unq", columnList = "id", unique = true),
    @Index(name = "idx_review_id_book_id", columnList = "book_id, id"),
    @Index(name = "idx_review_book_id_rating", columnList = "book_id, rating"),
    @Index(name = "idx_review_book_id_createdat", columnList = "book_id, createdAt")
})
public class Review extends BaseAuditEntity {
    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}
