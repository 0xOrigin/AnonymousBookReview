package io.github._0xorigin.anonymousbookreview.book.mapper;

import io.github._0xorigin.anonymousbookreview.book.Book;
import io.github._0xorigin.anonymousbookreview.book.dto.BookRequest;
import io.github._0xorigin.anonymousbookreview.book.dto.BookResponse;
import io.github._0xorigin.anonymousbookreview.book.dto.BookSummaryResponse;
import io.github._0xorigin.anonymousbookreview.book.projection.BookSummaryView;
import io.github._0xorigin.anonymousbookreview.book.projection.BookView;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper {

    @Mapping(target = "author.id", source = "authorId")
    @Mapping(target = "author.name", source = "authorName")
    @Mapping(target = "averageRating", ignore = true)
    @Mapping(target = "totalReviews", ignore = true)
    BookResponse toResponse(BookView book);

    @Mapping(target = "author.id", source = "authorId")
    @Mapping(target = "author.name", source = "authorName")
    @Mapping(target = "averageRating", ignore = true)
    @Mapping(target = "totalReviews", ignore = true)
    BookSummaryResponse toSummaryResponse(BookSummaryView bookSummaryView);

    @Mapping(target = "author", ignore = true)
    Book toEntity(BookRequest request);

    @Mapping(target = "author", ignore = true)
    void updateEntityFromRequest(BookRequest request, @MappingTarget Book book);
}
