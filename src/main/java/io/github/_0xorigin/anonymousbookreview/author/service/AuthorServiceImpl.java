package io.github._0xorigin.anonymousbookreview.author.service;

import io.github._0xorigin.anonymousbookreview.author.Author;
import io.github._0xorigin.anonymousbookreview.author.AuthorRepository;
import io.github._0xorigin.anonymousbookreview.author.dto.AuthorRequest;
import io.github._0xorigin.anonymousbookreview.author.dto.AuthorResponse;
import io.github._0xorigin.anonymousbookreview.author.mapper.AuthorMapper;
import io.github._0xorigin.anonymousbookreview.author.specification.AuthorSpecification;
import io.github._0xorigin.anonymousbookreview.common.exceptions.ResourceNotFoundException;
import io.github._0xorigin.anonymousbookreview.common.responses.ResponseFactory;
import io.github._0xorigin.anonymousbookreview.common.responses.apiresponse.ApiResponse;
import io.github._0xorigin.queryfilterbuilder.FilterContext;
import io.github._0xorigin.queryfilterbuilder.QueryFilterBuilder;
import io.github._0xorigin.queryfilterbuilder.SortContext;
import io.github._0xorigin.queryfilterbuilder.base.dtos.ListAPIRequest;
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
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final QueryFilterBuilder<Author> queryFilterBuilder;
    private final AuthorSpecification authorSpecification;
    private final ResponseFactory responseFactory;

    @Override
    public ApiResponse<List<AuthorResponse>> getAllAuthors(HttpServletRequest httpServletRequest, Pageable pageable, ListAPIRequest listAPIRequest) {
        FilterContext<Author> filterContext = authorSpecification.getFilterTemplate()
            .newSourceBuilder()
            .withQuerySource(httpServletRequest)
            .withBodySource(listAPIRequest.filters())
            .buildFilterContext();

        SortContext<Author> sortContext = authorSpecification.getSortTemplate()
            .newSourceBuilder()
            .withQuerySource(httpServletRequest)
            .withBodySource(listAPIRequest.sorts())
            .buildSortContext();

        Specification<Author> filterSpecs = queryFilterBuilder.buildFilterSpecification(filterContext);
        Specification<Author> sortSpecs = queryFilterBuilder.buildSortSpecification(sortContext);

        Page<Author> authors = authorRepository.findAll(filterSpecs.and(sortSpecs), pageable);
        return responseFactory.createSuccessResponse(authors, authors.stream().map(authorMapper::toResponse).toList());
    }

    @Override
    public ApiResponse<AuthorResponse> getAuthorById(UUID id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> authorNotFoundException(id));
        return responseFactory.createSuccessResponse(authorMapper.toResponse(author));
    }

    @Override
    @Transactional
    public ApiResponse<AuthorResponse> createAuthor(AuthorRequest request) {
        Author author = authorMapper.toEntity(request);
        Author savedAuthor = authorRepository.save(author);
        return responseFactory.createSuccessResponse(authorMapper.toResponse(savedAuthor));
    }

    @Override
    @Transactional
    public ApiResponse<AuthorResponse> updateAuthor(UUID id, AuthorRequest request) {
        Author author = authorRepository.findById(id).orElseThrow(() -> authorNotFoundException(id));

        authorMapper.updateEntityFromRequest(request, author);
        Author savedAuthor = authorRepository.save(author);
        return responseFactory.createSuccessResponse(authorMapper.toResponse(savedAuthor));
    }

    @Override
    @Transactional
    public void deleteAuthor(UUID id) {
        throwExceptionIfNotExistById(id);
        authorRepository.deleteById(id);
    }

    @Override
    public void throwExceptionIfNotExistById(UUID id) {
        if (!authorRepository.existsById(id))
            throw authorNotFoundException(id);
    }

    private ResourceNotFoundException authorNotFoundException(UUID id) {
        return new ResourceNotFoundException("Author not found with ID: " + id);
    }
}
