package io.github._0xorigin.anonymousbookreview.author.service;

import io.github._0xorigin.anonymousbookreview.author.dto.AuthorRequest;
import io.github._0xorigin.anonymousbookreview.author.dto.AuthorResponse;
import io.github._0xorigin.anonymousbookreview.common.responses.apiresponse.ApiResponse;
import io.github._0xorigin.queryfilterbuilder.base.dtos.ListAPIRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface AuthorService {
    ApiResponse<List<AuthorResponse>> getAllAuthors(HttpServletRequest httpServletRequest, Pageable pageable, ListAPIRequest listAPIRequest);

    ApiResponse<AuthorResponse> getAuthorById(UUID id);

    ApiResponse<AuthorResponse> createAuthor(AuthorRequest request);

    ApiResponse<AuthorResponse> updateAuthor(UUID id, AuthorRequest request);

    void deleteAuthor(UUID id);

    void throwExceptionIfNotExistById(UUID id);
}
