package io.github._0xorigin.anonymousbookreview.common.responses.apierrorresponse;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public interface ApiErrorResponse {
    OffsetDateTime timestamp();

    String message();

    String path();

    Map<String, List<String>> errors();
}
