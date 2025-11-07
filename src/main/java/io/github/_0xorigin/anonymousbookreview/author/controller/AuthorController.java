package io.github._0xorigin.anonymousbookreview.author.controller;

import io.github._0xorigin.anonymousbookreview.author.dto.AuthorRequest;
import io.github._0xorigin.anonymousbookreview.author.dto.AuthorResponse;
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

@Tag(name = "Authors", description = "Author management endpoints")
@RequestMapping("/api/authors")
public interface AuthorController {
    @PostMapping(value = "/list", headers = "API-VERSION=1")
    @Operation(summary = "Get all authors with pagination")
    ResponseEntity<ApiResponse<List<AuthorResponse>>> getAllAuthors(
        HttpServletRequest httpServletRequest,
        @Parameter(description = "Pagination parameters")
        Pageable pageable,
        @Parameter(description = "List API Body")
        @Valid @RequestBody(required = false) ListAPIRequest listAPIRequest
    );

    @GetMapping(value = "/{id}", headers = "API-VERSION=1")
    @Operation(summary = "Get author by ID")
    ResponseEntity<ApiResponse<AuthorResponse>> getAuthorById(@Parameter(description = "Author ID") @PathVariable UUID id);

    @PostMapping(headers = "API-VERSION=1")
    @Operation(summary = "Create a new author")
    ResponseEntity<ApiResponse<AuthorResponse>> createAuthor(@Valid @RequestBody AuthorRequest request);

    @PutMapping(value = "/{id}", headers = "API-VERSION=1")
    @Operation(summary = "Update an existing author")
    ResponseEntity<ApiResponse<AuthorResponse>> updateAuthor(
        @Parameter(description = "Author ID") @PathVariable UUID id,
        @Valid @RequestBody AuthorRequest request
    );

    @DeleteMapping(value = "/{id}", headers = "API-VERSION=1")
    @Operation(summary = "Delete an author")
    ResponseEntity<Void> deleteAuthor(@Parameter(description = "Author ID") @PathVariable UUID id);
}
