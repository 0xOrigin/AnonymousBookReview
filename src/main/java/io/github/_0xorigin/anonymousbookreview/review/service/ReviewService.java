package io.github._0xorigin.anonymousbookreview.review.service;

import io.github._0xorigin.anonymousbookreview.common.responses.apiresponse.ApiResponse;
import io.github._0xorigin.anonymousbookreview.review.dto.BookReviewResponse;
import io.github._0xorigin.anonymousbookreview.review.dto.BookReviewRequest;
import io.github._0xorigin.queryfilterbuilder.base.dtos.ListAPIRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    ApiResponse<List<BookReviewResponse>> getAllReviews(
        HttpServletRequest httpServletRequest,
        UUID bookId,
        Pageable pageable,
        ListAPIRequest listAPIRequest
    );

    ApiResponse<BookReviewResponse> createReview(UUID bookId, BookReviewRequest request);

    ApiResponse<BookReviewResponse> getReviewById(UUID bookId, UUID id);
}
