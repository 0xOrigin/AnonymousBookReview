package io.github._0xorigin.anonymousbookreview.common.seeders;

import io.github._0xorigin.anonymousbookreview.author.Author;
import io.github._0xorigin.anonymousbookreview.author.AuthorRepository;
import io.github._0xorigin.anonymousbookreview.book.Book;
import io.github._0xorigin.anonymousbookreview.book.BookRepository;
import io.github._0xorigin.anonymousbookreview.review.Review;
import io.github._0xorigin.anonymousbookreview.review.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Component
@ConditionalOnProperty(name = "app.seed.run", havingValue = "true", matchIfMissing = false)
public class DataSeeder implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    public DataSeeder(AuthorRepository authorRepository, BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Starting data seeding for anonymous book review system...");

        // Create authors
        Author author1 = createAuthor("George Orwell");
        Author author2 = createAuthor("J.K. Rowling");
        Author author3 = createAuthor("J.R.R. Tolkien");

        // Create books
        Book book1 = createBook("1984", "978-0451524935", 1949, 328, author1);
        Book book2 = createBook("Animal Farm", "978-0451526342", 1945, 95, author1);
        Book book3 = createBook("Harry Potter and the Philosopher's Stone", "978-0747532699", 1997, 223, author2);
        Book book4 = createBook("The Hobbit", "978-0547928227", 1937, 304, author3);
        Book book5 = createBook("The Lord of the Rings", "978-0544003415", 1954, 1216, author3);

        // Create reviews
        createReview(5, "A masterpiece of dystopian fiction. Absolutely brilliant!", book1);
        createReview(5, "Timeless classic that remains relevant today.", book1);
        createReview(4, "Powerful allegory about political systems.", book2);
        createReview(5, "Clever and thought-provoking.", book2);
        createReview(5, "Magical start to an incredible series!", book3);
        createReview(4, "Perfect introduction to the wizarding world.", book3);
        createReview(5, "The adventure that started it all.", book4);
        createReview(5, "A delightful tale for all ages.", book4);
        createReview(5, "Epic fantasy at its finest.", book5);
        createReview(4, "A monumental work of literature.", book5);
        createReview(3, "Not as good as I expected.", book1);
        createReview(2, "Waste of money.", book2);
        createReview(1, "Terrible book.", book3);
        createReview(4, "Good book.", book4);
        createReview(5, "Excellent book.", book5);

        log.info("Data seeding completed successfully!");
    }

    private Author createAuthor(String name) {
        Author author = new Author();
        author.setName(name);

        Author saved = authorRepository.save(author);
        log.debug("Created author: {} with ID: {}", name, saved.getId());
        return saved;
    }

    private Book createBook(String title, String isbn, int publishedYear, int pages, Author author) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPublishedDate(OffsetDateTime.of(publishedYear, 1, 1, 0, 0, 0, 0, OffsetDateTime.now().getOffset()));
        book.setNumberOfPages(pages);
        book.setAuthor(author);

        Book saved = bookRepository.save(book);
        log.debug("Created book: {} with ID: {}", title, saved.getId());
        return saved;
    }

    private void createReview(int rating, String comment, Book book) {
        Review review = new Review();
        review.setRating(rating);
        review.setComment(comment);
        review.setBook(book);

        Review saved = reviewRepository.save(review);
        log.debug("Created review for book {}: {} stars - {}", book.getTitle(), saved.getRating(), comment.substring(0, Math.min(50, comment.length())) + "...");
    }
}
