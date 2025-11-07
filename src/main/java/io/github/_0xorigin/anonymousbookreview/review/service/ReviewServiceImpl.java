package io.github._0xorigin.anonymousbookreview.review.service;

import io.github._0xorigin.anonymousbookreview.book.Book;
import io.github._0xorigin.anonymousbookreview.book.service.BookService;
import io.github._0xorigin.anonymousbookreview.common.responses.ResponseFactory;
import io.github._0xorigin.anonymousbookreview.common.responses.apiresponse.ApiResponse;
import io.github._0xorigin.anonymousbookreview.review.dto.BookReviewRequest;
import io.github._0xorigin.anonymousbookreview.review.dto.BookReviewResponse;
import io.github._0xorigin.anonymousbookreview.review.mapper.ReviewMapper;
import io.github._0xorigin.anonymousbookreview.common.exceptions.ResourceNotFoundException;
import io.github._0xorigin.anonymousbookreview.review.Review;
import io.github._0xorigin.anonymousbookreview.review.ReviewRepository;
import io.github._0xorigin.anonymousbookreview.review.specification.ReviewSpecification;
import io.github._0xorigin.queryfilterbuilder.FilterContext;
import io.github._0xorigin.queryfilterbuilder.QueryFilterBuilder;
import io.github._0xorigin.queryfilterbuilder.SortContext;
import io.github._0xorigin.queryfilterbuilder.base.dtos.ListAPIRequest;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final BookService bookService;
    private final ReviewSpecification reviewSpecification;
    private final QueryFilterBuilder<Review> queryFilterBuilder;
    private final ResponseFactory responseFactory;
    private final EntityManager entityManager;

    @Override
    public ApiResponse<List<BookReviewResponse>> getAllReviews(
        HttpServletRequest httpServletRequest,
        UUID bookId,
        Pageable pageable,
        ListAPIRequest listAPIRequest
    ) {
        FilterContext<Review> filterContext = reviewSpecification.getFilterTemplate()
            .newSourceBuilder()
            .withQuerySource(httpServletRequest)
            .withBodySource(listAPIRequest.filters())
            .buildFilterContext();

        SortContext<Review> sortContext = reviewSpecification.getSortTemplate()
            .newSourceBuilder()
            .withQuerySource(httpServletRequest)
            .withBodySource(listAPIRequest.sorts())
            .buildSortContext();

        Specification<Review> specs = (root, cq, cb) -> cb.equal(root.get("book").get("id"), bookId);
        Specification<Review> filterSpecs = queryFilterBuilder.buildFilterSpecification(filterContext);
        Specification<Review> sortSpecs = queryFilterBuilder.buildSortSpecification(sortContext);

        Page<Review> reviews = reviewRepository.findAll(Specification.allOf(specs, filterSpecs, sortSpecs), pageable);
        return responseFactory.createSuccessResponse(reviews, reviews.map(reviewMapper::toResponse).toList());
    }

    @Override
    public ApiResponse<BookReviewResponse> getReviewById(UUID bookId, UUID id) {
        Review review = reviewRepository.findByBookIdAndId(bookId, id).orElseThrow(() -> reviewNotFoundException(bookId, id));
        return responseFactory.createSuccessResponse(reviewMapper.toResponse(review));
    }

    @Override
    @Transactional
    public ApiResponse<BookReviewResponse> createReview(UUID bookId, BookReviewRequest request) {
        Review review = reviewMapper.toEntity(request);
        Book bookRef = entityManager.getReference(Book.class, bookId);
        review.setBook(bookRef);
        Review savedReview = reviewRepository.save(review);
        return responseFactory.createSuccessResponse(reviewMapper.toResponse(savedReview));
    }

    private ResourceNotFoundException reviewNotFoundException(UUID bookId, UUID id) {
        return new ResourceNotFoundException("Review not found with ID: " + id + " for book ID: " + bookId);
    }
}
