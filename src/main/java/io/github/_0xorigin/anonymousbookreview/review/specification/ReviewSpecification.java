package io.github._0xorigin.anonymousbookreview.review.specification;

import io.github._0xorigin.anonymousbookreview.review.Review;
import io.github._0xorigin.queryfilterbuilder.FilterContext;
import io.github._0xorigin.queryfilterbuilder.SortContext;
import io.github._0xorigin.queryfilterbuilder.base.filteroperator.Operator;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class ReviewSpecification {
    private final FilterContext.Template<Review> filterTemplate = generateFilterTemplate();
    private final SortContext.Template<Review> sortTemplate = generateSortTemplate();

    private FilterContext.Template<Review> generateFilterTemplate() {
        return FilterContext.buildTemplateForType(Review.class)
            .queryParam(builder ->
                builder
                    .addFilter("createdAt", Operator.GTE, Operator.LTE, Operator.BETWEEN)
            )
            .requestBody(builder ->
                builder
                    .addFilter("rating", Operator.GTE, Operator.LTE)
                    .addFilter("comment", (root, cq, cb) -> root.get("comment"), Operator.EQ, Operator.CONTAINS, Operator.STARTS_WITH, Operator.ENDS_WITH)
                    .addFilter("updatedAt", Operator.GTE, Operator.LTE, Operator.BETWEEN)
            )
            .buildTemplate();
    }

    private SortContext.Template<Review> generateSortTemplate() {
        return SortContext.buildTemplateForType(Review.class)
            .queryParam(builder ->
                builder
                    .addSorts("rating")
                    .addDescSort("updatedAt", (root, cq, cb) -> root.get("updatedAt"))
            )
            .requestBody(builder ->
                builder
                    .addSorts("rating")
                    .addDescSort("createdAt", (root, cq, cb) -> root.get("createdAt"))
            )
            .buildTemplate();
    }
}
