package io.github._0xorigin.anonymousbookreview.author.controller;

import io.github._0xorigin.anonymousbookreview.author.dto.AuthorRequest;
import io.github._0xorigin.anonymousbookreview.author.dto.AuthorResponse;
import io.github._0xorigin.anonymousbookreview.author.service.AuthorService;
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
public class AuthorControllerImpl implements AuthorController {

    private final AuthorService authorService;

    @Override
    public ResponseEntity<ApiResponse<List<AuthorResponse>>> getAllAuthors(HttpServletRequest httpServletRequest, Pageable pageable, @Valid ListAPIRequest listAPIRequest) {
        ApiResponse<List<AuthorResponse>> response = authorService.getAllAuthors(httpServletRequest, pageable, listAPIRequest);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiResponse<AuthorResponse>> getAuthorById(UUID id) {
        ApiResponse<AuthorResponse> response = authorService.getAuthorById(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiResponse<AuthorResponse>> createAuthor(@Valid AuthorRequest request) {
        ApiResponse<AuthorResponse> response = authorService.createAuthor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ApiResponse<AuthorResponse>> updateAuthor(UUID id, @Valid AuthorRequest request) {
        ApiResponse<AuthorResponse> response = authorService.updateAuthor(id, request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> deleteAuthor(UUID id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
