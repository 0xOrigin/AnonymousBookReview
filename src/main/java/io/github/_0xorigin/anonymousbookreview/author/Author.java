package io.github._0xorigin.anonymousbookreview.author;

import io.github._0xorigin.anonymousbookreview.book.Book;
import io.github._0xorigin.anonymousbookreview.common.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "authors", indexes = {
    @Index(name = "idx_author_id_unq", columnList = "id", unique = true),
    @Index(name = "idx_author_name", columnList = "name"),
    @Index(name = "idx_author_createdat", columnList = "createdAt"),
    @Index(name = "idx_author_updatedat", columnList = "updatedAt")
})
public class Author extends BaseAuditEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books;
}
