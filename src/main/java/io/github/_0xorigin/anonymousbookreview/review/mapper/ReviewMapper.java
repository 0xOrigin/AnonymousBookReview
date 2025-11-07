package io.github._0xorigin.anonymousbookreview.review.mapper;

import io.github._0xorigin.anonymousbookreview.review.Review;
import io.github._0xorigin.anonymousbookreview.review.dto.BookReviewRequest;
import io.github._0xorigin.anonymousbookreview.review.dto.BookReviewResponse;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReviewMapper {
    BookReviewResponse toResponse(Review review);

    @Mapping(target = "book", ignore = true)
    Review toEntity(BookReviewRequest request);
}
