package io.github._0xorigin.anonymousbookreview.author.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record AuthorRequest(
    @NotBlank(message = "Author name is required")
    @Size(min = 1, max = 255, message = "Author name must be between 1 and 255 characters")
    String name
) {}
