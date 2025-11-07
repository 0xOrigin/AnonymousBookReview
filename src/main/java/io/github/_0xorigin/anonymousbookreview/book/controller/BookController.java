package io.github._0xorigin.anonymousbookreview.book.controller;

import io.github._0xorigin.anonymousbookreview.book.dto.BookRequest;
import io.github._0xorigin.anonymousbookreview.book.dto.BookResponse;
import io.github._0xorigin.anonymousbookreview.book.dto.BookSummaryResponse;
import io.github._0xorigin.anonymousbookreview.common.responses.apiresponse.ApiResponse;
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

@Tag(name = "Books", description = "Book management endpoints")
@RequestMapping("/api/books")
public interface BookController {

    @PostMapping(value = "/list", headers = "API-VERSION=1")
    @Operation(summary = "Get all books with pagination and rating summary")
    ResponseEntity<ApiResponse<List<BookSummaryResponse>>> getAllBooks(
        HttpServletRequest httpServletRequest,
        @Parameter(description = "Pagination parameters")
        Pageable pageable,
        @Parameter(description = "List API Body")
        @Valid @RequestBody(required = false) ListAPIRequest listAPIRequest
    );

    @GetMapping(value = "/{id}", headers = "API-VERSION=1")
    @Operation(summary = "Get book by ID with full details")
    ResponseEntity<ApiResponse<BookResponse>> getBookById(@Parameter(description = "Book ID") @PathVariable UUID id);

    @PostMapping(headers = "API-VERSION=1")
    @Operation(summary = "Create a new book")
    ResponseEntity<ApiResponse<BookResponse>> createBook(@Valid @RequestBody BookRequest request);

    @PutMapping(value = "/{id}", headers = "API-VERSION=1")
    @Operation(summary = "Update an existing book")
    ResponseEntity<ApiResponse<BookResponse>> updateBook(
            @Parameter(description = "Book ID") @PathVariable UUID id,
            @Valid @RequestBody BookRequest request);

    @DeleteMapping(value = "/{id}", headers = "API-VERSION=1")
    @Operation(summary = "Delete a book")
    ResponseEntity<Void> deleteBook(@Parameter(description = "Book ID") @PathVariable UUID id);
}
