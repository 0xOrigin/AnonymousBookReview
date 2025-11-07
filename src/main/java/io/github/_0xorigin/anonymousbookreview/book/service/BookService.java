package io.github._0xorigin.anonymousbookreview.book.service;

import io.github._0xorigin.anonymousbookreview.book.dto.BookRequest;
import io.github._0xorigin.anonymousbookreview.book.dto.BookResponse;
import io.github._0xorigin.anonymousbookreview.book.dto.BookSummaryResponse;
import io.github._0xorigin.anonymousbookreview.common.responses.apiresponse.ApiResponse;
import io.github._0xorigin.queryfilterbuilder.base.dtos.ListAPIRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BookService {
    ApiResponse<List<BookSummaryResponse>> getAllBooks(HttpServletRequest httpServletRequest, Pageable pageable, ListAPIRequest listAPIRequest);

    ApiResponse<BookResponse> getBookById(UUID id);

    ApiResponse<BookResponse> createBook(BookRequest request);

    ApiResponse<BookResponse> updateBook(UUID id, BookRequest request);

    void deleteBook(UUID id);

    void throwExceptionIfNotExistById(UUID id);
}
