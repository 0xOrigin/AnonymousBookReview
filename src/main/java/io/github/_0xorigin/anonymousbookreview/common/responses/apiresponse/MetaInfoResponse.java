package io.github._0xorigin.anonymousbookreview.common.responses.apiresponse;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

public record MetaInfoResponse(
    @JsonInclude(JsonInclude.Include.NON_NULL) Long count,
    @JsonInclude(JsonInclude.Include.NON_NULL) @JsonAnyGetter Map<String, ?> info
) {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long count;
        private Map<String, ?> info;

        private Builder() {}

        public Builder count(Long count) {
            this.count = count;
            return this;
        }

        public Builder info(Map<String, ?> info) {
            this.info = info;
            return this;
        }

        public MetaInfoResponse build() {
            return new MetaInfoResponse(count, info);
        }
    }
}
