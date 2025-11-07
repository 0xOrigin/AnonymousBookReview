package io.github._0xorigin.anonymousbookreview.author.mapper;

import io.github._0xorigin.anonymousbookreview.author.Author;
import io.github._0xorigin.anonymousbookreview.author.dto.AuthorRequest;
import io.github._0xorigin.anonymousbookreview.author.dto.AuthorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthorMapper {
    AuthorResponse toResponse(Author author);

    Author toEntity(AuthorRequest request);

    void updateEntityFromRequest(AuthorRequest request, @MappingTarget Author author);
}
