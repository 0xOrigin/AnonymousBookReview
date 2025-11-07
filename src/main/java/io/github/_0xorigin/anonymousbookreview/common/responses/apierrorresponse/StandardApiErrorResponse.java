package io.github._0xorigin.anonymousbookreview.common.responses.apierrorresponse;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public record StandardApiErrorResponse(
    OffsetDateTime timestamp,
    String message,
    String path,
    Map<String, List<String>> errors
) implements ApiErrorResponse {
    public StandardApiErrorResponse(
        String message,
        String path,
        Map<String, List<String>> errors
    ) {
        this(OffsetDateTime.now(), message, path, errors);
    }
}
