package io.github._0xorigin.anonymousbookreview.common.responses.apiresponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Page;

public record PaginationInfoResponse(
    Integer prev,
    Integer next,
    Integer last,
    Integer current,
    Integer size,
    @JsonIgnore Page<?> page
) {
    public PaginationInfoResponse(Page<?> page) {
        this(
            calculatePrev(page),
            calculateNext(page),
            calculateLast(page),
            calculateCurrent(page),
            calculateSize(page),
            page
        );
    }

    private static Integer calculatePrev(Page<?> page) {
        if (page == null)
            return null;
        return page.hasPrevious() && page.getTotalElements() > page.getSize() ? page.getNumber() : null;
    }

    private static Integer calculateNext(Page<?> page) {
        if (page == null)
            return null;
        return page.hasNext() ? page.getNumber() + 2 : null;
    }

    private static Integer calculateLast(Page<?> page) {
        return page != null ? page.getTotalPages() : null;
    }

    private static Integer calculateCurrent(Page<?> page) {
        return page != null ? page.getNumber() + 1 : null;
    }

    private static Integer calculateSize(Page<?> page) {
        return page != null ? page.getSize() : null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Page<?> page;

        private Builder() {}

        public Builder page(Page<?> page) {
            this.page = page;
            return this;
        }

        public PaginationInfoResponse build() {
            return new PaginationInfoResponse(page);
        }
    }
}
