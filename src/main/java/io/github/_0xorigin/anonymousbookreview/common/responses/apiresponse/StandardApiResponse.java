package io.github._0xorigin.anonymousbookreview.common.responses.apiresponse;

import com.fasterxml.jackson.annotation.JsonInclude;

public record StandardApiResponse<T> (
    String message,
    @JsonInclude(JsonInclude.Include.NON_NULL) MetaInfoResponse meta,
    @JsonInclude(JsonInclude.Include.NON_NULL) PaginationInfoResponse pagination,
    T data
) implements ApiResponse<T> {

    public StandardApiResponse(
        String message,
        MetaInfoResponse meta,
        PaginationInfoResponse pagination,
        T data
    ) {
        this.message = message;
        this.meta = (meta != null && meta.count() != null) ? meta : null;
        this.pagination = (pagination != null && pagination.last() != null) ? pagination : null;
        this.data = data;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private String message = "Success";
        private MetaInfoResponse meta;
        private PaginationInfoResponse pagination;
        private T data;

        private Builder() {}

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> meta(MetaInfoResponse meta) {
            this.meta = meta;
            return this;
        }

        public Builder<T> pagination(PaginationInfoResponse pagination) {
            this.pagination = pagination;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public StandardApiResponse<T> build() {
            return new StandardApiResponse<>(message, meta, pagination, data);
        }
    }
}
