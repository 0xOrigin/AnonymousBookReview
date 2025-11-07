package io.github._0xorigin.anonymousbookreview.review.controller;

import io.github._0xorigin.anonymousbookreview.common.responses.apiresponse.ApiResponse;
import io.github._0xorigin.anonymousbookreview.review.dto.BookReviewRequest;
import io.github._0xorigin.anonymousbookreview.review.dto.BookReviewResponse;
import io.github._0xorigin.anonymousbookreview.review.service.ReviewService;
import io.github._0xorigin.queryfilterbuilder.base.dtos.ListAPIRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookReviewControllerImpl implements BookReviewController {
    private final ReviewService reviewService;

    @Override
    public ResponseEntity<ApiResponse<List<BookReviewResponse>>> getAllReviews(
        HttpServletRequest httpServletRequest,
        UUID bookId,
        Pageable pageable,
        @Valid ListAPIRequest listAPIRequest
    ) {
        ApiResponse<List<BookReviewResponse>> response = reviewService.getAllReviews(httpServletRequest, bookId, pageable, listAPIRequest);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiResponse<BookReviewResponse>> getReviewById(UUID bookId, UUID id) {
        ApiResponse<BookReviewResponse> response = reviewService.getReviewById(bookId, id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiResponse<BookReviewResponse>> createReview(UUID bookId, @Valid BookReviewRequest request) {
        ApiResponse<BookReviewResponse> response = reviewService.createReview(bookId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
