package io.github._0xorigin.anonymousbookreview.book.service;

import io.github._0xorigin.anonymousbookreview.author.service.AuthorService;
import io.github._0xorigin.anonymousbookreview.book.Book;
import io.github._0xorigin.anonymousbookreview.book.dto.BookRatingAggregate;
import io.github._0xorigin.anonymousbookreview.book.BookRepository;
import io.github._0xorigin.anonymousbookreview.book.dto.BookRequest;
import io.github._0xorigin.anonymousbookreview.book.dto.BookResponse;
import io.github._0xorigin.anonymousbookreview.book.dto.BookSummaryResponse;
import io.github._0xorigin.anonymousbookreview.book.mapper.BookMapper;
import io.github._0xorigin.anonymousbookreview.book.projection.BookSummaryView;
import io.github._0xorigin.anonymousbookreview.book.projection.BookView;
import io.github._0xorigin.anonymousbookreview.book.specification.BookSpecification;
import io.github._0xorigin.anonymousbookreview.common.exceptions.BusinessLogicException;
import io.github._0xorigin.anonymousbookreview.common.exceptions.ResourceNotFoundException;
import io.github._0xorigin.anonymousbookreview.common.responses.ResponseFactory;
import io.github._0xorigin.anonymousbookreview.common.responses.apiresponse.ApiResponse;
import io.github._0xorigin.queryfilterbuilder.FilterContext;
import io.github._0xorigin.queryfilterbuilder.QueryFilterBuilder;
import io.github._0xorigin.queryfilterbuilder.SortContext;
import io.github._0xorigin.queryfilterbuilder.base.dtos.ListAPIRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorService authorService;
    private final BookSpecification bookSpecification;
    private final QueryFilterBuilder<Book> queryFilterBuilder;
    private final ResponseFactory responseFactory;

    @Override
    public ApiResponse<List<BookSummaryResponse>> getAllBooks(HttpServletRequest httpServletRequest, Pageable pageable, ListAPIRequest listAPIRequest) {
        FilterContext<Book> filterContext = bookSpecification.getFilterTemplate()
            .newSourceBuilder()
            .withQuerySource(httpServletRequest)
            .withBodySource(listAPIRequest.filters())
            .buildFilterContext();

        SortContext<Book> sortContext = bookSpecification.getSortTemplate()
            .newSourceBuilder()
            .withQuerySource(httpServletRequest)
            .withBodySource(listAPIRequest.sorts())
            .buildSortContext();

        Specification<Book> filterSpecs = queryFilterBuilder.buildFilterSpecification(filterContext);
        Specification<Book> sortSpecs = queryFilterBuilder.buildSortSpecification(sortContext);

        Page<BookSummaryView> books = bookRepository.findBy(
            Specification.allOf(filterSpecs, sortSpecs),
            query -> query.as(BookSummaryView.class).page(pageable)
        );

        List<UUID> bookIds = books.stream().map(BookSummaryView::getId).toList();
        Map<UUID, BookRatingAggregate> aggregatesById = getAggregatesById(bookIds);

        List<BookSummaryResponse> responses = books.stream().map(book -> {
            BookSummaryResponse response = bookMapper.toSummaryResponse(book);
            BookRatingAggregate agg = aggregatesById.get(book.getId());
            long totalReviews = 0L;
            Double averageRating = null;
            if (agg != null) {
                totalReviews = agg.totalReviews() == null ? 0L : agg.totalReviews();
                averageRating = agg.averageRating();
            }
            response = response.withRatingFields(averageRating, totalReviews);
            return response;
        }).toList();

        return responseFactory.createSuccessResponse(books, responses);
    }

    @Override
    public ApiResponse<BookResponse> getBookById(UUID id) {
        Specification<Book> specs = (root, cq, cb) -> cb.equal(root.get("id"), id);
        BookView book = bookRepository.findBy(specs, query -> query.as(BookView.class).first())
            .orElseThrow(() -> bookNotFoundException(id));

        Map<UUID, BookRatingAggregate> aggregatesById = getAggregatesById(List.of(book.getId()));
        BookResponse response = bookMapper.toResponse(book);
        BookRatingAggregate agg = aggregatesById.get(book.getId());
        long totalReviews = 0L;
        Double averageRating = null;
        if (agg != null) {
            totalReviews = agg.totalReviews() == null ? 0L : agg.totalReviews();
            averageRating = agg.averageRating();
        }
        response = response.withRatingFields(averageRating, totalReviews);

        return responseFactory.createSuccessResponse(response);
    }

    @Override
    @Transactional
    public ApiResponse<BookResponse> createBook(BookRequest request) {
        authorService.throwExceptionIfNotExistById(request.authorId());

        if (bookRepository.existsByIsbn(request.isbn()))
            throw new BusinessLogicException("Book with ISBN " + request.isbn() + " already exists");

        Book book = bookMapper.toEntity(request);
        Book savedBook = bookRepository.save(book);
        return getBookById(savedBook.getId());
    }

    @Override
    @Transactional
    public ApiResponse<BookResponse> updateBook(UUID id, BookRequest request) {
        Book book = bookRepository.findById(id).orElseThrow(() -> bookNotFoundException(id));

        if (!book.getIsbn().equals(request.isbn()) && bookRepository.existsByIsbn(request.isbn()))
            throw new BusinessLogicException("Book with ISBN " + request.isbn() + " already exists");

        if (!book.getAuthor().getId().equals(request.authorId()))
            authorService.throwExceptionIfNotExistById(id);

        bookMapper.updateEntityFromRequest(request, book);
        Book savedBook = bookRepository.save(book);

        return getBookById(savedBook.getId());
    }

    @Override
    @Transactional
    public void deleteBook(UUID id) {
        throwExceptionIfNotExistById(id);
        bookRepository.deleteById(id);
    }

    @Override
    public void throwExceptionIfNotExistById(UUID id) {
        if (!bookRepository.existsById(id))
            throw bookNotFoundException(id);
    }

    private ResourceNotFoundException bookNotFoundException(UUID id) {
        return new ResourceNotFoundException("Book not found with ID: " + id);
    }

    private Map<UUID, BookRatingAggregate> getAggregatesById(List<UUID> bookIds) {
        List<BookRatingAggregate> aggregates = bookIds.isEmpty() ? List.of() : bookRepository.findRatingAggregatesByBookIds(bookIds);
        return aggregates.stream().collect(Collectors.toMap(BookRatingAggregate::bookId, a -> a));
    }
}
