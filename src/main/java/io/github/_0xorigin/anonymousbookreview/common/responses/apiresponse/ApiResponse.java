package io.github._0xorigin.anonymousbookreview.common.responses.apiresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Generic API response wrapper", implementation = StandardApiResponse.class)
public interface ApiResponse<T> {
    String message();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    MetaInfoResponse meta();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    PaginationInfoResponse pagination();

    @Schema(description = "Response payload")
    T data();
}
