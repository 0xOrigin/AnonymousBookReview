package io.github._0xorigin.anonymousbookreview.book.controller;

import io.github._0xorigin.anonymousbookreview.book.dto.BookRequest;
import io.github._0xorigin.anonymousbookreview.book.dto.BookResponse;
import io.github._0xorigin.anonymousbookreview.book.dto.BookSummaryResponse;
import io.github._0xorigin.anonymousbookreview.book.service.BookService;
import io.github._0xorigin.anonymousbookreview.common.responses.apiresponse.ApiResponse;
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
public class BookControllerImpl implements BookController {

    private final BookService bookService;

    @Override
    public ResponseEntity<ApiResponse<List<BookSummaryResponse>>> getAllBooks(HttpServletRequest httpServletRequest, Pageable pageable, ListAPIRequest listAPIRequest) {
        ApiResponse<List<BookSummaryResponse>> response = bookService.getAllBooks(httpServletRequest, pageable, listAPIRequest);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiResponse<BookResponse>> getBookById(UUID id) {
        ApiResponse<BookResponse> response = bookService.getBookById(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiResponse<BookResponse>> createBook(@Valid BookRequest request) {
        ApiResponse<BookResponse> response = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(UUID id, @Valid BookRequest request) {
        ApiResponse<BookResponse> response = bookService.updateBook(id, request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> deleteBook(UUID id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
