package io.github._0xorigin.anonymousbookreview.review.controller;

import io.github._0xorigin.anonymousbookreview.common.responses.apiresponse.ApiResponse;
import io.github._0xorigin.anonymousbookreview.review.dto.BookReviewRequest;
import io.github._0xorigin.anonymousbookreview.review.dto.BookReviewResponse;
import io.github._0xorigin.queryfilterbuilder.base.dtos.ListAPIRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@Tag(name = "BookReviews", description = "Anonymous book review endpoints")
@RequestMapping("/api/books/{bookId}/reviews")
public interface BookReviewController {
    @PostMapping(value = "/list", headers = "API-VERSION=1")
    @Operation(summary = "Get all reviews with pagination")
    ResponseEntity<ApiResponse<List<BookReviewResponse>>> getAllReviews(
        HttpServletRequest httpServletRequest,
        @Parameter(description = "Book ID") @PathVariable UUID bookId,
        @Parameter(description = "Pagination parameters")
        Pageable pageable,
        @Parameter(description = "List API Body")
        @Valid @RequestBody(required = false) ListAPIRequest listAPIRequest
    );

    @GetMapping("/{id}")
    @Operation(summary = "Get review by ID")
    ResponseEntity<ApiResponse<BookReviewResponse>> getReviewById(
        @Parameter(description = "Book ID") @PathVariable UUID bookId,
        @Parameter(description = "Review ID") @PathVariable UUID id
    );

    @PostMapping
    @Operation(summary = "Create an anonymous review for a book")
    ResponseEntity<ApiResponse<BookReviewResponse>> createReview(
        @Parameter(description = "Book ID") @PathVariable UUID bookId,
        @Valid @RequestBody BookReviewRequest request
    );
}
