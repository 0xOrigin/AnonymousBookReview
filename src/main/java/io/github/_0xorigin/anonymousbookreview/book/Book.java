package io.github._0xorigin.anonymousbookreview.book;

import io.github._0xorigin.anonymousbookreview.author.Author;
import io.github._0xorigin.anonymousbookreview.common.entity.BaseAuditEntity;
import io.github._0xorigin.anonymousbookreview.review.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "books", indexes = {
    @Index(name = "idx_book_id_unq", columnList = "id", unique = true),
    @Index(name = "idx_book_isbn_unq", columnList = "isbn", unique = true),
    @Index(name = "idx_book_createdat", columnList = "createdAt"),
    @Index(name = "idx_book_number_of_pages", columnList = "number_of_pages"),
    @Index(name = "idx_book_author_id", columnList = "author_id")
}, uniqueConstraints = {
    @UniqueConstraint(name = "uc_book_isbn", columnNames = {"isbn"})
})
public class Book extends BaseAuditEntity {
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "published_date", nullable = false)
    private OffsetDateTime publishedDate;

    @Column(name = "number_of_pages")
    private Integer numberOfPages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;
}
