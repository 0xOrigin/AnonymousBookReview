package io.github._0xorigin.anonymousbookreview.book.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record BasicAuthorInfoResponse (
    UUID id,
    String name
) {}
