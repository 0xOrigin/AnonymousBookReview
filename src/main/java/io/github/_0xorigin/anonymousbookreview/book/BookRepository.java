package io.github._0xorigin.anonymousbookreview.book;

import io.github._0xorigin.anonymousbookreview.book.dto.BookRatingAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID>, JpaSpecificationExecutor<Book> {

    boolean existsByIsbn(String isbn);

    @Query("SELECT new io.github._0xorigin.anonymousbookreview.book.dto.BookRatingAggregate(b.id, AVG(r.rating), COUNT(r)) " +
           "FROM Book b LEFT JOIN b.reviews r WHERE b.id IN :ids GROUP BY b.id")
    List<BookRatingAggregate> findRatingAggregatesByBookIds(@Param("ids") List<UUID> ids);
}